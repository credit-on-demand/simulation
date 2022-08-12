package com.creditoonde.simulation.dto;

import com.creditoonde.simulation.domain.Product;

import java.io.Serializable;

public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private double minInterestRate;
    private double maxInterestRate;
    private FinancialInstitutionDTO financialInstitution;

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

    public FinancialInstitutionDTO getFinancialInstitution() {
        return financialInstitution;
    }

    public void setFinancialInstitution(FinancialInstitutionDTO financialInstitution) {
        this.financialInstitution = financialInstitution;
    }
}
