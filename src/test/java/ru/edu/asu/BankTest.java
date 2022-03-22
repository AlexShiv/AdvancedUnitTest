package ru.edu.asu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankTest {

    @Test
    void correctConvert() {
        Bank bank = new Bank();

        assertDoesNotThrow(() -> bank.convert(100, "RUB", "USD"));
    }

    @Test
    void unknownFromConvert() {
        Bank bank = new Bank();

        assertThrows(Exception.class, () -> bank.convert(100, "QWE", "USD"));
    }

    @Test
    void unknownToConvert() {
        Bank bank = new Bank();

        assertThrows(Exception.class, () -> bank.convert(100, "RUB", "QWE"));
    }
}
