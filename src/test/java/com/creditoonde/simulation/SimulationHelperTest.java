package com.creditoonde.simulation;

import com.creditoonde.simulation.helper.SimulationHelper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimulationHelperTest {

    @Test
    void shouldCalculateInstalmentValue() {
        BigDecimal totalAmount = new BigDecimal("1200");
        BigDecimal monthlyInterest = new BigDecimal("2");
        int instalmentsQuantity = 12;
        BigDecimal result = SimulationHelper.calculateInstalmentValue(instalmentsQuantity, monthlyInterest, totalAmount);

        assertEquals(113.47, result.doubleValue());
    }
}
