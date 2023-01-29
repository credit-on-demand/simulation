package com.creditoonde.simulation.dto;

import com.creditoonde.simulation.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    @NotNull(message = "Product's name cannot be null")
    private String name;
    @NotNull(message = "Product's rate indexer cannot be null")
    private String rateIndexer;
    @NotNull(message = "Product's minInterestRate cannot be null")
    @Positive(message = "Product's minInterestRate must be greater than zero")
    private Double minInterestRate;
    @NotNull(message = "Product's maxInterestRate cannot be null")
    @Positive(message = "Product's maxInterestRate must be greater than zero")
    private Double maxInterestRate;
    @NotNull(message = "Product's financialInstitution cannot be null")
    private String financialInstitution;

    public ProductDTO(Product product) {
        id = product.getId();
        name = product.getName();
        rateIndexer = product.getRateIndexer();
        minInterestRate = product.getMinInterestRate();
        maxInterestRate = product.getMaxInterestRate();
        financialInstitution = product.getFinancialInstitution();
    }
}
