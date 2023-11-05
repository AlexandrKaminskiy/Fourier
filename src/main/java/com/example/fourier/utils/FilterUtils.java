package com.example.fourier.utils;

import com.example.fourier.model.Filter;
import com.example.fourier.model.FilterType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FilterUtils {

    public static boolean filter(int i, int n, Filter filter, FilterType filterType) {

        int naikvLowBound = n - filter.getLowBound();
        int naikvHighBound = n - filter.getHighBound();

        return switch (filterType) {
            case HIGH_FREQUENCY -> high(i, n, filter.getHighBound(), naikvHighBound);
            case LOW_FREQUENCY -> low(i, n, filter.getLowBound(), naikvLowBound);
            case BAND_PASS -> low(i, n, filter.getLowBound(), naikvLowBound) && high(i, n, filter.getHighBound(), naikvHighBound);
            case NONE -> true;
        };
    }

    private boolean low(int i, int n, int low, int naikvLow) {
        return (i <= n / 2 && i > low) || (i > n / 2 && i < naikvLow);
    }

    private boolean high(int i, int n, int high, int naikvHigh) {
        return (i <= n / 2 && i < high) || (i > n / 2 && i > naikvHigh);
    }

}
