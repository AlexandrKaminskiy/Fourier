package com.example.fourier;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Complex {

    private double re;
    private double im;

    public static Complex of(int n, int r, double[] f) {
        double sumRe = 0;
        for (int i = 0; i < n; i++) {
            sumRe += f[i] * Math.cos(2 * Math.PI * n * r / n);
        }

        double re = 2.0 / n * sumRe;

        double sumIm = 0;
        for (int i = 0; i < n; i++) {
            sumIm += f[i] * Math.sin(2 * Math.PI * n * r / n);
        }

        double im = 2.0 / n * sumIm;

        return new Complex(re, im);
    }
}
