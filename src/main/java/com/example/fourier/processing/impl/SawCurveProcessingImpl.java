package com.example.fourier.processing.impl;


import com.example.fourier.processing.CurveTypeProcessing;

public class SawCurveProcessingImpl extends CurveTypeProcessing {

    @Override
    public double process(double t, int n, double a, double f, double fi) {
        return a / Math.PI * ((2 * Math.PI * f * t / n + fi) % (2 * Math.PI)) - a;
    }
}
