package org.example;

public class CurrencyConverter {
// amount - сумма в исх. валюте
    // rate - курс конвертации
    public static double convert(double amount, double rate) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (rate <= 0) {
            throw new IllegalArgumentException("Rate must be greater than zero");
        }

        // Вычисляем и округляем до 2 знаков после запятой
        double result = amount * rate;
        result = Math.round(result * 100.0) / 100.0;

        return result;
    }
}
