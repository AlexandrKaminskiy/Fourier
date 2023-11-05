package com.example.fourier.utils;

import com.example.fourier.model.Filter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FilterUtils {

    public static boolean filter(int i, int n, Filter filter) {
        int naikvLowBound = n - filter.getLowBound();
        int naikvHighBound = n - filter.getHighBound();


        if (i <= n / 2 && i > filter.getLowBound() && i < filter.getHighBound()) return true;

        if (i > n / 2 && i > naikvHighBound && i < naikvLowBound) return true;

        return false;
    }
}
