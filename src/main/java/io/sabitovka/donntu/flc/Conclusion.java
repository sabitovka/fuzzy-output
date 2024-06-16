package io.sabitovka.donntu.flc;

import io.sabitovka.donntu.fuzzy.Term;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Conclusion {
    public static final double DEFAULT_WEIGHT = 1.0;
    private Term term;
    private double weight;
}
