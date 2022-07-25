package com.creditoonde.simulation.repository;

import com.creditoonde.simulation.domain.FinancialInstitution;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialInstitutionRepository extends MongoRepository<FinancialInstitution, String> {

}
