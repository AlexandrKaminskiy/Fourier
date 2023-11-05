package com.example.fourier.processing.impl;


import com.example.fourier.processing.CurveTypeProcessing;

import java.util.List;

public class RectangleTypeProcessing extends CurveTypeProcessing {

    @Override
    public double process(double t, int n, double a, double f, double fi) {
        List<Integer> rands = ksi.ints(0, 2).limit(21).boxed().toList();
        int j = 50;
        double sum = 0.0;
        for (var r : rands) {
            sum += Math.pow(-1, r) * (a / 50) * Math.sin(2 * Math.PI * t * j / n);
            j++;
        }
        return Math.sin(2 * Math.PI * f * t / n + fi) / Math.abs(Math.sin(2 * Math.PI * f * t / n + fi)) + sum;
    }
}
