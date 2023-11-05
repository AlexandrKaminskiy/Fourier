package com.example.fourier.utils;

import com.example.fourier.model.SmoothingType;
import com.example.fourier.smoothing.SmoothingTypeProcessing;
import com.example.fourier.smoothing.impl.AverageSmoothingTypeProcessing;
import com.example.fourier.smoothing.impl.MedianSmoothingTypeProcessing;
import com.example.fourier.smoothing.impl.ParabolicSmoothingProcessing;
import javafx.scene.chart.XYChart;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.IntStream;

@UtilityClass
public class SmoothUtils {

    public static List<XYChart.Data<Double, Double>> getSmooth(List<XYChart.Data<Double, Double>> base, SmoothingType smoothingType, int frame) {

        int halfFrame = frame / 2;
        var typeProcessing = switch (smoothingType) {
            case PARABOLIC -> {
                halfFrame = 3;
                yield new ParabolicSmoothingProcessing();
            }
            case MEDIAN -> new MedianSmoothingTypeProcessing(halfFrame);
            case AVERAGE -> new AverageSmoothingTypeProcessing();
            case NONE -> null;
        };

        if (typeProcessing == null) {
            return base;
        }

        int finalHalfFrame = halfFrame;
        return IntStream.range(0, base.size()).boxed()
            .map(integer -> {
                if (integer - finalHalfFrame < 0 || integer + finalHalfFrame + 1 > base.size()) {
                    return new XYChart.Data<>(base.get(integer).getXValue(), base.get(integer).getYValue());
                }
                List<Double> list = base.subList(integer - finalHalfFrame, integer + finalHalfFrame + 1).stream().map(XYChart.Data::getYValue).toList();
                double smooth = typeProcessing.smooth(list);
                return new XYChart.Data<>(base.get(integer).getXValue(), smooth);
            }).toList();
    }
}
