package com.example.fourier;

import com.example.fourier.model.Filter;
import com.example.fourier.model.FilterType;
import com.example.fourier.model.FunctionInfo;
import com.example.fourier.model.FunctionState;
import com.example.fourier.model.SmoothingType;
import com.example.fourier.processing.impl.RectangleTypeProcessing;
import com.example.fourier.processing.impl.SawCurveProcessingImpl;
import com.example.fourier.processing.impl.SinusoidalCurveProcessingImpl;
import com.example.fourier.processing.impl.TriangleTypeProcessing;
import com.example.fourier.utils.SmoothUtils;
import com.example.fourier.stats.Calculator;
import com.example.fourier.stats.impl.AmplitudeCalculator;
import com.example.fourier.stats.impl.PhaseCalculator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    private LineChart<Double, Double> chart;

    @FXML
    private LineChart<Double, Double> amplitudeChart;

    @FXML
    private BarChart<String, Double> phaseChart;

    @FXML
    private LineChart<Double, Double> complexChart;

    @FXML
    private Slider nSlider;

    @FXML
    private Slider fSlider;

    @FXML
    private Slider aSlider;

    @FXML
    private Slider fiSlider;

    @FXML
    private Slider kSlider;

    @FXML
    private Slider recoveredNSlider;

    @FXML
    private ComboBox<FunctionState> availableFunctions;

    @FXML
    private TableView<FunctionInfo> containedFunctions;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;
    @FXML
    private Text nField;
    @FXML
    private Text aField;
    @FXML
    private Text fField;
    @FXML
    private Text kField;
    @FXML
    private Text fiField;
    @FXML
    private Text rnField;

    @FXML
    private ComboBox<SmoothingType> smoothingTypeBox;
    @FXML
    private ComboBox<Integer> frameBox;
    @FXML
    private ComboBox<FilterType> filterTypeBox;
    @FXML
    private ComboBox<Integer> fromFrequencyBox;
    @FXML
    private ComboBox<Integer> toFrequencyBox;

    private final ObservableList<XYChart.Data<Double, Double>> baseSignal = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Data<Double, Double>> smoothSignal = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Data<Double, Double>> amplitude = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Data<Double, Double>> phase = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Data<Double, Double>> complex = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Data<Double, Double>> newSignal = FXCollections.observableArrayList();

    private final XYChart.Series<Double, Double> baseSeries = new XYChart.Series<>();
    private final XYChart.Series<Double, Double> smoothSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Double> phaseSeries = new XYChart.Series<>();
    private final XYChart.Series<Double, Double> complexSeries = new XYChart.Series<>();
    private final XYChart.Series<Double, Double> amplitudeSeries = new XYChart.Series<>();
    private final XYChart.Series<Double, Double> newSignalSeries = new XYChart.Series<>();

    private final Calculator amplitudeCalculator = new AmplitudeCalculator();
    private final Calculator phaseCalculator = new PhaseCalculator();
    private final Filter filter = new Filter(0, 100);

    private int nValue = 256;
    private int fValue = 1;
    private double fiValue = 0;
    private int aValue = 1;
    private int kValue = 2;
    private int newNValue = 1024;

    private final int aDivision = 5;
    private final int fDivision = 5;
    private final double fiDivision = 5;
    private final int nScale = 16;
    private final int kScale = 5;

    public void initialize() {
        smoothingTypeBox.getItems().addAll(Arrays.asList(SmoothingType.values()));
        frameBox.getItems().addAll(List.of(1, 3, 5, 7, 9, 11));
        filterTypeBox.getItems().addAll(Arrays.asList(FilterType.values()));
        fromFrequencyBox.getItems().addAll(List.of(2, 3, 4, 5, 6, 7, 8, 9));
        toFrequencyBox.getItems().addAll(List.of(20, 21, 22, 23, 24, 25, 26, 27));

        smoothingTypeBox.valueProperty().addListener((observable, oldValue, newValue) -> updatePlots());
        frameBox.valueProperty().addListener((observable, oldValue, newValue) -> updatePlots());
        filterTypeBox.valueProperty().addListener((observable, oldValue, newValue) -> updatePlots());
        fromFrequencyBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            filter.setLowBound(newValue);
            updatePlots();
        });
        toFrequencyBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            filter.setHighBound(newValue);
            updatePlots();
        });

        TableColumn<FunctionInfo, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<FunctionInfo, Double> aColumn = new TableColumn<>("A");
        aColumn.setCellValueFactory(new PropertyValueFactory<>("a"));

        TableColumn<FunctionInfo, Double> fColumn = new TableColumn<>("F");
        fColumn.setCellValueFactory(new PropertyValueFactory<>("f"));

        TableColumn<FunctionInfo, Double> fiColumn = new TableColumn<>("Fi");
        fiColumn.setCellValueFactory(new PropertyValueFactory<>("fi"));
        containedFunctions.getColumns().addAll(nameColumn, aColumn, fColumn, fiColumn);

        availableFunctions.getItems().addAll(
            new FunctionState("Sin", new SinusoidalCurveProcessingImpl()),
            new FunctionState("Triangle", new TriangleTypeProcessing()),
            new FunctionState("Rectangle", new RectangleTypeProcessing()),
            new FunctionState("Saw", new SawCurveProcessingImpl())
        );

        aSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            aValue = newValue.intValue() / aDivision;
            aField.setText("A = " + aValue);
        });

        fSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            fValue = newValue.intValue();
            fField.setText("F = " + fValue);
        });

        fiSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            fiValue = newValue.intValue() / 100.0 * Math.PI;
            fiField.setText("Fi = " + fiValue);
        });

        nSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            nValue = (int) Math.pow(2, Math.ceil(newValue.doubleValue() * 11 / 100));
            nField.setText("N = " + nValue);
            if (!containedFunctions.getItems().isEmpty()) {
                updatePlots();
            }
        });

        kSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int res = (int) (Math.log(nValue) / Math.log(2.0));
            kValue = (int) Math.pow(2, Math.ceil(newValue.doubleValue() * res / 100));

//            kValue = (int) (newValue.doubleValue() / 100.0 * nValue);
            kField.setText("K = " + kValue);
            updatePlots();
        });

        recoveredNSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            newNValue = (int) Math.pow(2, Math.ceil(newValue.doubleValue() * 11 / 100));
            rnField.setText("Recovered N = " + newNValue);
            updatePlots();
        });

        addButton.setOnAction(event -> {
            FunctionState value = availableFunctions.getValue();
            containedFunctions.getItems().add(new FunctionInfo(value.getTitle(), value.getProcessing(), aValue, fValue, fiValue));
            updatePlots();
        });

        removeButton.setOnAction(event -> {
            FunctionInfo selectedItem = containedFunctions.getSelectionModel().getSelectedItem();
            containedFunctions.getItems().remove(selectedItem);
            updatePlots();
        });

        chart.setAnimated(false);
        complexChart.setAnimated(false);
        amplitudeChart.setAnimated(false);
        phaseChart.setAnimated(false);
    }

    public void updatePlots() {
        clearData();

        if (containedFunctions.getItems().isEmpty()) {
            return;
        }
        List<XYChart.Data<Double, Double>> base = BaseFunction.create(containedFunctions.getItems(), nValue);

        smoothSignal.addAll(SmoothUtils.getSmooth(base, smoothingTypeBox.getValue(), frameBox.getValue()));
        smoothSeries.setData(smoothSignal);


        baseSignal.addAll(base);
        baseSeries.setData(baseSignal);

        chart.getData().add(baseSeries);


        complex.addAll(ComplexFunctionConverter.convert(smoothSignal, nValue));

        amplitude.addAll(amplitudeCalculator.calculate(nValue, kValue, smoothSignal, filterTypeBox.getValue(), filter));
        phase.addAll(phaseCalculator.calculate(nValue, kValue, smoothSignal, filterTypeBox.getValue(), filter));


        complexSeries.setData(complex);
        complexChart.getData().add(complexSeries);

        amplitudeSeries.setData(amplitude);
        amplitudeChart.getData().add(amplitudeSeries);

        phaseSeries.setData(phase.stream().map(ddd -> new XYChart.Data<>(ddd.getXValue().toString(), ddd.getYValue()))
            .collect(Collectors.toCollection(FXCollections::observableArrayList)));

        phaseChart.getData().add(phaseSeries);


        newSignal.addAll(RecoveredFunction.recover(amplitude, phase, nValue));

        newSignalSeries.setData(newSignal);
        chart.getData().add(newSignalSeries);
        chart.getData().add(smoothSeries);
    }

    void clearData() {

        smoothSeries.getData().clear();
        baseSeries.getData().clear();
        complexSeries.getData().clear();
        phaseSeries.getData().clear();
        amplitudeSeries.getData().clear();
        newSignalSeries.getData().clear();

        chart.getData().clear();
        phaseChart.getData().clear();
        complexChart.getData().clear();
        amplitudeChart.getData().clear();

        baseSignal.clear();
        complex.clear();
        phase.clear();
        amplitude.clear();
        newSignal.clear();


    }
}
