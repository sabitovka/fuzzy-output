package io.sabitovka.donntu.fuzzy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ActivatedTerm implements Countable {
    private Countable term;
    private double truthDegree;

    private double getActivatedValue(double x) {
        return Math.min(term.getValue(x), truthDegree);
    }

    @Override
    public double getValue(double x) {
        return getActivatedValue(x);
    }
}
