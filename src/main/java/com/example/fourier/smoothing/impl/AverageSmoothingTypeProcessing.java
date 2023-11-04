package com.example.fourier.smoothing.impl;

import com.example.fourier.smoothing.SmoothingTypeProcessing;

import java.util.List;

public class AverageSmoothingTypeProcessing extends SmoothingTypeProcessing {

    @Override
    public double smooth(List<Double> values) {

        return values.stream().mapToDouble(value -> value).average().orElse(0);
    }
}
