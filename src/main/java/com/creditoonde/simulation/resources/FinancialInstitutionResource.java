package com.creditoonde.simulation.resources;

import com.creditoonde.simulation.domain.FinancialInstitution;
import com.creditoonde.simulation.services.FinancialInstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/financial-institutions")
public class FinancialInstitutionResource {

    @Autowired
    private FinancialInstitutionService service;

    @GetMapping
    public ResponseEntity<List<FinancialInstitution>> findAll() {
        List<FinancialInstitution> financialInstitutions = service.findAll();
        return ResponseEntity.ok().body(financialInstitutions);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FinancialInstitution> findById(@PathVariable String id) {
        FinancialInstitution financialInstitution = service.findById(id);
        return ResponseEntity.ok().body(financialInstitution);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody FinancialInstitution financialInstitution) {
        FinancialInstitution created = service.insert(financialInstitution);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody FinancialInstitution financialInstitution) {
        financialInstitution.setId(id);
        service.update(financialInstitution);
        return ResponseEntity.noContent().build();
    }
}
