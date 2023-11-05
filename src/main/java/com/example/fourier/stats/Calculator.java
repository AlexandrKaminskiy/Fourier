package com.example.fourier.stats;

import com.example.fourier.model.Filter;
import com.example.fourier.model.FilterType;
import javafx.scene.chart.XYChart;

import java.util.List;

public interface Calculator {
    List<XYChart.Data<Double, Double>> calculate(final int n, final int k, final List<XYChart.Data<Double, Double>> data, FilterType filterType, final Filter filter);
}
