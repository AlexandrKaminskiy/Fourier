package com.example.fourier.stats.impl;

import com.example.fourier.Complex;
import com.example.fourier.Fourier;
import com.example.fourier.model.Filter;
import com.example.fourier.stats.Calculator;
import com.example.fourier.utils.FilterUtils;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class AmplitudeCalculator implements Calculator {

    @Override
    public List<XYChart.Data<Double, Double>> calculate(int n, int k, List<XYChart.Data<Double, Double>> data, final Filter filter) {
        List<XYChart.Data<Double, Double>> values = new ArrayList<>();

        data = IntStream.range(0, n)
            .boxed()
            .filter(i -> i % (n / k) == 0)
            .map(data::get)
            .toList();
        for (int i = 0; i < k; i++) {
            if (FilterUtils.filter(i, k, filter)) {

                values.add(new XYChart.Data<>((double) i, 0.0));
                continue;
            }

            Complex complex = Fourier.getValue(data, i, k);
            double a = Math.sqrt(complex.getRe() * complex.getRe() + complex.getIm() * complex.getIm());

            values.add(new XYChart.Data<>((double) i, a));
        }



        return values;
    }
}
