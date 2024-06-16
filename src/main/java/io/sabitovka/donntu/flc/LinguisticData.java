package io.sabitovka.donntu.flc;

import io.sabitovka.donntu.fuzzy.LinguisticVariable;
import io.sabitovka.donntu.fuzzy.Term;
import lombok.Getter;

import java.util.*;
import java.util.stream.IntStream;

public final class LinguisticData {
    private static LinguisticData INSTANCE = new LinguisticData();
    @Getter
    private List<LinguisticVariable> linguisticVariables;
    @Getter
    private List<Rule> rules;

    private LinguisticData() {
        linguisticVariables = new ArrayList<>();
        rules = new ArrayList<>();
        createLinguisticVariables();
        createRules();
    }

    public static synchronized LinguisticData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LinguisticData();
        }
        return INSTANCE;
    }

    private void createLinguisticVariables() {
        LinguisticVariable intensityOfRequests = new LinguisticVariable("Интенсивность обращений к серверу (запросов/мин)");
        intensityOfRequests.setUniversalSet(IntStream.rangeClosed(0, 999).toArray());
        linguisticVariables.add(intensityOfRequests);

        // Терм "Умеренная интенсивность"
        intensityOfRequests.addTerm(new Term(
                "Умеренная интенсивность",
                0, 300, 500, 600,
                Term.MembershipFunctionType.QUADRATIC
        ));

        // Терм "Высокая интенсивность"
        intensityOfRequests.addTerm(new Term(
                "Высокая интенсивность",
                500, 550, 800, 900,
                Term.MembershipFunctionType.LINEAR
        ));

        // Терм "Экстремальная интенсивность"
        intensityOfRequests.addTerm(new Term(
                "Экстремальная интенсивность",
                800, 900, 950, 1000,
                Term.MembershipFunctionType.LINEAR
        ));

        LinguisticVariable ramAmount = new LinguisticVariable("Количество ОЗУ (Мб)");
        ramAmount.setUniversalSet(IntStream.rangeClosed(0, 8196).toArray());
        linguisticVariables.add(ramAmount);

        // Терм "Мало памяти"
        ramAmount.addTerm(new Term(
                "Мало памяти",
                0, 512, 1024, 2048,
                Term.MembershipFunctionType.LINEAR
        ));

        // Терм "Среднее количество памяти"
        ramAmount.addTerm(new Term(
                "Среднее количество памяти",
                1024, 4096, 4096, 6144,
                Term.MembershipFunctionType.QUADRATIC
        ));

        // Терм "Много памяти"
        ramAmount.addTerm(new Term(
                "Много памяти",
                4096, 6144, 7168, 8196,
                Term.MembershipFunctionType.LINEAR
        ));

        LinguisticVariable cpuFreq = new LinguisticVariable("Частота процессора (МГц)");
        cpuFreq.setUniversalSet(IntStream.rangeClosed(1200, 5000).toArray());
        linguisticVariables.add(cpuFreq);

        // Терм "Низкая частота"
        cpuFreq.addTerm(new Term(
                "Низкая частота",
                1200, 1600, 2000, 2400,
                Term.MembershipFunctionType.LINEAR
        ));

        // Терм "Средняя частота"
        cpuFreq.addTerm(new Term(
                "Средняя частота",
                2000, 2400, 3000, 3600,
                Term.MembershipFunctionType.LINEAR
        ));

        // Терм "Высокая частота"
        cpuFreq.addTerm(new Term(
                "Высокая частота",
                3000, 3600, 4200, 5000,
                Term.MembershipFunctionType.LINEAR
        ));
    }

    private void createRules() {
        int[][] rulesMap = {
                {1, 2, 2},
                {0, 1, 2},
                {0, 0, 0},
        };

        for (int i = 0; i < rulesMap.length; i++) {
            for (int j = 0; j < rulesMap[i].length; j++) {
                int intensityIndex = i;
                int ramIndex = j;
                int cpuIndex = rulesMap[i][j];

                Term intensityTerm = linguisticVariables.get(0).getTerms().get(intensityIndex);
                Term ramTerm = linguisticVariables.get(1).getTerms().get(ramIndex);
                Term cpuTerm = linguisticVariables.get(2).getTerms().get(cpuIndex);

                Rule rule = new Rule(new ArrayList<>() {{
                    add(new Condition(intensityTerm, 0));
                    add(new Condition(ramTerm, 1));
                }}, new Conclusion(cpuTerm, Conclusion.DEFAULT_WEIGHT));
                rules.add(rule);
            }
        }

    }
}
