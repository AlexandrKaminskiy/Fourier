package com.example.fourier;

import javafx.scene.chart.XYChart;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ComplexFunctionConverter {

    public static List<XYChart.Data<Double, Double>> convert(final List<XYChart.Data<Double, Double>> data, final int n) {
        List<XYChart.Data<Double, Double>> result = new ArrayList<>();

        int i = 0;
        for (var f : data) {
            double re = f.getYValue() * Math.cos(2 * Math.PI * i / n);
            double im = f.getYValue() * Math.sin(2 * Math.PI * i / n);
            i++;
            result.add(new XYChart.Data<>(re, im));
        }

        return result;
    }
}
