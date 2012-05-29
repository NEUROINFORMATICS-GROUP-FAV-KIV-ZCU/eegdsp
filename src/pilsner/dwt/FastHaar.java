package pilsner.dwt;

import pilsner.utils.math.Power2Utils;

/**
 * T��da pro rychlou waveletovu transformaci pomoc� 
 * Haar wavelet� (d�lka 2) a mate�sk�ch koeficient�.
 * Po transformaci vznikne pole stejn� d�lky p�vodn�ho sign�lu,
 * proto�e se podvozorkov�v�.
 * 
 * @author Petr Soukal
 */
public class FastHaar {
    //mate�sk� koeficienty
    private static final double[] scale = {1.0 / Math.sqrt(2), 1.0 / Math.sqrt(2)};
    //koeficienty waveletu
    private static final double[] wavelet = {scale[1], -scale[0]};

    /**
     * Metoda prodlu�uje vstupn� sign�l na d�lku (2^n) pokud v takovou d�lku nem�
     * a nov� m�sto se vypln� nulami.
     * Pokud m� sign�l 2 nebo v�t��, tak spou�t� transformaci.
     * Kone�n� �rove� transformace je odvozena od d�lky sign�lu.
     *
     * @param inputSignal - origin�ln� vstupn� sign�l.
     * @return sign�l po transformaci.
     */
    public static double[] transform(double[] inputSignal) {
        double[] signal = Power2Utils.createPower2LengthArray(inputSignal);
        
        for (int last = signal.length; last >= wavelet.length; last >>= 1) {
            transform(signal, last);
        }

        return signal;
    }

    /**
     * Metoda transformuj�c� sign�l pomoc� nastaven�ch wavelet� a mate�sk�ch koeficient�.
     * V prvn� polovin� �seku transformovan�ho sign�lu je ukl�d�n� aproxima�n� slo�ka
     * z�skan� mate�sk�mi koeficienty a v druh� polovin� je ukl�d�na detailn� slo�ka
     * z�skan� pomoc� waveletov�mi koeficienty.
     *
     *
     * @param signal - transformovan� sign�l.
     * @param last - d�lka transformovan�ho �seku sign�lu.
     */
    private static void transform(double[] signal, int last) {
        int half = last / Power2Utils.CONST_2;
        double tmp[] = new double[last];
        int i = 0;

        for (int j = 0; j < last; j += Power2Utils.CONST_2, i++) {
            tmp[i] = signal[j] * scale[0] + signal[j + 1] * scale[1];
            tmp[i + half] = signal[j] * wavelet[0] + signal[j + 1] * wavelet[1];
        }

        System.arraycopy(tmp, 0, signal, 0, last);
    }

    /**
     * Metoda prodlu�uje vstupn� koeficienty na d�lku (2^n) pokud takovou d�lku nem�j�
     * a nov� m�sto se vypln� nulami.
     * Pokud maj� d�lku 4 nebo v�t�� spou�t� inverzn� transformaci.
     *
     * @param inputCoef - transformovan� vstupn� sign�l.
     * @return sign�l po inverzn� transformaci.
     */
    public static double[] invTransform(double[] inputCoef) {
        double[] coeficients = Power2Utils.createPower2LengthArray(inputCoef);

        for (int last = wavelet.length; last <= coeficients.length; last <<= 1) {
            invTransform(coeficients, last);
        }

        return coeficients;
    }

    /**
     * Metoda transformuj�c� coeficienty zp�tky na origin�ln� sign�l pomoc�
     * nastaven�ch wavelet� a mate�sk�ch koeficient�.
     * Skl�d� p�vodn� sign�l z aproxima�n�ch a detailn�ch slo�ek
     * transformovan�ho sign�lu.
     *
     * @param coef - transformovan� sign�l.
     * @param last - d�lka transformovan�ho �seku sign�lu.
     */
    private static void invTransform(double[] coef, int last) {
        int half = last / Power2Utils.CONST_2;
        double tmp[] = new double[last];

        for (int i = 0, j = 0; i < half; i++, j += 2) {
            tmp[j] = coef[i] * scale[0] + coef[i + half] * scale[1];
            tmp[j + 1] = coef[i] * wavelet[0] + coef[i + half] * wavelet[1];
        }

        System.arraycopy(tmp, 0, coef, 0, last);
    }
}
