package io.sabitovka.donntu.fuzzy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Term implements Countable{
    private String name;
    private int left;
    private int centerLeft;
    private int centerRight;
    private int right;
    private MembershipFunctionType membershipFunctionType;

    @Override
    public double getValue(double x) {
        return calculateMembership(x);
    }

    // Перечисление видов функций принадлежности
    public enum MembershipFunctionType {
        LINEAR,
        QUADRATIC,
        // TRIGONOMETRIC, FRACTIONAL
        EXPONENTIAL,
    }

    public Term(String name, int left, int centerLeft, int centerRight, int right) {
        this(name, left, centerLeft, centerRight, right, MembershipFunctionType.LINEAR);
    }

    public Term(String name, int left, int centerLeft, int centerRight, int right, MembershipFunctionType membershipFunctionType) {
        // validateParameters(left, centerLeft, centerRight, right);
        this.name = name;
        this.left = left;
        this.centerLeft = centerLeft;
        this.centerRight = centerRight;
        this.right = right;
        this.membershipFunctionType = membershipFunctionType;
    }

    private void validateParameters(int left, int centerLeft, int centerRight, int right) {
        if (left >= centerLeft || centerLeft >= centerRight || centerRight >= right) {
            throw new IllegalArgumentException("Недопустимые параметры. Убедитесь, что left < centerLeft < centerRight < right.");
        }
    }

    public Term conjunction(Term other) {
        String conjunctionName = String.format("(%s AND %s)", this.name, other.name);
        int conjunctionLeft = Math.max(this.left, other.left);
        int conjunctionCenterLeft = Math.max(this.centerLeft, other.centerLeft);
        int conjunctionCenterRight = Math.min(this.centerRight, other.centerRight);
        int conjunctionRight = Math.min(this.right, other.right);

        return new Term(conjunctionName, conjunctionLeft, conjunctionCenterLeft, conjunctionCenterRight, conjunctionRight);
    }

    // Функция, вычисляющая функцию принадлежности на основе выбранного типа
    public double calculateMembership(double x) {
        switch (membershipFunctionType) {
            case LINEAR:
                return linearMembership(x);
            case QUADRATIC:
                return quadraticMembership(x);
//                case TRIGONOMETRIC:
//                    return trigonometricMembership(x);
            case EXPONENTIAL:
                return gaussianMembership(x);
//                case FRACTIONAL:
//                    return fractionalMembership(x);
            default:
                throw new IllegalArgumentException("Недопустимый тип функции принадлежности");
        }
    }

    // Линейная функция принадлежности
    private double linearMembership(double x) {
        if (x <= left) {
            return 0;
        }
        if (x <= centerLeft) {
            return (double) (x - left) / (centerLeft - left);
        }
        if (x <= centerRight) {
            return 1;
        }
        if (x <= right) {
            return (double) (right - x) / (right - centerRight);
        }
        return 0;
    }

    private static double avg(double a, double b) {
        return (a + b) / 2;
    }

    private double smf(double x, int a, int b) {
        if (x <= a) {
            return 0;
        } else if (x > a && x <= avg(a, b)) {
            return 2 * Math.pow(((x - a) / (b - a)), 2);
        } else if (x > avg(a, b) && x <= b) {
            return 1 - 2 * Math.pow((double) (b - x) / (b - a), 2);
        } else {
            return 1;
        }
    }

    private double zmf(double x, int a, int b) {
        if (x < a) {
            return 1;
        } else if (x >= a && x <= avg(a, b)) {
            return 1 - 2 * Math.pow((double) (x - a) / (b - a), 2);
        } else if (x > avg(a, b) && x <= b) {
            return 2 * Math.pow((double) (b - x) / (b - a), 2);
        } else {
            return 0;
        }
    }

    private double pimf(double x, int a1, int b1, int a2, int b2) {
        double smfResult = smf(x, a1, b1);
        double zmfResult = zmf(x, a2, b2);
        return smfResult * zmfResult;
    }

    // Квадратичная функция принадлежности
    private double quadraticMembership(double x) {
        return pimf(x, left, centerLeft, centerRight, right);
    }

    // Тригонометрическая функция принадлежности
    private double trigonometricMembership(int x) {
        return 0;
    }

    // Экспоненциальная функция принадлежности
    private double gaussianMembership(double x) {
        return Math.exp(-Math.pow((x - left), 2) / Math.pow(centerLeft, 2));
    }

    // Дробная функция принадлежности
    private double fractionalMembership(int x) {
        return 0;
    }
}
