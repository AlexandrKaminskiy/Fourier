package com.example.fourier;

import com.example.fourier.processing.impl.RectangleTypeProcessing;
import com.example.fourier.processing.impl.SawCurveProcessingImpl;
import com.example.fourier.processing.impl.SinusoidalCurveProcessingImpl;
import com.example.fourier.stats.Calculator;
import com.example.fourier.stats.impl.AmplitudeCalculator;
import com.example.fourier.stats.impl.PhaseCalculator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;

import java.util.List;
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

        ObservableList<XYChart.Data<Double, Double>> amplitude = FXCollections.observableArrayList();
        ObservableList<XYChart.Data<Double, Double>> phase = FXCollections.observableArrayList();
        ObservableList<XYChart.Data<Double, Double>> complex = FXCollections.observableArrayList();
        ObservableList<XYChart.Data<Double, Double>> newSignal = FXCollections.observableArrayList();

        final Calculator amplitudeCalculator = new AmplitudeCalculator();
        final Calculator phaseCalculator = new PhaseCalculator();
        complex.addAll(ComplexFunctionConverter.convert(collected, n));

        amplitude.addAll(amplitudeCalculator.calculate(n, 100, collected));
        phase.addAll(phaseCalculator.calculate(n, 100, collected));


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

        XYChart.Series ns = new XYChart.Series();

        newSignal.addAll(RecoveredFunction.recover(amplitude, phase, aN));

        ns.setData(newSignal);
        chart.getData().add(ns);
    }
}

