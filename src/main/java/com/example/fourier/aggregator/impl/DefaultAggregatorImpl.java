package com.example.fourier.aggregator.impl;


import com.example.fourier.aggregator.CurveAggregator;
import com.example.fourier.processing.CurveTypeProcessing;

import java.util.HashMap;
import java.util.Map;

public class DefaultAggregatorImpl extends CurveAggregator {

    public DefaultAggregatorImpl(CurveTypeProcessing curveTypeProcessing) {
        super(curveTypeProcessing);
    }

    @Override
    public Map<Integer, Double> aggregate(int n, double a, double f, double fi) {
        Map<Integer, Double> values = new HashMap<>();

        for (int i = 0; i < n; i++) {
            values.put(i, curveTypeProcessing.process(i, n, a, f, fi));
        }
        return values;
    }

}
