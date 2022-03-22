package ru.edu.asu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.UUID;

public class MoneyPrinter {

    private final File f;

    public MoneyPrinter() {
        f = new File(MessageFormat.format("logs/{0}.history", UUID.randomUUID()));
    }

    public void print(String operation, String currency, double amount) {
        try {
            f.createNewFile();
            printFile(operation, currency, amount);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void printFile(String operation, String currency, double amount) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(f, true))) {
            writer.print(MessageFormat.format("{0} {1} {2}\r\n", operation, currency, amount));
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
