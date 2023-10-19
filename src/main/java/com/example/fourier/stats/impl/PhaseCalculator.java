package com.example.fourier.stats.impl;

import com.example.fourier.Complex;
import com.example.fourier.Fourier;
import com.example.fourier.stats.Calculator;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class PhaseCalculator implements Calculator {

    @Override
    public List<XYChart.Data<Double, Double>> calculate(int n, int k, List<XYChart.Data<Double, Double>> data) {
        List<XYChart.Data<Double, Double>> values = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            Complex complex = Fourier.getValue(data, i, n);
            double fi = Math.atan2(complex.getRe(), complex.getIm());
            values.add(new XYChart.Data<>((double) i, fi));
        }

        return values;
    }

}
