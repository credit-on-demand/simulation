package com.creditoonde.simulation.resources;

import com.creditoonde.simulation.domain.FinancialInstitution;
import com.creditoonde.simulation.domain.Product;
import com.creditoonde.simulation.dto.FinancialInstitutionDTO;
import com.creditoonde.simulation.dto.ProductDTO;
import com.creditoonde.simulation.services.FinancialInstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/financial-institutions")
public class FinancialInstitutionResource {

    @Autowired
    private FinancialInstitutionService service;

    @GetMapping
    public ResponseEntity<List<FinancialInstitutionDTO>> findAll() {
        List<FinancialInstitution> financialInstitutions = service.findAll();
        List<FinancialInstitutionDTO> financialInstitutionDTOs = financialInstitutions.stream().map(FinancialInstitutionDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(financialInstitutionDTOs);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FinancialInstitutionDTO> findById(@PathVariable String id) {
        FinancialInstitution financialInstitution = service.findById(id);
        return ResponseEntity.ok().body(new FinancialInstitutionDTO(financialInstitution));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody FinancialInstitutionDTO financialInstitutionDTO) {
        FinancialInstitution created = service.fromDTO(financialInstitutionDTO);
        created = service.insert(created);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody FinancialInstitutionDTO financialInstitutionDTO) {
        FinancialInstitution financialInstitution = service.fromDTO(financialInstitutionDTO);
        financialInstitution.setId(id);
        service.update(financialInstitution);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}/products")
    public ResponseEntity<List<Product>> findProducts(@PathVariable String id) {
        FinancialInstitution financialInstitution = service.findById(id);
        return ResponseEntity.ok().body(financialInstitution.getProducts());
    }
}
