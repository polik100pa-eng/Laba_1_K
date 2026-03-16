import org.example.CurrencyConverter;
import org.example.ExchangeRateService;
import org.example.LoggerService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CurrencyConverterTest {

    private ExchangeRateService exchangeRateService;
    private LoggerService loggerService;
    private CurrencyConverter converter;

    @BeforeEach
    void setUp() {

        exchangeRateService = mock(ExchangeRateService.class);
        loggerService = mock(LoggerService.class);

        converter = new CurrencyConverter(exchangeRateService, loggerService);
    }

    // успешная конвертация
    @Test
    void testConvertValidValues() {

        when(exchangeRateService.getRate("USD")).thenReturn(1.2);

        double result = converter.convert(100, "USD");

        assertEquals(120.00, result);
    }

    // проверка округления
    @Test
    void testRoundingToTwoDecimals() {

        when(exchangeRateService.getRate("USD")).thenReturn(2.0);

        double result = converter.convert(10.555, "USD");

        assertEquals(21.11, result);
    }

    // отрицательная сумма
    @Test
    void testNegativeAmount() {

        assertThrows(IllegalArgumentException.class,
                () -> converter.convert(-10, "USD"));
    }

    // неверный курс
    @Test
    void testZeroRate() {

        when(exchangeRateService.getRate("USD")).thenReturn(0.0);

        assertThrows(IllegalArgumentException.class,
                () -> converter.convert(100, "USD"));
    }

    // минимальные значения
    @Test
    void testMinimalValidValues() {

        when(exchangeRateService.getRate("USD")).thenReturn(0.01);

        double result = converter.convert(0.01, "USD");

        assertEquals(0.00, result);
    }

    // проверка логгера
    @Test
    void testLargeAmountLogging() {

        when(exchangeRateService.getRate("USD")).thenReturn(1.0);

        converter.convert(200000, "USD");

        verify(loggerService, times(1))
                .log("Large conversion: 200000.0 USD");
    }

    // сервис недоступен
    @Test
    void testExchangeServiceFailure() {

        when(exchangeRateService.getRate("USD"))
                .thenThrow(new RuntimeException("Service unavailable"));

        assertThrows(RuntimeException.class,
                () -> converter.convert(100, "USD"));
    }

    // логгер не вызывается
    @Test
    void testLoggerNotCalledForSmallAmount() {

        when(exchangeRateService.getRate("USD")).thenReturn(1.0);

        converter.convert(1000, "USD");

        verify(loggerService, never()).log(anyString());
    }
}