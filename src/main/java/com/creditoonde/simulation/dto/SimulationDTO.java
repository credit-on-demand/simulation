package com.creditoonde.simulation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimulationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Simulation's productId cannot be null")
    private String productId;
    @NotNull(message = "Simulation's instalmentsQuantity cannot be null")
    @Positive(message = "Simulation's instalmentsQuantity must be greater than zero")
    private Integer instalmentsQuantity;
    @NotNull(message = "Simulation's monthlyInterestRate cannot be null")
    @Positive(message = "Simulation's monthlyInterestRate must be greater than zero")
    private Double monthlyInterestRate;
    @NotNull(message = "Simulation's requestedAmount cannot be null")
    @Positive(message = "Simulation's requestedAmount must be greater than zero")
    private Double requestedAmount;
}
