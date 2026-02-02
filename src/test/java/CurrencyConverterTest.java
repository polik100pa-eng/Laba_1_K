import org.example.CurrencyConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyConverterTest {

    @Test
    void testConvertValidValues() {
        double result = CurrencyConverter.convert(100, 1.2);
        assertEquals(120.00, result);
    }

    @Test
    void testRoundingToTwoDecimals() {
        double result = CurrencyConverter.convert(10.555, 2);
        assertEquals(21.11, result);
    }

    @Test
    void testNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () ->
                CurrencyConverter.convert(-10, 1.2)
        );
    }

    @Test
    void testZeroRate() {
        assertThrows(IllegalArgumentException.class, () ->
                CurrencyConverter.convert(100, 0)
        );
    }

    @Test
    void testMinimalValidValues() {
        double result = CurrencyConverter.convert(0.01, 0.01);
        assertEquals(0.00, result);
    }
}
