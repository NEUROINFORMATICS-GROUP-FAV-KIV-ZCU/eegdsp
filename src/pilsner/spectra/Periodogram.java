package pilsner.spectra;

import org.apache.commons.math.MathException;
import org.apache.commons.math.complex.Complex;
import org.apache.commons.math.transform.FastFourierTransformer;

import pilsner.signalwindows.Window;
import pilsner.utils.math.Power2Utils;

/**
 * Tøída jejíž objìkt ponese nastavení pro tvorbu periodogramu jako jsou
 * informace o onsided/twosided
 * okno, kterým násobit
 * jseli se má poèítat power spectral density nebo mean square
 * 
 * vše je kompatibilní s matlabem až na délku výstupního pole, která je v matlabu
 * úmìrná délce signálu a zde je rovná nejbližsší vyšší mocninì dvou.
 * 
 * @author Martin Šimek
 * */
public class Periodogram {

    /* Reference na signálové okno kterým násobit */
    private Window signalWindowInstance;
    /* Energie okna, snadno se spoèetla pøi vzorkování okna */
    private double windowEnergy;
    /* Informace o tom jestli má být periodogram onesided --- tj. jenom z pùlky */
    private boolean onsided;
    /* vzorkovací frekvence, není-li zadaná bereme 2pi */
    private double sampleRate;
    /* informace jestli se má použít power spectral density nebo meansquare */
    private boolean psd;
    /* knihovna ve, které je rychlá fourierova transformace */
    private FastFourierTransformer fftLib;
    /* nfft -- jako v matlabu, aby bylo možná provádìt fourierovku i nad vìtšímnožinou */
    private int nfft;

    /**
     * Nastaví defaultní hodnoty stavových promìnných
     * */
    private void initDefaults() {
        this.onsided = false;
        this.signalWindowInstance = null;
        this.sampleRate = 2 * Math.PI;
        this.psd = true;
        this.fftLib = new FastFourierTransformer();
        this.nfft = -1; //aby to jako bylo odstavený
    }

    /**
     * Konstruktor, který vytvoøí nejzákladnìjší verzi periodogramu.
     * bez násobení oknem, bez vzorkovací frekvence, dvoustranný periodogram
     *
     * */
    public Periodogram() {
        this.initDefaults();
    }

    /**
     * @return instanci objektu, který umí
     * generovat vzorky pøíslušného signálového okna
     * */
    public Window getSignalWindowInstance() {
        return signalWindowInstance;
    }

    /**
     * Nastaví periodogramu okno, kterým se má vstupní signál násobit
     * @param signalWindowInstance je instance objektu s rozhraním Window
     * nebo null pokud chceme násobení oknem vypnout.
     * */
    public void setSignalWindowInstance(Window signalWindowInstance) {
        this.signalWindowInstance = signalWindowInstance;
    }

    /**
     * Podá informaci o tom zda periodogram bude generován
     * jako jednostranný. To znamená, že pokud je délka signálu sudá,
     * tak se první prvek DFT bere v pùvodní hodnotì a až do
     * poloviny se prvky násobí dvojkou.
     * Pokud je délka lichá berou se hodnoty DFT dvojnásobné kromì
     * prvního a prostøedního. Z toho vyplývá, že periodogram bude mít
     * polovièní délku než signál.
     *
     *  @return je periodogram jednostranný?
     * */
    public boolean isOnsided() {
        return onsided;
    }

    /**
     * nastaví pžíznak o tom zda pèítat periodogram jednostranný nebo dvoustranný.
     * @param onsided hodnota zda poèítat jednostanný periodogram.
     */
    public void setOnsided(boolean onsided) {
        this.onsided = onsided;
    }

    /**
     * @return Vzorkovací frekvenci.
     * */
    public double getSampleRate() {
        return sampleRate;
    }

    /**
     * Nataví vzorkovací frekvenci pokud chceme poèítat s tímto údajem.
     * */
    public void setSampleRate(double sampleRate) {
        this.sampleRate = sampleRate;
    }

    /**
     * Indikuje jestli se má poèítat Power spectral density.
     * Pokud je true, tak ano. Jinak se poèítá obyèejný periodogram
     * tj. nedìlí se samplovací frakvencí nebo 2pi
     * */
    public boolean isPsd() {
        return psd;
    }

    /**
     * Nastaví pøíznak pro poèítání power spectral density.
     * To znamená že pokud je true, tak se poèítá a tím pádem
     * se výsledné spektrum dìlí vzorkovací frekvencí nebo 2pi,
     * když není zadaná.
     * */
    public void setPsd(boolean psd) {
        this.psd = psd;
    }

    /**
     * @return poèet vzorkù nad kterými bude provádìna fouryerova transformce
     */
    public int getNfft() {
        return nfft;
    }

    /**
     * Tato hodnota slouží pouze k prodloužení signálu na spoètení FFT.
     * Pokud bude menší než délka signálu automaticky jí pøi výpoètu
     * pøekryje hodnota vypoètená z délky signálu.
     *
     * @param nfft poèet vzorkù nad kterým bude provádìna fourierova transformace
     * automaticky se zaokrouhlí na nejbližší vyšší mocninu dvou.
     */
    public void setNfft(int nfft) {
        this.nfft = Power2Utils.newMajorNumberOfPowerBase2(nfft);
    }

    /**
     * Spoète periodogram shodnì s výpoètem matlabu.
     * 1) spoète nejbližší vyšší mocninu dvou k délce signálu
     *
     * 2) vynásobí signál oknem je-li nastavená nìjaká instance okna
     *    to obnáší kopírování v pamìti, protože nemùžeme poškodit
     *    vstupní signál. Ten je jenom zapùjèen!!
     *
     * 3) doplní signál nulami na délku mocniny dvou je-li tøeba
     *    to obnáší kopírování dat v pamìti!!
     *
     * 4) Udìlá rychlou fourierovu transformaci (FFT) signálu
     *
     * 5) pøevede výsledek FFT na double jakožto |X|^2 kde X je
     *    komplexní èíslo získané ve FFT.
     *
     * 6) každou z tìchto hodnot násobí 1/U,
     *    kde U je energie okna sum |w(i)|^2 a v pøípadì, že okno není
     *    nastaveno tj. chová se jako obdélníkové je energie rovna poètu
     *    vzorkù okna (N).
     *
     * 7) Pokud je psd == true, tak každou z hodnot pøenásobí 1/(2*pi),
     *    pokud není zadaná vzorkovací frekvence jinak násobí 1/sampleRate.
     *
     * 8) Pokud je onesided == true, tak zkrátí spektrum na N/2 (sudé) nebo
     *    na N/2 + 1 (liché) a vzorky na indexech 1 až N/2 vynásobí dvìma.
     *
     * @param signal pole vzorkù se signálem.
     * @param bounds jedna nebo dvì hodnoty int. První zanamená poèáteèní
     * prvek, druhá poèet vzorkù, nad kterými poèítat tepriodogrm. Použití
     * tohoto vyžaduje kopírování úseku dat v pamìti!!
     * */
    public double[] getPeriodogram(double[] inputSignal, int... bounds) {
        Complex[] fftResult; //výsledek rychlé fourierovky
        double[] signal; //pracovní signál
        boolean signalAlreadyCopied = false; //informace o tom, že už jsme jednou signál kopírovali.
        int from = 0; //okdud zaèít
        int N = inputSignal.length; //kolik toho vzít
        double[] retval; //návratová hodnota s odhadem spektra
                /* navzorkované okno v požadované délce */
        double[] windowSeqence = null;

        /* naètení vario parametrù */
        if (bounds.length > 0) {
            from = bounds[0];
            N -= from;
            if (bounds.length > 1) {
                N = bounds[1];
            }
        }

        //nejbližší vyšší mocnina dvou
        int hlp = Power2Utils.newMajorNumberOfPowerBase2(N);
        hlp = Math.max(hlp, this.nfft); //pøípadné pøekrytí vyšší hodnotou nfft

        /*
         * Pøenásobení oknem pokud je nastavené
         * */
        if (this.signalWindowInstance != null) {
            // ještì se nevzorkovalo tak to napravíme
            if (windowSeqence == null || windowSeqence.length != N) {
                windowSeqence = this.signalWindowInstance.getWinSequence(N);
                this.windowEnergy = SpectraEstimation.signalEnergy(windowSeqence);
            }

            //duplikování signálu, abychom nepoškodli originál a rovnou jej zvìtšíme na mocninu dvou
            signal = new double[hlp];
            signalAlreadyCopied = true;

            //kopírování do nového místa
            System.arraycopy(inputSignal, from, signal, 0, N);

            //násobení vstupního signálu oknem velikosti N
            SpectraEstimation.multiplyBy(signal, windowSeqence);

        } else {
            signal = inputSignal;
        }

        /* zvìtšení na rozmìr mocniny dvou */
        if ((hlp > N || from != 0) && signalAlreadyCopied == false) {
            //duplikování signálu, abychom nepoškodli originál a rovnou jej zvìtšíme na mocninu dvou
            signal = new double[hlp];
            signalAlreadyCopied = true;

            //kopírování do nového místa
            System.arraycopy(inputSignal, 0, signal, 0, N);
        }

        /* doplnìní zbytku signálu nulami
         * to je na kopírování èi nekopírování nezávisle,
         * protože pokud se nemuselo zvìtšovat tak cyklus nezaène. */
        for (int i = N; i < hlp; i += 1) {
            signal[i] = 0;
        }


        fftResult = fftLib.transform(signal);

        /* výpoèet energie okna máme-li nìjaké nastavené */
        double U;
        if (windowSeqence != null && this.psd) {
            U = (double) 1 / (double) this.windowEnergy;
        } else if (windowSeqence != null) {
            int cnt = windowSeqence.length;
            U = 0;

            //souèet vzorkù okna
            for (int i = 0; i < cnt; i += 1) {
                U += windowSeqence[i];
            }

            //druhá mocnina souètu
            U *= U;
            U = 1 / U;
        } else if (!this.psd) {
            U = (double) 1 / (double) (N * N);
        } else {
            U = (double) 1 / (double) N;
        }

        /* upravení koeficinetu pro pøípad, že poèítáme PSD */
        if (this.psd) {
            U /= this.sampleRate;
        }

        /* alokace pamìti na výsledný odhad spektra */
        int len = hlp;
        if (this.onsided) {
            len = hlp / 2;
            if ((hlp & 0x01) == 0) {
                len += 1;
            }
        }
        retval = new double[len];

        /* vlastní výpoèet periodogramu */
        double real, imag;
        for (int i = 0; i < len; i += 1) {
            real = fftResult[i].getReal();
            imag = fftResult[i].getImaginary();
            retval[i] = U * (real * real + imag * imag);
        }

        /* pøi výpoètu periodogramu onsided se musí nìkteré prvky pøenásobit dvìma */
        if (this.onsided) {
            if ((hlp & 0x01) == 0) {
                len -= 1;
            }

            for (int i = 1; i < len; i += 1) {
                retval[i] *= 2;
            }
        }

        return retval;
    }
}
