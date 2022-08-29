package com.creditoonde.simulation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Simulation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private Integer instalmentsQuantity;
    private Double monthlyInterestRate;
    private Double totalAmount;
    private Double instalmentValue;
    private String productId;
    private LocalDateTime simulationDate;
}
