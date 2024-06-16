package io.sabitovka.donntu.controllers;

import io.sabitovka.donntu.common.Utils;
import io.sabitovka.donntu.common.Watchable;
import io.sabitovka.donntu.flc.FuzzyController;
import io.sabitovka.donntu.flc.LinguisticData;
import io.sabitovka.donntu.fuzzy.Countable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainController implements Watchable {
    @FXML
    Spinner<Integer> intensity;
    @FXML
    LineChart<Number, Number> intenseChart;
    @FXML
    Spinner<Integer> ramAmount;
    @FXML
    LineChart<Number, Number> ramChart;
    @FXML
    LineChart<Number, Number> cpuChart;
    @FXML
    TextField resultTextField;

    private Stage stage;

    private MainController(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        SpinnerValueFactory.IntegerSpinnerValueFactory intFact = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 0);
        intensity.setValueFactory(intFact);

        SpinnerValueFactory.IntegerSpinnerValueFactory intFact2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 8196, 0);
        ramAmount.setValueFactory(intFact2);
    }

    @FXML
    void actionOpenSettings() {
        Stage settingsStage = SettingsController.createView(new Stage());
        settingsStage.initOwner(stage);
        settingsStage.initModality(Modality.APPLICATION_MODAL);
        settingsStage.show();
    }

    @FXML
    void actionCalculate() {
        FuzzyController fc = new FuzzyController(LinguisticData.getInstance().getRules(), new int[] { intensity.getValue(), ramAmount.getValue() }, FuzzyController.ACTIVATION_METHODS.MIN, this);
        int result = fc.run();

        resultTextField.textProperty().set(String.valueOf(result));

        intenseChart.getData().clear();
        ramChart.getData().clear();
        cpuChart.getData().clear();

        Utils.drawFuzzyChart(LinguisticData.getInstance().getLinguisticVariables().get(0), intenseChart);
        Utils.drawTermPoints(LinguisticData.getInstance().getLinguisticVariables().get(0).getTerms(), intensity.getValue(), intenseChart);

        Utils.drawFuzzyChart(LinguisticData.getInstance().getLinguisticVariables().get(1), ramChart);
        Utils.drawTermPoints(LinguisticData.getInstance().getLinguisticVariables().get(1).getTerms(), ramAmount.getValue(), ramChart);

        Utils.drawFuzzyChart(LinguisticData.getInstance().getLinguisticVariables().get(2), cpuChart);
        Utils.drawTermPoints(LinguisticData.getInstance().getLinguisticVariables().get(2).getTerms(), result, cpuChart);
    }

    public static Stage createView(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainController.class.getResource("/views/fxml/main.fxml"));
        try {
            loader.setController(new MainController(primaryStage));
            Parent root = loader.load();
            primaryStage.setTitle("Курсовой проект по дисциплине НМОАД");
            primaryStage.setScene(new Scene(root, 1273, 475));
            return primaryStage;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return primaryStage;
    }

    @Override
    public void drawTerms(List<? extends Countable> terms, int[] universalSet) {

    }
}
