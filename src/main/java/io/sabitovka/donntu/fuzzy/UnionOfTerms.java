package io.sabitovka.donntu.fuzzy;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class UnionOfTerms implements Countable {
    private List<Countable> terms = new ArrayList<>();

    public double getMaxValue(double x) {
        return terms.stream().map(term -> term.getValue(x)).max(Double::compare).orElse(0.0);
    }

    @Override
    public double getValue(double x) {
        return getMaxValue(x);
    }
}
