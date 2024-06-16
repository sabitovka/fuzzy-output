package io.sabitovka.donntu.controllers;

import io.sabitovka.donntu.flc.LinguisticData;
import io.sabitovka.donntu.common.Utils;
import io.sabitovka.donntu.fuzzy.LinguisticVariable;
import io.sabitovka.donntu.fuzzy.Term;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;

public class SettingsController {

    @FXML
    LineChart<Number, Number> intensityChart;
    @FXML
    TableView<Term> intensityTable;
    @FXML
    LineChart<Number, Number> ramChart;
    @FXML
    TableView<Term> ramTable;
    @FXML
    LineChart<Number, Number> freqChart;
    @FXML
    TableView<Term> cpuTable;

    private Stage stage;

    public SettingsController(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        actionRecalculate();

        initTable(LinguisticData.getInstance().getLinguisticVariables().get(0), intensityTable);
        initTable(LinguisticData.getInstance().getLinguisticVariables().get(1), ramTable);
        initTable(LinguisticData.getInstance().getLinguisticVariables().get(2), cpuTable);
    }

    @FXML
    public void actionRecalculate() {
        intensityChart.getData().clear();
        ramChart.getData().clear();
        freqChart.getData().clear();

        LinguisticVariable intensity = LinguisticData.getInstance().getLinguisticVariables().get(0);
        Utils.drawFuzzyChart(intensity, intensityChart);

        LinguisticVariable ramAmount = LinguisticData.getInstance().getLinguisticVariables().get(1);
        Utils.drawFuzzyChart(ramAmount, ramChart);

        LinguisticVariable cpuFreq = LinguisticData.getInstance().getLinguisticVariables().get(2);
        Utils.drawFuzzyChart(cpuFreq, freqChart);
    }

    private TableColumn<Term, Integer> createParamColumn(String paramValue) {
        TableColumn<Term, Integer> column = new TableColumn<>(paramValue);
        column.setCellValueFactory(new PropertyValueFactory<>(paramValue));
        column.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        column.setOnEditCommit(event -> {
            try {
                Term term = event.getRowValue();
                Class<?> clazz = Class.forName(Term.class.getName());
                Method paramField = clazz.getMethod("set" + paramValue, int.class);
                paramField.invoke(term, event.getNewValue());
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                     ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        column.setEditable(true);
        return column;
    }

    public void initTable(LinguisticVariable variable, TableView<Term> tableView) {
        tableView.getColumns().clear();

        TableColumn<Term, String> nameColumn = new TableColumn<>("Название");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.getColumns().add(nameColumn);

        TableColumn<Term, Term.MembershipFunctionType> membershipColumn = new TableColumn<>("Функция принадлежности");
        ObservableList<Term.MembershipFunctionType> functionTypes = FXCollections.observableArrayList(Term.MembershipFunctionType.values());
        membershipColumn.setCellValueFactory(termMembershipFunctionTypeCellDataFeatures -> {
            Term term = termMembershipFunctionTypeCellDataFeatures.getValue();
            return new SimpleObjectProperty<>(term.getMembershipFunctionType());
        });
        membershipColumn.setCellFactory(ComboBoxTableCell.forTableColumn(functionTypes));
        membershipColumn.setOnEditCommit(event -> {
            TablePosition<Term, Term.MembershipFunctionType> pos = event.getTablePosition();
            Term.MembershipFunctionType newValue = event.getNewValue();
            int row =  pos.getRow();

            Term term = event.getTableView().getItems().get(row);

            term.setMembershipFunctionType(newValue);
        });
        tableView.getColumns().add(membershipColumn);

        TableColumn<Term, Integer> left = createParamColumn("Left");
        tableView.getColumns().add(left);

        TableColumn<Term, Integer> centerLeft = createParamColumn("CenterLeft");
        tableView.getColumns().add(centerLeft);

        TableColumn<Term, Integer> centerRight = createParamColumn("CenterRight");
        tableView.getColumns().add(centerRight);

        TableColumn<Term, Integer> right = createParamColumn("Right");
        tableView.getColumns().add(right);

        ObservableList<Term> terms = FXCollections.observableArrayList(variable.getTerms());
        tableView.setItems(terms);
    }

    public static Stage createView(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainController.class.getResource("/views/fxml/settings.fxml"));
        try {
            loader.setController(new SettingsController(primaryStage));
            Parent root = loader.load();
            primaryStage.setTitle("Настройка лингвистических переменных");
            primaryStage.setScene(new Scene(root, 800, 600));
            return primaryStage;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return primaryStage;
    }
}
