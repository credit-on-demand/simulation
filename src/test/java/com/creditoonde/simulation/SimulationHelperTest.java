package com.creditoonde.simulation;

import com.creditoonde.simulation.helper.SimulationHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimulationHelperTest {

    @Autowired
    SimulationHelper simulationHelper;

    @Test
    void shouldCalculateInstalmentValue() {
        simulationHelper = new SimulationHelper();
        BigDecimal totalAmount = new BigDecimal("1200");
        BigDecimal monthlyInterest = new BigDecimal("0.02");
        int instalmentsQuantity = 12;

        BigDecimal result = simulationHelper.calculateInstalmentValue(instalmentsQuantity, monthlyInterest, totalAmount);

        assertEquals(113.47, result.doubleValue());
    }
}
