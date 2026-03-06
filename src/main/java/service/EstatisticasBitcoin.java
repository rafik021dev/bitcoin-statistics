package service;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstatisticasBitcoin {

    private String media;
    private String mediana;
    private String moda;
    private String variancia;
    private String desvioPadrao;
    private String maximo;
    private String minimo;

    private DecimalFormat df2 = new DecimalFormat("#,##0.00");
    private DecimalFormat df0 = new DecimalFormat("#,##0");

    public void calcular(List<Long> valores) {
        this.media = calcularMedia(valores);
        this.mediana = calcularMediana(valores);
        this.moda = calcularModa(valores);
        this.variancia = calcularVariancia(valores);
        this.desvioPadrao = calcularDesvioPadrao(valores);
        this.maximo = calcularMaximo(valores);
        this.minimo = calcularMinimo(valores);
    }

    public String getMedia() { return media; }
    public String getMediana() { return mediana; }
    public String getModa() { return moda; }
    public String getVariancia() { return variancia; }
    public String getDesvioPadrao() { return desvioPadrao; }
    public String getMaximo() { return maximo; }
    public String getMinimo() { return minimo; }

    private String calcularMedia(List<Long> valores) {
        double soma = 0;
        for (int i = 0; i < valores.size(); i++) {
            soma += valores.get(i);
        }
        return df2.format(soma / valores.size());
    }

    private String calcularMediana(List<Long> valores) {
        long[] array = new long[valores.size()];
        for (int i = 0; i < valores.size(); i++) {
            array[i] = valores.get(i);
        }

        Arrays.sort(array);
        int tam = array.length;

        double mediana;
        if (tam % 2 == 0) {
            mediana = (array[tam / 2 - 1] + array[tam / 2]) / 2.0;
        } else {
            mediana = array[tam / 2];
        }

        return df2.format(mediana);
    }

    private String calcularModa(List<Long> valores) {
        Map<Long, Integer> freq = new HashMap<>();

        for (int i = 0; i < valores.size(); i++) {
            long v = valores.get(i);
            freq.put(v, freq.getOrDefault(v, 0) + 1);
        }

        long moda = valores.get(0);
        int maior = 0;

        for (Map.Entry<Long, Integer> e : freq.entrySet()) {
            if (e.getValue() > maior) {
                maior = e.getValue();
                moda = e.getKey();
            }
        }

        return df0.format(moda);
    }

    private String calcularVariancia(List<Long> valores) {
        double media = 0;
        for (int i = 0; i < valores.size(); i++) {
            media += valores.get(i);
        }
        media /= valores.size();

        double soma = 0;
        for (int i = 0; i < valores.size(); i++) {
            soma += Math.pow(valores.get(i) - media, 2);
        }

        return df2.format(soma / valores.size());
    }

    private String calcularDesvioPadrao(List<Long> valores) {
        double media = 0;
        for (int i = 0; i < valores.size(); i++) {
            media += valores.get(i);
        }
        media /= valores.size();

        double soma = 0;
        for (int i = 0; i < valores.size(); i++) {
            soma += Math.pow(valores.get(i) - media, 2);
        }

        return df2.format(Math.sqrt(soma / valores.size()));
    }

    private String calcularMaximo(List<Long> valores) {
        long max = valores.get(0);
        for (int i = 1; i < valores.size(); i++) {
            if (valores.get(i) > max) {
                max = valores.get(i);
            }
        }
        return df0.format(max);
    }

    private String calcularMinimo(List<Long> valores) {
        long min = valores.get(0);
        for (int i = 1; i < valores.size(); i++) {
            if (valores.get(i) < min) {
                min = valores.get(i);
            }
        }
        return df0.format(min);
    }
}