package com.example.fourier.smoothing;

import javafx.scene.chart.XYChart;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

@UtilityClass
public class SmoothUtils {



    public static List<XYChart.Data<Double, Double>> getSmooth(List<XYChart.Data<Double, Double>> base, SmoothingTypeProcessing typeProcessing) {

        return IntStream.range(0, base.size()).boxed()
            .map(integer -> {
                if (integer - 3 < 0 || integer + 3 > base.size())  {
                    return new XYChart.Data<>(base.get(integer).getXValue(), base.get(integer).getYValue());
                }
                List<Double> list = base.subList(integer - 3, integer + 3).stream().map(XYChart.Data::getYValue).toList();
                double smooth = typeProcessing.smooth(list);
                return new XYChart.Data<>(base.get(integer).getXValue(), smooth);
            }).toList();
    }
}
