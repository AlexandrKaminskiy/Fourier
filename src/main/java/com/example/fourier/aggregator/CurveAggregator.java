package com.example.fourier.aggregator;


import com.example.fourier.processing.CurveTypeProcessing;

import java.util.Map;

public abstract class CurveAggregator {

    protected final CurveTypeProcessing curveTypeProcessing;

    protected CurveAggregator(CurveTypeProcessing curveTypeProcessing) {
        this.curveTypeProcessing = curveTypeProcessing;
    }

    public abstract Map<Integer, Double> aggregate(int n, double a, double f, double fi);
}
