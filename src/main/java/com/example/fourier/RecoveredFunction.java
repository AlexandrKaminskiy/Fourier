package com.example.fourier;

import javafx.collections.FXCollections;
import javafx.scene.chart.XYChart;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class RecoveredFunction {

    public static List<XYChart.Data<Double, Double>> recover(final List<XYChart.Data<Double, Double>> amplitudes,
                                                             final List<XYChart.Data<Double, Double>> phases,
                                                             final int n) {

        final List<XYChart.Data<Double, Double>> newSignal = FXCollections.observableArrayList();
        final double newInc = 1.0 / n;
        for (int i = 0; i < n; i++) {
            double value = amplitudes.get(0).getYValue() / 2;
            for (int j = 0; j < amplitudes.size() / 2; j++) {
                value += amplitudes.get(j).getYValue() * Math.sin(2.0 * Math.PI * i * j / n + phases.get(j).getYValue());
            }

            newSignal.add(new XYChart.Data<>(i * newInc, value));
        }

        return newSignal;
    }

}
