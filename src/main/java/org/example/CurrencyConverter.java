package org.example;

public class CurrencyConverter {

    private final ExchangeRateService exchangeRateService;
    private final LoggerService loggerService;

    public CurrencyConverter(ExchangeRateService exchangeRateService,
                             LoggerService loggerService) {
        this.exchangeRateService = exchangeRateService;
        this.loggerService = loggerService;
    }

    public double convert(double amount, String currencyCode) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        // получаем курс валют из внешнего сервиса
        double rate = exchangeRateService.getRate(currencyCode);

        if (rate <= 0) {
            throw new IllegalArgumentException("Rate must be greater than zero");
        }

        double result = amount * rate;

        result = Math.round(result * 100.0) / 100.0;

        // если сумма большая — логируем
        if (amount > 100000) {
            loggerService.log("Large conversion: " + amount + " " + currencyCode);
        }

        return result;
    }
}