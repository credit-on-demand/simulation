package com.creditoonde.simulation.config;

import com.creditoonde.simulation.domain.FinancialInstitution;
import com.creditoonde.simulation.domain.Product;
import com.creditoonde.simulation.domain.User;
import com.creditoonde.simulation.dto.FinancialInstitutionDTO;
import com.creditoonde.simulation.repository.FinancialInstitutionRepository;
import com.creditoonde.simulation.repository.ProductRepository;
import com.creditoonde.simulation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Instatiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FinancialInstitutionRepository financialInstitutionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "BobGrey", "bob@gmail.com");
        userRepository.saveAll(Arrays.asList(maria, alex, bob));

        financialInstitutionRepository.deleteAll();
        FinancialInstitution sampleBank = new FinancialInstitution(null, "Bank Sample LTD");
        FinancialInstitution xptoFinance = new FinancialInstitution(null, "XPTO Finance");
        financialInstitutionRepository.saveAll(Arrays.asList(sampleBank, xptoFinance));

        productRepository.deleteAll();
        Product easyLoan = new Product(null, "Easy loan",
                1.95, 3.05, new FinancialInstitutionDTO(sampleBank));
        Product consignedCredit = new Product(null, "Consigned Loan",
                1.55, 2.20, new FinancialInstitutionDTO(xptoFinance));
        productRepository.saveAll(Arrays.asList(easyLoan, consignedCredit));

        sampleBank.getProducts().addAll(Arrays.asList(easyLoan));
        xptoFinance.getProducts().addAll(Arrays.asList(consignedCredit));
        financialInstitutionRepository.saveAll(Arrays.asList(sampleBank, xptoFinance));
    }
}
