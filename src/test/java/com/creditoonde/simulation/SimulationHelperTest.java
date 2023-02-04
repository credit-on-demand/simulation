package com.creditoonde.simulation;

import com.creditoonde.simulation.helper.SimulationHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimulationHelperTest {

    @ParameterizedTest
    @MethodSource("dataProvider")
    void shouldCalculateInstalmentValue(double total, double monthly, int instalmentsQt, double res) {
        BigDecimal totalAmount = BigDecimal.valueOf(total);
        BigDecimal monthlyInterest = BigDecimal.valueOf(monthly);
        BigDecimal result = SimulationHelper.calculateInstalmentValue(instalmentsQt, monthlyInterest, totalAmount);

        assertEquals(res, result.doubleValue());
    }

    @Test
    void shouldCalculateTotalAmountValue() {
        int instalmentsQuantity = 24;
        BigDecimal instalmentValue = new BigDecimal("63.45");
        BigDecimal result = SimulationHelper.calculateTotalAmount(instalmentsQuantity, instalmentValue);

        assertEquals(1522.80, result.doubleValue());
    }

    static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of(1200.00, 2.00, 12, 113.47),
                Arguments.of(1200.00, 2.00, 24, 63.45),
                Arguments.of(1380.00, 3.69, 48, 61.77)
        );
    }
}
