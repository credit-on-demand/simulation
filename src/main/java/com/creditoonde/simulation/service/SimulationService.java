package com.creditoonde.simulation.service;

import com.creditoonde.simulation.controller.exception.ObjectNotFoundException;
import com.creditoonde.simulation.domain.Product;
import com.creditoonde.simulation.domain.Simulation;
import com.creditoonde.simulation.dto.SimulationDTO;
import com.creditoonde.simulation.helper.SimulationHelper;
import com.creditoonde.simulation.repository.SimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SimulationService {

    @Autowired
    private SimulationRepository repository;

    @Autowired
    private ProductService productService;

    public Simulation simulate(SimulationDTO simulationDTO) {
        if (!monthlyInterestRateIsValid(simulationDTO.getProductId(), simulationDTO.getMonthlyInterestRate())) {
            throw new NumberFormatException("The monthly interest rate must be between the product's minimum and maximum rates.");
        }
        BigDecimal monthlyInterest = BigDecimal.valueOf(simulationDTO.getMonthlyInterestRate());
        BigDecimal requestedAmount = BigDecimal.valueOf(simulationDTO.getRequestedAmount());
        BigDecimal instalmentValue = SimulationHelper
                .calculateInstalmentValue(simulationDTO.getInstalmentsQuantity(), monthlyInterest, requestedAmount);
        BigDecimal totalAmount = SimulationHelper
                .calculateTotalAmount(simulationDTO.getInstalmentsQuantity(), instalmentValue);
        Simulation simulation =
                Simulation.builder()
                        .id(null)
                        .instalmentsQuantity(simulationDTO.getInstalmentsQuantity())
                        .monthlyInterestRate(simulationDTO.getMonthlyInterestRate())
                        .requestedAmount(simulationDTO.getRequestedAmount())
                        .productId(simulationDTO.getProductId())
                        .instalmentValue(instalmentValue.doubleValue())
                        .totalAmount(totalAmount.doubleValue())
                        .simulationDate(LocalDateTime.now()).build();
        return insert(simulation);
    }

    public Simulation insert(Simulation simulation) {
        return repository.insert(simulation);
    }

    public List<Simulation> findAll() {
        return repository.findAll();
    }

    public Simulation findById(String id) {
        Optional<Simulation> simulation = repository.findById(id);
        return simulation.orElseThrow(() -> new ObjectNotFoundException("Simulation not found."));
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }

    public boolean monthlyInterestRateIsValid(String productId, double monthlyInterestRate) {
        Product product = productService.findById(productId);
        return monthlyInterestRate >= product.getMinInterestRate() && monthlyInterestRate <= product.getMaxInterestRate();
    }
}
