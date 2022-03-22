package ru.edu.asu;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Wallet {
    private final Bank bank;
    private final MoneyPrinter moneyPrinter;
    private final Map<String, Double> wallet;

    public Wallet() {
        this.wallet = new HashMap<>();
        this.bank = new Bank();
        moneyPrinter = new MoneyPrinter();
    }

    public Wallet(MoneyPrinter moneyPrinter) {
        this.wallet = new HashMap<>();
        this.bank = new Bank();
        this.moneyPrinter = moneyPrinter;
    }

    public Wallet(Bank bank, MoneyPrinter moneyPrinter) {
        this.wallet = new HashMap<>();
        this.bank = bank;
        this.moneyPrinter = moneyPrinter;
    }

    public void addMoney(String currency, double value) {
        if (wallet.containsKey(currency)) {
            double result = wallet.get(currency) + value;
            wallet.put(currency, result);
        } else {
            wallet.put(currency, value);
        }
        moneyPrinter.print("add", currency, value);
    }

    public double getMoney(String currency) {
        return wallet.getOrDefault(currency, 0.0);
    }

    public void removeMoney(String currency, double value) {
        double result = wallet.get(currency) - value;
        if (result < 0) {
            throw new NullPointerException("Недостаточно средств для снятия");
        } else {
            wallet.put(currency, result);
        }
        moneyPrinter.print("remove", currency, value);
    }

    public double currencyCount() {
        return wallet
                .entrySet()
                .stream()
                .filter(stringIntegerEntry -> stringIntegerEntry.getValue() > 0)
                .count();
    }


    @Override
    public String toString() {
        if (wallet.isEmpty()) {
            return "{ }";
        } else {
            return wallet
                    .entrySet()
                    .stream()
                    .filter(stringIntegerEntry -> stringIntegerEntry.getValue() > 0)
                    .map(stringIntegerEntry -> stringIntegerEntry.getValue() + " " + stringIntegerEntry.getKey())
                    .collect(Collectors.joining(", ", "{ ", " }"));
        }
    }

    public double getTotalMoney(String currency) {
        return wallet
                .entrySet()
                .stream()
                .map(stringIntegerEntry -> bank.convert(stringIntegerEntry.getValue(), stringIntegerEntry.getKey(), currency))
                .reduce(0.0, Double::sum);
    }
}
