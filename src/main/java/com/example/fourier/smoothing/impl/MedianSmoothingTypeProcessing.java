package com.example.fourier.smoothing.impl;

import com.example.fourier.smoothing.SmoothingTypeProcessing;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MedianSmoothingTypeProcessing extends SmoothingTypeProcessing {

    private final int k;

    @Override
    public double smooth(List<Double> values) {
        List<Double> sortedValues = values.stream().sorted().toList();

        return sortedValues.subList(k, sortedValues.size() - k).stream()
            .mapToDouble(value -> value)
            .average()
            .orElse(Double.NaN);
    }
}
