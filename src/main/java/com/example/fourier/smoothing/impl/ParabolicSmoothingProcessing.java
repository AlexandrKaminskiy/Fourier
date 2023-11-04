package com.example.fourier.smoothing.impl;

import com.example.fourier.smoothing.SmoothingTypeProcessing;

import java.util.List;

public class ParabolicSmoothingProcessing extends SmoothingTypeProcessing {

    private static final double COEF = 1.0 / 231.0;

    @Override
    public double smooth(List<Double> values) {

        var a0 = values.get(0) * 5;
        var a1 = values.get(1) * (-30);
        var a2 = values.get(2) * 75;
        var a3 = values.get(3) * 131;
        var a4 = values.get(4) * 75;
        var a5 = values.get(5) * (-30);
        var a6 = values.get(6) * 5;

        return COEF * (a0 + a1 + a2 + a3 + a4 + a5 + a6);
    }

}
