package com.creditoonde.simulation.repository;

import com.creditoonde.simulation.domain.Simulation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationRepository extends MongoRepository<Simulation, String> {
}
