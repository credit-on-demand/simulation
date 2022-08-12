package com.creditoonde.simulation.resources;

import com.creditoonde.simulation.domain.Product;
import com.creditoonde.simulation.dto.ProductDTO;
import com.creditoonde.simulation.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

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
        return ResponseEntity.ok().body( new ProductDTO(product));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody ProductDTO productDTO) {
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
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody ProductDTO productDTO) {
        Product product = service.fromDTO(productDTO);
        product.setId(id);
        service.update(product);
        return ResponseEntity.noContent().build();
    }
}
