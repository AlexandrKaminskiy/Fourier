package com.example.fourier;

import com.example.fourier.processing.impl.RectangleTypeProcessing;
import com.example.fourier.processing.impl.SawCurveProcessingImpl;
import com.example.fourier.processing.impl.SinusoidalCurveProcessingImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Controller {

    @FXML
    private LineChart<Double, Double> chart;

    @FXML
    private LineChart<Double, Double> amplitudeChart;

    @FXML
    private LineChart<Double, Double> phaseChart;

    @FXML
    private LineChart<Double, Double> complexChart;

    @FXML
    private Slider nSlider;

    @FXML
    private Slider fSlider;

    @FXML
    private Slider aSlider;

    @FXML
    private Slider kSlider;

    @FXML
    private TableView containedFunctions;

    @FXML
    private ComboBox availableFunctions;

    public void initialize() {

        XYChart.Series series = new XYChart.Series();
        var curveProcessing = new SinusoidalCurveProcessingImpl();

        var n = 1024;
        var inc = 1 / (double) n;

        var collected = IntStream.range(0, n)
            .boxed()
            .map(val -> new XYChart.Data<>(val * inc, curveProcessing.process(val, n, 1, 5, 1)))
            .collect(Collectors.toCollection(FXCollections::observableArrayList));
        series.setData(collected);

        chart.getData().add(series);

        ObservableList<XYChart.Data<Integer, Double>> amplitude = FXCollections.observableArrayList();
        ObservableList<XYChart.Data<Integer, Double>> phase = FXCollections.observableArrayList();
        ObservableList<XYChart.Data<Double, Double>> complex = FXCollections.observableArrayList();

//        for (int i = 0; i < 100; i++) {
//            double sum = 0;
//            for (var f : collected) {
//                sum += f.getYValue() * Math.exp(-f.getXValue() * i);
//            }
//            sum /= Math.sqrt(2 * Math.PI);
//            objects.add(new XYChart.Data<>((double) i, sum));
//        }

        int j1 = 0;
        for (var f : collected) {

            double re = f.getYValue() * Math.cos(2 * Math.PI * j1 / n);
            double im = f.getYValue() * Math.sin(2 * Math.PI * j1 / n);
            j1++;
            complex.add(new XYChart.Data<>(re, im));
        }

        for (int i = 0; i < 100; i++) {
            double sumRe = 0;
            int j = 0;
            for (var f : collected) {
                sumRe += f.getYValue() * Math.cos(2 * Math.PI * i * j / n);
                j++;
            }

            double re = 2.0 / n * sumRe;
            j = 0;
            double sumIm = 0;
            for (var f : collected) {
                sumIm += f.getYValue() * Math.sin(2 * Math.PI * i * j / n);
                j++;
            }

            double im = 2.0 / n * sumIm;
            double fi = Math.atan(re / im);
            double a = Math.sqrt(re * re + im * im);
//            double x = a * Math.cos(fi);
//            double y = a * Math.sin(fi);

            amplitude.add(new XYChart.Data<>(i, a));
            phase.add(new XYChart.Data<>(i, fi));
//            complex.add(new XYChart.Data<>(x, y));

        }
        XYChart.Series comp = new XYChart.Series();
        comp.setData(complex);
//        complexChart.setAnimated(true);
        complexChart.getData().add(comp);

        XYChart.Series am = new XYChart.Series();
        am.setData(amplitude);
        amplitudeChart.getData().add(am);

        XYChart.Series ph = new XYChart.Series();
        ph.setData(phase);
        phaseChart.getData().add(ph);

        int aN = 2048;
        double newInc = 1 / (double) aN;

        XYChart.Series series1 = new XYChart.Series();
        ObservableList<XYChart.Data<Double, Double>> newSignal = FXCollections.observableArrayList();
        for (int i = 0; i < aN; i++)
        {
            double value = amplitude.get(0).getYValue() / 2;
            for (int j = 0; j < amplitude.size() / 2; j++)
            {
                value += amplitude.get(j).getYValue() * Math.sin(2 * Math.PI * i * j / aN + phase.get(j).getYValue());
            }

            newSignal.add(new XYChart.Data<>(i * newInc, value));
        }

        series1.setData(newSignal);
        chart.getData().add(series1);
    }
}

