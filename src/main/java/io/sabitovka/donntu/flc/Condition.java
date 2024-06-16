package io.sabitovka.donntu.flc;

import io.sabitovka.donntu.fuzzy.Term;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Condition {
    private Term term;
    private int index;
}
