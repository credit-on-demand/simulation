package com.creditoonde.simulation.controller;

import com.creditoonde.simulation.domain.Simulation;
import com.creditoonde.simulation.dto.SimulationDTO;
import com.creditoonde.simulation.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/simulation")
public class SimulationController {

    @Autowired
    private SimulationService service;

    @PostMapping
    public ResponseEntity<Simulation> simulate(@Valid @RequestBody SimulationDTO simulationDTO) {
        Simulation simulation = service.simulate(simulationDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(simulation.getId()).toUri();
        return ResponseEntity.created(uri).body(simulation);
    }

    @GetMapping
    public ResponseEntity<List<Simulation>> findAll() {
        List<Simulation> simulations = service.findAll();
        return ResponseEntity.ok().body(simulations);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Simulation> findById(@PathVariable String id) {
        Simulation simulation = service.findById(id);
        return ResponseEntity.ok().body(simulation);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
