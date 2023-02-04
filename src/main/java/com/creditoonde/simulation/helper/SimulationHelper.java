package com.creditoonde.simulation.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SimulationHelper {

    private SimulationHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static BigDecimal calculateInstalmentValue(int instalmentsQuantity, BigDecimal monthlyInterestRate, BigDecimal totalAmount) {
        if (instalmentsQuantity <= 0 || monthlyInterestRate == null || totalAmount == null
                || monthlyInterestRate.compareTo(BigDecimal.ZERO) == 0 || totalAmount.compareTo(BigDecimal.ZERO) == 0) {
            throw new NumberFormatException("One or more simulation values are invalid.");
        }
        monthlyInterestRate = monthlyInterestRate.divide(new BigDecimal(100), 6, RoundingMode.HALF_UP);
        BigDecimal commonPow = monthlyInterestRate.add(new BigDecimal(1)).pow(instalmentsQuantity);
        BigDecimal fractionUp = commonPow.multiply(monthlyInterestRate);
        BigDecimal fractionDown = commonPow.subtract(new BigDecimal(1));
        BigDecimal fractionResult = fractionUp.divide(fractionDown, 6, RoundingMode.HALF_UP);

        return totalAmount.multiply(fractionResult).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateTotalAmount(int instalmentsQuantity, BigDecimal instalmentValue) {
        return instalmentValue.multiply(BigDecimal.valueOf(instalmentsQuantity)).setScale(2, RoundingMode.HALF_UP);
    }
}
