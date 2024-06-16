package io.sabitovka.donntu.fuzzy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class LinguisticVariable {

    @Getter
    private String name;
    @Setter
    @Getter
    private int[] universalSet;
    @Getter
    private List<Term> terms;

    public LinguisticVariable(String name) {
        this.name = name;
        terms = new ArrayList<>();
    }

    public void addTerm(Term term) {
        terms.add(term);
    }

}
