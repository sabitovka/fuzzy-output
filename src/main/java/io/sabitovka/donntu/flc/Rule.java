package io.sabitovka.donntu.flc;

import io.sabitovka.donntu.fuzzy.Term;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Rule {
    private List<Condition> conditions;
    private Conclusion conclusion;
}
