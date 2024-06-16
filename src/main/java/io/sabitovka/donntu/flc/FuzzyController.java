package io.sabitovka.donntu.flc;

import io.sabitovka.donntu.common.Watchable;
import io.sabitovka.donntu.fuzzy.ActivatedTerm;
import io.sabitovka.donntu.fuzzy.Term;
import io.sabitovka.donntu.fuzzy.UnionOfTerms;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

@AllArgsConstructor
public class FuzzyController {
    public enum ACTIVATION_METHODS { MIN, AVG, PROD };

    private List<Rule> rules;
    private int[] inputData;
    private ACTIVATION_METHODS activationMethod;
    private Watchable watcher;

    public int run() {
        List<Double> fuzzificated = fuzzification(inputData);
        List<Double> aggregated = aggregation(fuzzificated);
        List<ActivatedTerm> activated = activation(aggregated);
        UnionOfTerms accumulated = accumulation(activated);
        return (int) defuzzification(accumulated);
    }

    private List<Double> fuzzification(int[] inputData) {
        List<Double> b = new ArrayList<>();

        for (Rule rule : rules) {
            for (Condition condition : rule.getConditions()) {
                Term term = condition.getTerm();
                double y = term.calculateMembership(inputData[condition.getIndex()]);
                b.add(y);
            }
        }
        return b;
    }

    private List<Double> aggregation(List<Double> b) {
        int i = 0;
        int j = 0;
        List<Double> c = new ArrayList<>();
        for (Rule rule : rules) {
            double truthOfConditions = 1.0;
            for (Condition ignored : rule.getConditions()) {
                truthOfConditions = Math.min(truthOfConditions, b.get(i));
                i++;
            }
            c.add(truthOfConditions);
        }
        return c;
    }

    private List<ActivatedTerm> activation(List<Double> c) {
        int i = 0;
        List<ActivatedTerm> activatedTerms = new ArrayList<>();
        for (Rule rule : rules) {
            activatedTerms.add(new ActivatedTerm(
                    rule.getConclusion().getTerm(), c.get(i) * rule.getConclusion().getWeight()
            ));
            i++;
        }
        return activatedTerms;
    }


    private UnionOfTerms accumulation(List<ActivatedTerm> activatedFuzzySets) {
        UnionOfTerms unionOfTerms = new UnionOfTerms();
        int i = 0;
        for (Rule rule : rules) {
            unionOfTerms.getTerms().add(activatedFuzzySets.get(i));
            watcher.drawTerms(unionOfTerms.getTerms(), LinguisticData.getInstance().getLinguisticVariables().get(2).getUniversalSet());
            i++;
        }
        return unionOfTerms;
    }

    private double defuzzification(UnionOfTerms unionOfTerms) {
        double i1 = integral(unionOfTerms, true);
        double i2 = integral(unionOfTerms, false);
        return i1 / i2;
    }

    private double minActivation(double ci, double ny) {
        return Math.min(ci, ny);
    }

    private double prodActivation(double ci, double ny) {
        return ci * ny;
    }

    private double avgActivation(double ci, double ny) {
        return 0.5 * (ci + ny);
    }

    private double activate(double ci, double ny) {
        switch (activationMethod) {
            case AVG:
                return avgActivation(ci, ny);
            case MIN:
                return minActivation(ci, ny);
            case PROD:
                return prodActivation(ci, ny);
            default:
                throw new IllegalArgumentException("Неизвестный тип функции активации");
        }
    }

    private double integral(UnionOfTerms terms, Boolean useX) {
        return integrate(1200, 5000, (x) -> (useX ? x * terms.getValue(x) : terms.getValue(x)));
    }

    private static double integrate(double a, double b, DoubleUnaryOperator f) {
        final double INC = 0.5;

        double area = 0;
        double modifier = 1;
        if(a > b) {
            double tempA = a;
            a = b;
            b = tempA;
            modifier = -1;
        }
        for(double i = a + INC; i < b; i += INC) {
            double dFromA = i - a;
            area += (INC / 2) * (f.applyAsDouble(a + dFromA) + f.applyAsDouble(a + dFromA - INC));
        }
        return (Math.round(area * 1000.0) / 1000.0) * modifier;
    }
}
