package com.creditoonde.simulation.config;

import com.creditoonde.simulation.domain.Product;
import com.creditoonde.simulation.repository.ProductRepository;
import com.creditoonde.simulation.repository.SimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Instatiation implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SimulationRepository simulationRepository;

    @Override
    public void run(String... args) throws Exception {
        productRepository.deleteAll();
        Product easyLoan = new Product(null, "Easy loan", "PRE_FIXED",
                1.95, 3.05, "Bank Sample LTD");
        Product consignedCredit = new Product(null, "Consigned Loan", "CDI",
                1.55, 2.20, "XPTO Finance");
        productRepository.saveAll(Arrays.asList(easyLoan, consignedCredit));
        simulationRepository.deleteAll();
    }
}
