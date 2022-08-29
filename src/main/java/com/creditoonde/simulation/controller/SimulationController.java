package com.creditoonde.simulation.controller;

import com.creditoonde.simulation.domain.Simulation;
import com.creditoonde.simulation.dto.SimulationDTO;
import com.creditoonde.simulation.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
}
