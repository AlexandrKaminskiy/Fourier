package com.example.fourier.processing.impl;


import com.example.fourier.processing.CurveTypeProcessing;

public class RectangleTypeProcessing extends CurveTypeProcessing {

    @Override
    public double process(double t, int n, double a, double f, double fi) {
        return Math.sin(2 * Math.PI * f * t / n + fi) / Math.abs(Math.sin(2 * Math.PI * f * t / n + fi));
    }
}
