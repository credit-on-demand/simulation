package com.creditoonde.simulation.service;

import com.creditoonde.simulation.controller.exception.ObjectNotFoundException;
import com.creditoonde.simulation.domain.Product;
import com.creditoonde.simulation.dto.ProductDTO;
import com.creditoonde.simulation.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(String id) {
        Optional<Product> product = repository.findById(id);
        return product.orElseThrow(() -> new ObjectNotFoundException("Product not found."));
    }

    public List<Product> findByFinancialInstitution(String name) {
        return repository.findByFinancialInstitutionContainingIgnoreCase(name);
    }

    public Product insert(Product product) {
        return repository.insert(product);
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }

    public Product update(Product product) {
        Product updated = findById(product.getId());
        updateData(updated, product);
        return repository.save(updated);
    }

    private void updateData(Product updated, Product product) {
        updated.setName(product.getName());
        updated.setFinancialInstitution(product.getFinancialInstitution());
        updated.setMinInterestRate(product.getMinInterestRate());
        updated.setMaxInterestRate(product.getMaxInterestRate());
    }

    public Product fromDTO(ProductDTO productDTO) {
        return new Product(productDTO.getId(), productDTO.getName(), productDTO.getMinInterestRate(), productDTO.getMaxInterestRate(), productDTO.getFinancialInstitution());
    }
}
