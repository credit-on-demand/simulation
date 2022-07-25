package com.creditoonde.simulation.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SimulationHelper {

    public BigDecimal calculateInstalmentValue(int instalmentsQuantity, BigDecimal monthlyInterestRate, BigDecimal totalAmount) {
        if (instalmentsQuantity <= 0 || monthlyInterestRate == null || totalAmount == null
                || monthlyInterestRate.equals(new BigDecimal(0)) || totalAmount.equals(new BigDecimal(0))) {
            throw new NumberFormatException("One or more simulation values are invalid.");
        }
        BigDecimal commonPow = monthlyInterestRate.add(new BigDecimal(1)).pow(instalmentsQuantity);

        BigDecimal fractionUp = commonPow.multiply(monthlyInterestRate);
        BigDecimal fractionDown = commonPow.subtract(new BigDecimal(1));

        BigDecimal fractionResult = fractionUp.divide(fractionDown, 5, RoundingMode.HALF_UP);

        return totalAmount.multiply(fractionResult).setScale(2, RoundingMode.HALF_UP);
    }
}
