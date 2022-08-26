package com.creditoonde.simulation.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Document
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String name;
    private double minInterestRate;
    private double maxInterestRate;
    private String financialInstitution;

    public Product(String id, String name, double minInterestRate, double maxInterestRate, String financialInstitution) {
        this.id = id;
        this.name = name;
        this.minInterestRate = minInterestRate;
        this.maxInterestRate = maxInterestRate;
        this.financialInstitution = financialInstitution;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
