package com.creditoonde.simulation.controller;

import com.creditoonde.simulation.domain.Product;
import com.creditoonde.simulation.dto.ProductDTO;
import com.creditoonde.simulation.helper.URLHelper;
import com.creditoonde.simulation.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<Product> products = service.findAll();
        List<ProductDTO> productsDTO = products.stream().map(ProductDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(productsDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable String id) {
        Product product = service.findById(id);
        return ResponseEntity.ok().body(new ProductDTO(product));
    }

    @GetMapping(value = "/financialInstitution")
    public ResponseEntity<List<ProductDTO>> findByFinancialInstitution(@RequestParam(value = "name", defaultValue = "") String name) {
        name = URLHelper.decodeParam(name);
        List<Product> products = service.findByFinancialInstitution(name);
        List<ProductDTO> productsDTO = products.stream().map(ProductDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(productsDTO);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ProductDTO productDTO) {
        Product created = service.fromDTO(productDTO);
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
    public ResponseEntity<Void> update(@PathVariable String id, @Valid @RequestBody ProductDTO productDTO) {
        Product product = service.fromDTO(productDTO);
        product.setId(id);
        service.update(product);
        return ResponseEntity.noContent().build();
    }
}
