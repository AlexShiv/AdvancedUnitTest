package ru.edu.asu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class WalletTest {

    private MoneyPrinter mockMoneyPrinter = buildMockMoneyPrinter();

    @Test
    void addMoney() {
        Wallet wallet = new Wallet(mockMoneyPrinter);
        wallet.addMoney("RUB", 500);
        assertEquals(500, wallet.getMoney("RUB"));
    }

    @Test
    void addMoneyMulti() {
        Wallet wallet = new Wallet(mockMoneyPrinter);
        int multi = 3;
        for (int i = 0; i < multi; i++) {
            wallet.addMoney("RUB", 500);
        }
        assertEquals(1500, wallet.getMoney("RUB"));
    }

    @Test
    void removeMoneyEnough() {
        Wallet wallet = new Wallet(mockMoneyPrinter);
        wallet.addMoney("RUB", 500);
        assertEquals(500, wallet.getMoney("RUB"));
        wallet.removeMoney("RUB", 500);

        assertEquals(0, wallet.getMoney("RUB"));
    }

    @Test
    void removeMoneyNotEnough() {
        Wallet wallet = new Wallet(mockMoneyPrinter);
        wallet.addMoney("RUB", 500);
        assertEquals(500, wallet.getMoney("RUB"));

        assertThrows(Exception.class, () -> {
            wallet.removeMoney("RUB", 1000);
        });

        assertEquals(500, wallet.getMoney("RUB"));
    }

    @Test
    void addDifferentCurrency() {
        Wallet wallet = new Wallet(mockMoneyPrinter);
        wallet.addMoney("RUB", 500);
        wallet.addMoney("USD", 300);

        assertEquals(500, wallet.getMoney("RUB"));
        assertEquals(300, wallet.getMoney("USD"));
    }

    @Test
    void removeDifferentCurrency() {
        Wallet wallet = new Wallet(mockMoneyPrinter);
        wallet.addMoney("RUB", 500);
        wallet.addMoney("USD", 300);

        wallet.removeMoney("RUB", 100);
        wallet.removeMoney("USD", 100);

        assertEquals(400, wallet.getMoney("RUB"));
        assertEquals(200, wallet.getMoney("USD"));
    }

    @Test
    void getNotNullValue() {
        Wallet wallet = new Wallet(mockMoneyPrinter);
        wallet.addMoney("RUB", 500);

        assertEquals(500, wallet.getMoney("RUB"));
    }

    @Test
    void getNullValue() {
        Wallet wallet = new Wallet(mockMoneyPrinter);

        assertEquals(0, wallet.getMoney("RUB"));
    }

    @Test
    void getCurrencyCount() {
        Wallet wallet = new Wallet(mockMoneyPrinter);

        wallet.addMoney("RUB", 500);
        wallet.addMoney("USD", 300);

        assertEquals(2, wallet.currencyCount());
    }

    @Test
    void getCurrencyCountWithRemove() {
        Wallet wallet = new Wallet(mockMoneyPrinter);

        wallet.addMoney("RUB", 500);
        wallet.addMoney("USD", 300);

        assertEquals(2, wallet.currencyCount());

        wallet.removeMoney("RUB", 500);
        assertEquals(1, wallet.currencyCount());
    }

    @Test
    void notEmptyToStringTest() {
        Wallet wallet = new Wallet(mockMoneyPrinter);

        wallet.addMoney("USD", 300);
        wallet.addMoney("RUB", 500);

        assertEquals("{ 300.0 USD, 500.0 RUB }", wallet.toString());
    }

    @Test
    void emptyToStringTest() {
        Wallet wallet = new Wallet(mockMoneyPrinter);

        assertEquals("{ }", wallet.toString());
    }

    @Test
    void getTotalMoneyTest() {
        Bank mock = mock(Bank.class);
        when(mock.convert(100, "USD", "RUB")).thenReturn(90.0);
        when(mock.convert(200, "EUR", "RUB")).thenReturn(120.0);

        MoneyPrinter moneyPrinter = mock(MoneyPrinter.class);

        doNothing().when(moneyPrinter).print(isA(String.class), isA(String.class), isA(double.class));

        Wallet wallet = new Wallet(mock, moneyPrinter);

        wallet.addMoney("USD", 100);
        wallet.addMoney("EUR", 200);

        assertEquals(210, wallet.getTotalMoney("RUB"));
    }

    private MoneyPrinter buildMockMoneyPrinter(){
        MoneyPrinter moneyPrinter = mock(MoneyPrinter.class);
        doNothing().when(moneyPrinter).print(isA(String.class), isA(String.class), isA(double.class));
        return moneyPrinter;
    }

}
