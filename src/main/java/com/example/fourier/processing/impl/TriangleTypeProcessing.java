package com.example.fourier.processing.impl;

import com.example.fourier.processing.CurveTypeProcessing;

public class TriangleTypeProcessing extends CurveTypeProcessing {
    @Override
    public double process(double t, int n, double a, double f, double fi) {

        return (2 * a / Math.PI) * Math.abs(Math.abs((((2 * Math.PI * f * t) / n + fi - (Math.PI / 2)) % (2 * Math.PI))) - Math.PI) - a;
    }
}
