package com.creditoonde.simulation.services;

import com.creditoonde.simulation.domain.FinancialInstitution;
import com.creditoonde.simulation.repository.FinancialInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinancialInstitutionService {

    @Autowired
    private FinancialInstitutionRepository repository;

    public List<FinancialInstitution> findAll() {
        return repository.findAll();
    }

    public FinancialInstitution findById(String id) {
        Optional<FinancialInstitution> financialInstitution = repository.findById(id);
        return financialInstitution.orElseThrow(() -> new ObjectNotFoundException("Financial institution not found."));
    }

    public FinancialInstitution insert(FinancialInstitution financialInstitution) {
        return repository.insert(financialInstitution);
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }

    public FinancialInstitution update(FinancialInstitution financialInstitution) {
        FinancialInstitution updated = findById(financialInstitution.getId());
        updateData(updated, financialInstitution);
        return repository.save(updated);
    }

    private void updateData(FinancialInstitution updated, FinancialInstitution financialInstitution) {
        updated.setName(financialInstitution.getName());
    }
}
