package pilsner.spectra;

import org.apache.commons.math.MathException;
import org.apache.commons.math.complex.Complex;
import org.apache.commons.math.transform.FastFourierTransformer;

import pilsner.signalwindows.Window;
import pilsner.utils.math.Power2Utils;

/**
 * T��da jej� obj�kt ponese nastaven� pro tvorbu periodogramu jako jsou
 * informace o onsided/twosided
 * okno, kter�m n�sobit
 * jseli se m� po��tat power spectral density nebo mean square
 * 
 * v�e je kompatibiln� s matlabem a� na d�lku v�stupn�ho pole, kter� je v matlabu
 * �m�rn� d�lce sign�lu a zde je rovn� nejbli�s�� vy��� mocnin� dvou.
 * 
 * @author Martin �imek
 * */
public class Periodogram {

    /* Reference na sign�lov� okno kter�m n�sobit */
    private Window signalWindowInstance;
    /* Energie okna, snadno se spo�etla p�i vzorkov�n� okna */
    private double windowEnergy;
    /* Informace o tom jestli m� b�t periodogram onesided --- tj. jenom z p�lky */
    private boolean onsided;
    /* vzorkovac� frekvence, nen�-li zadan� bereme 2pi */
    private double sampleRate;
    /* informace jestli se m� pou��t power spectral density nebo meansquare */
    private boolean psd;
    /* knihovna ve, kter� je rychl� fourierova transformace */
    private FastFourierTransformer fftLib;
    /* nfft -- jako v matlabu, aby bylo mo�n� prov�d�t fourierovku i nad v�t��mno�inou */
    private int nfft;

    /**
     * Nastav� defaultn� hodnoty stavov�ch prom�nn�ch
     * */
    private void initDefaults() {
        this.onsided = false;
        this.signalWindowInstance = null;
        this.sampleRate = 2 * Math.PI;
        this.psd = true;
        this.fftLib = new FastFourierTransformer();
        this.nfft = -1; //aby to jako bylo odstaven�
    }

    /**
     * Konstruktor, kter� vytvo�� nejz�kladn�j�� verzi periodogramu.
     * bez n�soben� oknem, bez vzorkovac� frekvence, dvoustrann� periodogram
     *
     * */
    public Periodogram() {
        this.initDefaults();
    }

    /**
     * @return instanci objektu, kter� um�
     * generovat vzorky p��slu�n�ho sign�lov�ho okna
     * */
    public Window getSignalWindowInstance() {
        return signalWindowInstance;
    }

    /**
     * Nastav� periodogramu okno, kter�m se m� vstupn� sign�l n�sobit
     * @param signalWindowInstance je instance objektu s rozhran�m Window
     * nebo null pokud chceme n�soben� oknem vypnout.
     * */
    public void setSignalWindowInstance(Window signalWindowInstance) {
        this.signalWindowInstance = signalWindowInstance;
    }

    /**
     * Pod� informaci o tom zda periodogram bude generov�n
     * jako jednostrann�. To znamen�, �e pokud je d�lka sign�lu sud�,
     * tak se prvn� prvek DFT bere v p�vodn� hodnot� a a� do
     * poloviny se prvky n�sob� dvojkou.
     * Pokud je d�lka lich� berou se hodnoty DFT dvojn�sobn� krom�
     * prvn�ho a prost�edn�ho. Z toho vypl�v�, �e periodogram bude m�t
     * polovi�n� d�lku ne� sign�l.
     *
     *  @return je periodogram jednostrann�?
     * */
    public boolean isOnsided() {
        return onsided;
    }

    /**
     * nastav� p��znak o tom zda p��tat periodogram jednostrann� nebo dvoustrann�.
     * @param onsided hodnota zda po��tat jednostann� periodogram.
     */
    public void setOnsided(boolean onsided) {
        this.onsided = onsided;
    }

    /**
     * @return Vzorkovac� frekvenci.
     * */
    public double getSampleRate() {
        return sampleRate;
    }

    /**
     * Natav� vzorkovac� frekvenci pokud chceme po��tat s t�mto �dajem.
     * */
    public void setSampleRate(double sampleRate) {
        this.sampleRate = sampleRate;
    }

    /**
     * Indikuje jestli se m� po��tat Power spectral density.
     * Pokud je true, tak ano. Jinak se po��t� oby�ejn� periodogram
     * tj. ned�l� se samplovac� frakvenc� nebo 2pi
     * */
    public boolean isPsd() {
        return psd;
    }

    /**
     * Nastav� p��znak pro po��t�n� power spectral density.
     * To znamen� �e pokud je true, tak se po��t� a t�m p�dem
     * se v�sledn� spektrum d�l� vzorkovac� frekvenc� nebo 2pi,
     * kdy� nen� zadan�.
     * */
    public void setPsd(boolean psd) {
        this.psd = psd;
    }

    /**
     * @return po�et vzork� nad kter�mi bude prov�d�na fouryerova transformce
     */
    public int getNfft() {
        return nfft;
    }

    /**
     * Tato hodnota slou�� pouze k prodlou�en� sign�lu na spo�ten� FFT.
     * Pokud bude men�� ne� d�lka sign�lu automaticky j� p�i v�po�tu
     * p�ekryje hodnota vypo�ten� z d�lky sign�lu.
     *
     * @param nfft po�et vzork� nad kter�m bude prov�d�na fourierova transformace
     * automaticky se zaokrouhl� na nejbli��� vy��� mocninu dvou.
     */
    public void setNfft(int nfft) {
        this.nfft = Power2Utils.newMajorNumberOfPowerBase2(nfft);
    }

    /**
     * Spo�te periodogram shodn� s v�po�tem matlabu.
     * 1) spo�te nejbli��� vy��� mocninu dvou k d�lce sign�lu
     *
     * 2) vyn�sob� sign�l oknem je-li nastaven� n�jak� instance okna
     *    to obn�� kop�rov�n� v pam�ti, proto�e nem��eme po�kodit
     *    vstupn� sign�l. Ten je jenom zap�j�en!!
     *
     * 3) dopln� sign�l nulami na d�lku mocniny dvou je-li t�eba
     *    to obn�� kop�rov�n� dat v pam�ti!!
     *
     * 4) Ud�l� rychlou fourierovu transformaci (FFT) sign�lu
     *
     * 5) p�evede v�sledek FFT na double jako�to |X|^2 kde X je
     *    komplexn� ��slo z�skan� ve FFT.
     *
     * 6) ka�dou z t�chto hodnot n�sob� 1/U,
     *    kde U je energie okna sum |w(i)|^2 a v p��pad�, �e okno nen�
     *    nastaveno tj. chov� se jako obd�ln�kov� je energie rovna po�tu
     *    vzork� okna (N).
     *
     * 7) Pokud je psd == true, tak ka�dou z hodnot p�en�sob� 1/(2*pi),
     *    pokud nen� zadan� vzorkovac� frekvence jinak n�sob� 1/sampleRate.
     *
     * 8) Pokud je onesided == true, tak zkr�t� spektrum na N/2 (sud�) nebo
     *    na N/2 + 1 (lich�) a vzorky na indexech 1 a� N/2 vyn�sob� dv�ma.
     *
     * @param signal pole vzork� se sign�lem.
     * @param bounds jedna nebo dv� hodnoty int. Prvn� zanamen� po��te�n�
     * prvek, druh� po�et vzork�, nad kter�mi po��tat tepriodogrm. Pou�it�
     * tohoto vy�aduje kop�rov�n� �seku dat v pam�ti!!
     * */
    public double[] getPeriodogram(double[] inputSignal, int... bounds) {
        Complex[] fftResult; //v�sledek rychl� fourierovky
        double[] signal; //pracovn� sign�l
        boolean signalAlreadyCopied = false; //informace o tom, �e u� jsme jednou sign�l kop�rovali.
        int from = 0; //okdud za��t
        int N = inputSignal.length; //kolik toho vz�t
        double[] retval; //n�vratov� hodnota s odhadem spektra
                /* navzorkovan� okno v po�adovan� d�lce */
        double[] windowSeqence = null;

        /* na�ten� vario parametr� */
        if (bounds.length > 0) {
            from = bounds[0];
            N -= from;
            if (bounds.length > 1) {
                N = bounds[1];
            }
        }

        //nejbli��� vy��� mocnina dvou
        int hlp = Power2Utils.newMajorNumberOfPowerBase2(N);
        hlp = Math.max(hlp, this.nfft); //p��padn� p�ekryt� vy��� hodnotou nfft

        /*
         * P�en�soben� oknem pokud je nastaven�
         * */
        if (this.signalWindowInstance != null) {
            // je�t� se nevzorkovalo tak to naprav�me
            if (windowSeqence == null || windowSeqence.length != N) {
                windowSeqence = this.signalWindowInstance.getWinSequence(N);
                this.windowEnergy = SpectraEstimation.signalEnergy(windowSeqence);
            }

            //duplikov�n� sign�lu, abychom nepo�kodli origin�l a rovnou jej zv�t��me na mocninu dvou
            signal = new double[hlp];
            signalAlreadyCopied = true;

            //kop�rov�n� do nov�ho m�sta
            System.arraycopy(inputSignal, from, signal, 0, N);

            //n�soben� vstupn�ho sign�lu oknem velikosti N
            SpectraEstimation.multiplyBy(signal, windowSeqence);

        } else {
            signal = inputSignal;
        }

        /* zv�t�en� na rozm�r mocniny dvou */
        if ((hlp > N || from != 0) && signalAlreadyCopied == false) {
            //duplikov�n� sign�lu, abychom nepo�kodli origin�l a rovnou jej zv�t��me na mocninu dvou
            signal = new double[hlp];
            signalAlreadyCopied = true;

            //kop�rov�n� do nov�ho m�sta
            System.arraycopy(inputSignal, 0, signal, 0, N);
        }

        /* dopln�n� zbytku sign�lu nulami
         * to je na kop�rov�n� �i nekop�rov�n� nez�visle,
         * proto�e pokud se nemuselo zv�t�ovat tak cyklus neza�ne. */
        for (int i = N; i < hlp; i += 1) {
            signal[i] = 0;
        }


        fftResult = fftLib.transform(signal);

        /* v�po�et energie okna m�me-li n�jak� nastaven� */
        double U;
        if (windowSeqence != null && this.psd) {
            U = (double) 1 / (double) this.windowEnergy;
        } else if (windowSeqence != null) {
            int cnt = windowSeqence.length;
            U = 0;

            //sou�et vzork� okna
            for (int i = 0; i < cnt; i += 1) {
                U += windowSeqence[i];
            }

            //druh� mocnina sou�tu
            U *= U;
            U = 1 / U;
        } else if (!this.psd) {
            U = (double) 1 / (double) (N * N);
        } else {
            U = (double) 1 / (double) N;
        }

        /* upraven� koeficinetu pro p��pad, �e po��t�me PSD */
        if (this.psd) {
            U /= this.sampleRate;
        }

        /* alokace pam�ti na v�sledn� odhad spektra */
        int len = hlp;
        if (this.onsided) {
            len = hlp / 2;
            if ((hlp & 0x01) == 0) {
                len += 1;
            }
        }
        retval = new double[len];

        /* vlastn� v�po�et periodogramu */
        double real, imag;
        for (int i = 0; i < len; i += 1) {
            real = fftResult[i].getReal();
            imag = fftResult[i].getImaginary();
            retval[i] = U * (real * real + imag * imag);
        }

        /* p�i v�po�tu periodogramu onsided se mus� n�kter� prvky p�en�sobit dv�ma */
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
