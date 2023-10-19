package com.example.fourier;

import com.example.fourier.model.FunctionInfo;
import com.example.fourier.model.FunctionState;
import com.example.fourier.processing.impl.RectangleTypeProcessing;
import com.example.fourier.processing.impl.SawCurveProcessingImpl;
import com.example.fourier.processing.impl.SinusoidalCurveProcessingImpl;
import com.example.fourier.processing.impl.TriangleTypeProcessing;
import com.example.fourier.stats.Calculator;
import com.example.fourier.stats.impl.AmplitudeCalculator;
import com.example.fourier.stats.impl.PhaseCalculator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    private final ObservableList<XYChart.Data<Double, Double>> baseSignal = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Data<Double, Double>> amplitude = FXCollections.observableArrayList();
    private final  ObservableList<XYChart.Data<Double, Double>> phase = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Data<Double, Double>> complex = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Data<Double, Double>> newSignal = FXCollections.observableArrayList();

    private final XYChart.Series<Double, Double> baseSeries = new XYChart.Series<>();
    private final XYChart.Series<Double, Double> phaseSeries = new XYChart.Series<>();
    private final XYChart.Series<Double, Double> complexSeries = new XYChart.Series<>();
    private final XYChart.Series<Double, Double> amplitudeSeries = new XYChart.Series<>();
    private final XYChart.Series<Double, Double> newSignalSeries = new XYChart.Series<>();

    private final Calculator amplitudeCalculator = new AmplitudeCalculator();
    private final Calculator phaseCalculator = new PhaseCalculator();

    private int nValue = 256;
    private int fValue = 1;
    private double fiValue = 0;
    private int aValue = 1;
    private int kValue = 100;
    private int newNValue = 1024;

    private final int aDivision = 5;
    private final int fDivision = 5;
    private final double fiDivision = 5;
    private final int nScale = 16;
    private final int kScale = 5;
    public void initialize() {

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
//            updatePlots();
        });

        fSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            fValue = newValue.intValue() / fDivision;
//            updatePlots();
        });

        fiSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            fiValue = newValue.intValue() / fiDivision;
//            updatePlots();
        });

        nSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            nValue = (int) Math.pow(2, Math.ceil(newValue.doubleValue() * 11 / 100));
            System.out.println(nValue);
            if (!containedFunctions.getItems().isEmpty()) {
                updatePlots();
            }
        });

        kSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            kValue = newValue.intValue();
            System.out.println(kValue);
            updatePlots();
        });

        recoveredNSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            newNValue = (int) Math.pow(2, Math.ceil(newValue.doubleValue() * 11 / 100));
            System.out.println(nValue);
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

//        updatePlots();
        chart.setAnimated(false);
        complexChart.setAnimated(false);
        amplitudeChart.setAnimated(false);
        phaseChart.setAnimated(false);
    }

    public void updatePlots() {
        clearData();

        baseSignal.addAll(BaseFunction.create(containedFunctions.getItems(), nValue));

        baseSeries.setData(baseSignal);

        chart.getData().add(baseSeries);


        complex.addAll(ComplexFunctionConverter.convert(baseSignal, nValue));

        amplitude.addAll(amplitudeCalculator.calculate(nValue, kValue, baseSignal));
        phase.addAll(phaseCalculator.calculate(nValue, kValue, baseSignal));


        complexSeries.setData(complex);
        complexChart.getData().add(complexSeries);

        amplitudeSeries.setData(amplitude);
        amplitudeChart.getData().add(amplitudeSeries);

        phaseSeries.setData(phase);
        phaseChart.getData().add(phaseSeries);


        newSignal.addAll(RecoveredFunction.recover(amplitude, phase, newNValue));

        newSignalSeries.setData(newSignal);
        chart.getData().add(newSignalSeries);
    }

    void clearData() {
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
