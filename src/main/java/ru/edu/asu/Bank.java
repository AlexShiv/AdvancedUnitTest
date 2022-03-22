package ru.edu.asu;

public class Bank {

    private enum AVAILABLE_CURRENCY {
        RUB,
        USD,
        EUR,
        GBP,
        JPY
    }
    private static final double CONST = 0.9;

    public double convert(double value, String from, String to) {
        AVAILABLE_CURRENCY.valueOf(from);
        AVAILABLE_CURRENCY.valueOf(to);

        double percent = CONST * rnd(0, 20)/100;
        int plusOrMinus = rnd(0,1);

        double result;
        if (plusOrMinus == 0) {
            result = value * (CONST + percent);
        } else {
            result = value * (CONST - percent);

        }
        return result;
    }

    private int rnd(int min, int max)
    {
        int shiftedMax = max - min;
        return (int) (Math.random() * ++shiftedMax) + min;
    }
}
