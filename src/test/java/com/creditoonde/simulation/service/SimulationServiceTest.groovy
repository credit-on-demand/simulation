package com.creditoonde.simulation.service

import com.creditoonde.simulation.controller.exception.ObjectNotFoundException
import com.creditoonde.simulation.domain.Product
import com.creditoonde.simulation.domain.Simulation
import com.creditoonde.simulation.dto.SimulationDTO
import com.creditoonde.simulation.repository.SimulationRepository
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

@SpringBootTest
class SimulationServiceTest extends Specification {

    @Shared
    SimulationService simulationService
    @Shared
    SimulationRepository simulationRepository
    @Shared
    ProductService productService

    def setup() {
        simulationRepository = Mock(SimulationRepository)
        productService = Mock(ProductService)
        simulationService = new SimulationService(repository: simulationRepository, productService: productService)
    }

    def 'Should insert a simulation and return it'() {
        given:
        def simulation = new Simulation('id1', 12, 2.0, 1200.0, 120.0, 'id1', 1440.0, LocalDateTime.now())
        1 * simulationRepository.insert(simulation) >> simulation

        when:
        def created = simulationService.insert(simulation)

        then:
        created == simulation
    }

    def 'Should find all simulations'() {
        given:
        def simulationList =
                [new Simulation('id1', 12, 2.0, 1200.0, 120.0, 'id1', 1440.0, LocalDateTime.now()),
                 new Simulation('id2', 12, 2.0, 1200.0, 120.0, 'id2', 1440.0, LocalDateTime.now())]
        1 * simulationRepository.findAll() >> simulationList

        when:
        def result = simulationService.findAll()

        then:
        result == simulationList
    }

    def 'Should find simulation by id when simulation exists'() {
        given:
        def simulation = new Simulation('id1', 12, 2.0, 1200.0, 120.0, 'id1', 1440.0, LocalDateTime.now())
        1 * simulationRepository.findById('id1') >> Optional.of(simulation)

        when:
        def result = simulationService.findById('id1')

        then:
        result == simulation
    }

    def 'Should throw exception when simulation does not exist'() {
        given:
        simulationRepository.findById('id1') >> Optional.empty()

        when:
        simulationService.findById('id1')

        then:
        def t = thrown(ObjectNotFoundException)
        t.message == 'Simulation not found.'
    }

    def 'Should delete a simulation'() {
        given:
        def simulation = new Simulation('id1', 12, 2.0, 1200.0, 120.0, 'id1', 1440.0, LocalDateTime.now())

        when:
        simulationService.delete('id1')

        then:
        1 * simulationRepository.findById('id1') >> Optional.of(simulation)
        1 * simulationRepository.deleteById('id1')
    }

    @Unroll
    def 'Should create Simulation from SimulationDTO and insert it in repository'() {
        given:
        def simulationDTO = new SimulationDTO('id1', 10, monthlyInterestRate, 1000)
        def product = new Product('id1', 'product1', 'indexer1', 1.0, 2.0, 'institution1')
        1 * productService.findById('id1') >> product

        when:
        def result = simulationService.simulate(simulationDTO)

        then:
        result.instalmentsQuantity == simulationDTO.instalmentsQuantity
        result.requestedAmount == simulationDTO.requestedAmount
        result.productId == simulationDTO.productId
        result.monthlyInterestRate == simulationDTO.monthlyInterestRate
        1 * simulationRepository.insert(_) >>
                Simulation.builder()
                        .productId('id1')
                        .instalmentsQuantity(10)
                        .monthlyInterestRate(monthlyInterestRate)
                        .requestedAmount(1000)
                        .build()
        where:
        monthlyInterestRate << [1.0, 2.0]
    }

    @Unroll
    def 'Should throw NumberFormatException if monthly interest rate is not between product minimum and maximum rates'() {
        given:
        def simulationDTO = new SimulationDTO('id1', 10, monthlyInterestRate, 1000)
        def product = new Product('id1', 'product1', 'indexer1', 1.0, 2.0, 'institution1')
        1 * productService.findById('id1') >> product

        when:
        simulationService.simulate(simulationDTO)

        then:
        def t = thrown(NumberFormatException)
        t.message == 'The monthly interest rate must be between the product\'s minimum and maximum rates.'

        where:
        monthlyInterestRate << [0.09, 2.01]
    }
}
