package com.creditoonde.simulation.repository;

import com.creditoonde.simulation.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByFinancialInstitutionContainingIgnoreCase(String name);
}
