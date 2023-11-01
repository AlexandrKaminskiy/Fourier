package com.example.fourier;

import com.example.fourier.model.FunctionInfo;
import javafx.scene.chart.XYChart;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.IntStream;

@UtilityClass
public class BaseFunction {

    public static List<XYChart.Data<Double, Double>> create(final List<FunctionInfo> curves,
                                                            final int n) {

        final double inc = 1 / (double) n;

        return IntStream.range(1, n + 1)
            .boxed()
            .map(val -> curves.stream()
                .map(curve -> curve.getProcessing().process(val, n, curve.getA(), curve.getF(), curve.getFi()))
                .reduce(Double::sum)
                .map(operand -> new XYChart.Data<>((double) val * inc, operand))
                .orElse(null)
            )
            .toList();
    }

}
