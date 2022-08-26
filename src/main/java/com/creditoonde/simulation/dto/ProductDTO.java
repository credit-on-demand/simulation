package com.creditoonde.simulation.dto;

import com.creditoonde.simulation.domain.Product;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    @NotNull(message = "Product's name cannot be null")
    private String name;
    @NotNull(message = "Product's minInterestRate cannot be null")
    @Positive(message = "Product's minInterestRate must be greater than zero")
    private Double minInterestRate;
    @NotNull(message = "Product's maxInterestRate cannot be null")
    @Positive(message = "Product's maxInterestRate must be greater than zero")
    private Double maxInterestRate;
    @NotNull(message = "Product's financialInstitution cannot be null")
    private String financialInstitution;

    public ProductDTO() {
    }

    public ProductDTO(Product product) {
        id = product.getId();
        name = product.getName();
        minInterestRate = product.getMinInterestRate();
        maxInterestRate = product.getMaxInterestRate();
        financialInstitution = product.getFinancialInstitution();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMinInterestRate() {
        return minInterestRate;
    }

    public void setMinInterestRate(double minInterestRate) {
        this.minInterestRate = minInterestRate;
    }

    public double getMaxInterestRate() {
        return maxInterestRate;
    }

    public void setMaxInterestRate(double maxInterestRate) {
        this.maxInterestRate = maxInterestRate;
    }

    public String getFinancialInstitution() {
        return financialInstitution;
    }

    public void setFinancialInstitution(String financialInstitution) {
        this.financialInstitution = financialInstitution;
    }
}
