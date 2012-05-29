package pilsner.dwt;

import pilsner.utils.math.Power2Utils;

/**
 * T��da pro rychlou waveletovu transformaci pomoc� 
 * Daubechies4 wavelet� (d�lka 8) a mate�sk�ch koeficient�.
 * Po transformaci vznikne pole stejn� d�lky p�vodn�ho sign�lu,
 * proto�e se podvozorkov�v�.
 * 
 * @author Petr Soukal
 */
public class FastDaubechies4 {
    //mate�sk� koeficienty

    private static final double[] scale = {0.230377813308855,
        0.714846570552542,
        0.630880767929590,
        -0.0279837694169839,
        -0.187034811718881,
        0.0308413818359870,
        0.0328830116669829,
        -0.0105974017849973};

    //koeficienty waveletu
    private static final double[] wavelet = {scale[7], -scale[6], scale[5], -scale[4], scale[3],
        -scale[2], scale[1], -scale[0]};
    //	koeficienty waveletu
    private static final double[] iScale = {scale[6], wavelet[6],
        scale[4], wavelet[4],
        scale[2], wavelet[2],
        scale[0], wavelet[0]};
    //	koeficienty waveletu
    private static final double[] iWavelet = {scale[7], wavelet[7],
        scale[5], wavelet[5],
        scale[3], wavelet[3],
        scale[1], wavelet[1]};

    /**
     * Metoda prodlu�uje vstupn� sign�l na d�lku (2^n) pokud v takovou d�lku nem�
     * a nov� m�sto se vypln� nulami.
     * Pokud m� sign�l d�lku 8 nebo v�t��, tak spou�t� transformaci.
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

        for (int j = 0; j < last - 7; j += Power2Utils.CONST_2, i++) {
            tmp[i] = signal[j] * scale[0] + signal[j + 1] * scale[1]
                    + signal[j + 2] * scale[2] + signal[j + 3] * scale[3]
                    + signal[j + 4] * scale[4] + signal[j + 5] * scale[5]
                    + signal[j + 6] * scale[6] + signal[j + 7] * scale[7];

            tmp[i + half] = signal[j] * wavelet[0] + signal[j + 1] * wavelet[1]
                    + signal[j + 2] * wavelet[2] + signal[j + 3] * wavelet[3]
                    + signal[j + 4] * wavelet[4] + signal[j + 5] * wavelet[5]
                    + signal[j + 6] * wavelet[6] + signal[j + 7] * wavelet[7];
        }

        tmp[i] = signal[last - 6] * scale[0] + signal[last - 5] * scale[1]
                + signal[last - 4] * scale[2] + signal[last - 3] * scale[3]
                + signal[last - 2] * scale[4] + signal[last - 1] * scale[5]
                + signal[last - 1] * scale[6] + signal[last - 2] * scale[7];

        tmp[i + 1] = signal[last - 4] * scale[0] + signal[last - 3] * scale[1]
                + signal[last - 2] * scale[2] + signal[last - 1] * scale[3]
                + signal[last - 1] * scale[4] + signal[last - 2] * scale[5]
                + signal[last - 3] * scale[6] + signal[last - 4] * scale[7];

        tmp[i + 2] = signal[last - 2] * scale[0] + signal[last - 1] * scale[1]
                + signal[last - 1] * scale[2] + signal[last - 2] * scale[3]
                + signal[last - 3] * scale[4] + signal[last - 4] * scale[5]
                + signal[last - 5] * scale[6] + signal[last - 6] * scale[7];

        tmp[i + half] = signal[last - 6] * wavelet[0] + signal[last - 5] * wavelet[1]
                + signal[last - 4] * wavelet[2] + signal[last - 3] * wavelet[3]
                + signal[last - 2] * wavelet[4] + signal[last - 1] * wavelet[5]
                + signal[last - 1] * wavelet[6] + signal[last - 2] * wavelet[7];

        tmp[i + half + 1] = signal[last - 4] * wavelet[0] + signal[last - 3] * wavelet[1]
                + signal[last - 2] * wavelet[2] + signal[last - 1] * wavelet[3]
                + signal[last - 1] * wavelet[4] + signal[last - 2] * wavelet[5]
                + signal[last - 3] * wavelet[6] + signal[last - 4] * wavelet[7];

        tmp[i + half + 2] = signal[last - 2] * wavelet[0] + signal[last - 1] * wavelet[1]
                + signal[last - 1] * wavelet[2] + signal[last - 2] * wavelet[3]
                + signal[last - 3] * wavelet[4] + signal[last - 4] * wavelet[5]
                + signal[last - 5] * wavelet[6] + signal[last - 6] * wavelet[7];

        System.arraycopy(tmp, 0, signal, 0, last);
    }

    /**
     * Metoda prodlu�uje vstupn� sign�l na d�lku (2^n) pokud v takovou d�lku nem�
     * a nov� m�sto se vypln� nulami.
     * Pokud maj� d�lku 8 nebo v�t�� spou�t� inverzn� transformaci.
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
     * Metoda transformuj�c� sign�l zp�tky na origin�ln� pomoc�
     * nastaven�ch wavelet� a mate�sk�ch koeficient�.
     * Skl�d� p�vodn� sign�l z aproxima�n�ch a detailn�ch slo�ek
     * transformovan�ho sign�lu.
     *
     * @param coef - transformovan� sign�l.
     * @param last - d�lka transformovan�ho �seku sign�lu.
     */
    private static void invTransform(double[] coef, int last) {
        int half = last / Power2Utils.CONST_2;
        int halfPls1 = half + 1;
        int halfPls2 = half + 2;
        int halfPls3 = half + 3;
        double tmp[] = new double[last];
        int j = 0;

        tmp[j++] = coef[half - 1] * iScale[4] + coef[last - 1] * iScale[5]
                + coef[0] * iScale[6] + coef[half] * iScale[7]
                + coef[half - 3] * iScale[0] + coef[last - 3] * iScale[1]
                + coef[half - 2] * iScale[2] + coef[last - 2] * iScale[3];

        tmp[j++] = coef[half - 1] * iWavelet[4] + coef[last - 1] * iWavelet[5]
                + coef[0] * iWavelet[6] + coef[half] * iWavelet[7]
                + coef[half - 3] * iWavelet[0] + coef[last - 3] * iWavelet[1]
                + coef[half - 2] * iWavelet[2] + coef[last - 2] * iWavelet[3];
        //---------------------------------------------------------------------

        tmp[j++] = coef[half - 1] * iScale[2] + coef[last - 1] * iScale[3]
                + coef[0] * iScale[4] + coef[half] * iScale[5]
                + coef[1] * iScale[6] + coef[halfPls1] * iScale[7]
                + coef[half - 2] * iScale[0] + coef[last - 2] * iScale[1];

        tmp[j++] = coef[half - 1] * iWavelet[2] + coef[last - 1] * iWavelet[3]
                + coef[0] * iWavelet[4] + coef[half] * iWavelet[5]
                + coef[1] * iWavelet[6] + coef[halfPls1] * iWavelet[7]
                + coef[half - 2] * iWavelet[0] + coef[last - 2] * iWavelet[1];
        //---------------------------------------------------------------------

        tmp[j++] = coef[half - 1] * iScale[0] + coef[last - 1] * iScale[1]
                + coef[0] * iScale[2] + coef[half] * iScale[3]
                + coef[1] * iScale[4] + coef[halfPls1] * iScale[5]
                + coef[2] * iScale[6] + coef[halfPls2] * iScale[7];

        tmp[j++] = coef[half - 1] * iWavelet[0] + coef[last - 1] * iWavelet[1]
                + coef[0] * iWavelet[2] + coef[half] * iWavelet[3]
                + coef[1] * iWavelet[4] + coef[halfPls1] * iWavelet[5]
                + coef[2] * iWavelet[6] + coef[halfPls2] * iWavelet[7];


        for (int i = 0; i < half - 3; i++) {
            tmp[j++] = coef[i] * iScale[0] + coef[i + half] * iScale[1]
                    + coef[i + 1] * iScale[2] + coef[i + halfPls1] * iScale[3]
                    + coef[i + 2] * iScale[4] + coef[i + halfPls2] * iScale[5]
                    + coef[i + 3] * iScale[6] + coef[i + halfPls3] * iScale[7];

            tmp[j++] = coef[i] * iWavelet[0] + coef[i + half] * iWavelet[1]
                    + coef[i + 1] * iWavelet[2] + coef[i + halfPls1] * iWavelet[3]
                    + coef[i + 2] * iWavelet[4] + coef[i + halfPls2] * iWavelet[5]
                    + coef[i + 3] * iWavelet[6] + coef[i + halfPls3] * iWavelet[7];
        }

        System.arraycopy(tmp, 0, coef, 0, last);
    }
}
