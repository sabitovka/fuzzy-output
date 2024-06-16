package io.sabitovka.donntu.common;

import io.sabitovka.donntu.fuzzy.Countable;
import io.sabitovka.donntu.fuzzy.UnionOfTerms;

import java.util.List;

public interface Watchable {
    void drawTerms(List<? extends Countable> terms, int[] universalSet);
}
