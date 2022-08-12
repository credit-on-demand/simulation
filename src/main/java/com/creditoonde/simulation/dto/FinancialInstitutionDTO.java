package com.creditoonde.simulation.dto;

import com.creditoonde.simulation.domain.FinancialInstitution;

import java.io.Serializable;

public class FinancialInstitutionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    public FinancialInstitutionDTO() {

    }

    public FinancialInstitutionDTO(FinancialInstitution financialInstitution) {
        id = financialInstitution.getId();
        name = financialInstitution.getName();
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
}
