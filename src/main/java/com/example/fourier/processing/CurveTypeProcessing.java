package com.example.fourier.processing;

import java.util.Random;

public abstract class CurveTypeProcessing {
    protected Random ksi = new Random();
    public abstract double process(double t, int n, double a, double f, double fi);

}
