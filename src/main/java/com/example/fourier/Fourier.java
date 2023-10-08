package com.example.fourier;

import javafx.scene.chart.XYChart;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class Fourier {

    public static Complex getValue(List<XYChart.Data<Double, Double>> data, final int i, final int n) {

        double sumRe = 0;
        int j = 0;
        for (var f : data) {
            sumRe += f.getYValue() * Math.cos(2 * Math.PI * i * j / n);
            j++;
        }

        double re = 2.0 / n * sumRe;
        j = 0;
        double sumIm = 0;
        for (var f : data) {
            sumIm += f.getYValue() * Math.sin(2 * Math.PI * i * j / n);
            j++;
        }

        double im = 2.0 / n * sumIm;

        return new Complex(re, im);
    }
}
