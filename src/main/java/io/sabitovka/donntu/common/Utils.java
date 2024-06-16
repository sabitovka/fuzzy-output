package io.sabitovka.donntu.common;

import io.sabitovka.donntu.fuzzy.Countable;
import io.sabitovka.donntu.fuzzy.LinguisticVariable;
import io.sabitovka.donntu.fuzzy.Term;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.List;

public final class Utils {
    public static void drawFuzzyChart(LinguisticVariable linguisticVariable, LineChart<Number, Number> chart) {
        chart.setTitle(linguisticVariable.getName());
        chart.setCreateSymbols(false);

        NumberAxis xAxis = (NumberAxis) chart.getXAxis();
        xAxis.setLabel("Входное значение");

        NumberAxis yAxis = (NumberAxis) chart.getYAxis();
        yAxis.setLabel("Значение функции принадлежности");

        drawTerms(linguisticVariable.getTerms(), linguisticVariable.getUniversalSet(), chart);
    }

    public static void drawTerms(List<? extends Countable> terms, int[] universalSet, LineChart<Number, Number> chart) {
        for (Countable term : terms) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            if (term instanceof Term) {
                series.setName(((Term) term).getName());
            }

            for (int i = 0; i < universalSet.length; i += 3) {
                int x = universalSet[i];
                series.getData().add(new XYChart.Data<>(x, term.getValue(x)));
            }

            chart.getData().add(series);
        }
    }

    public static void drawTermPoints(List<Term> terms, double x, LineChart<Number, Number> chart) {
        for (Term term : terms) {
            double y = term.getValue(x);
            if (y == 0) continue;
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>(x, y));
            series.setName("X-" + term.getName());

            chart.getData().add(series);
        }
    }
}
