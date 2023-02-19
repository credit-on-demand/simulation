package com.creditoonde.simulation.controller

import com.creditoonde.simulation.controller.exception.ObjectNotFoundException
import com.creditoonde.simulation.domain.Simulation
import com.creditoonde.simulation.service.SimulationService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.LocalDateTime

import static groovy.json.JsonOutput.toJson
import static org.hamcrest.Matchers.containsString
import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@AutoConfigureMockMvc
@SpringBootTest
class SimulationControllerTest extends Specification {

    @Autowired
    MockMvc mvc
    @SpringBean
    SimulationService simulationService = Mock()

    def 'Should create a simulation and return status created, empty response body and location header'() {
        given:
        def simulation = new Simulation('id1', 12, 2.0, 1200.0, 120.0, 'id1', 1440.0, LocalDateTime.now())
        1 * simulationService.simulate(_) >> simulation
        def request =
                [productId          : 'id1',
                 instalmentsQuantity: 12,
                 monthlyInterestRate: 2.0,
                 requestedAmount    : 1200.0]

        when:
        def result = mvc.perform(post('/simulations').contentType('application/json').content(toJson(request)))

        then:
        result.andExpect(status().isCreated())
                .andExpect(jsonPath('id').value(simulation.getId()))
                .andExpect(jsonPath('productId').value(simulation.getProductId()))
                .andExpect(jsonPath('instalmentsQuantity').value(simulation.getInstalmentsQuantity()))
                .andExpect(jsonPath('monthlyInterestRate').value(simulation.getMonthlyInterestRate()))
                .andExpect(jsonPath('requestedAmount').value(simulation.getRequestedAmount()))
                .andExpect(jsonPath('instalmentValue').value(simulation.getInstalmentValue()))
                .andExpect(jsonPath('totalAmount').value(simulation.getTotalAmount()))
                .andExpect(jsonPath('simulationDate').exists())
                .andExpect(header().string('Location', containsString('id1')))
    }

    def 'Should return all simulations in response body'() {
        given:
        def simulationList =
                [new Simulation('id1', 12, 2.0, 1200.0, 120.0, 'id1', 1440.0, LocalDateTime.now()),
                 new Simulation('id2', 12, 2.0, 1200.0, 120.0, 'id2', 1440.0, LocalDateTime.now())]
        1 * simulationService.findAll() >> simulationList

        when:
        def result = mvc.perform(get('/simulations'))

        then:
        result.andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(2)))
                .andExpect(jsonPath('$[0].id').value('id1'))
                .andExpect(jsonPath('$[1].id').value('id2'))
    }

    def 'Should return the correct simulation filtered by id in the response body'() {
        given:
        def id = 'id1'
        def simulation = new Simulation(id, 12, 2.0, 1200.0, 120.0, 'id1', 1440.0, LocalDateTime.now())
        1 * simulationService.findById(id) >> simulation

        when:
        def result = mvc.perform(get('/simulations/' + id))

        then:
        result.andExpect(status().isOk())
                .andExpect(jsonPath('id').value(simulation.getId()))
                .andExpect(jsonPath('productId').value(simulation.getProductId()))
                .andExpect(jsonPath('instalmentsQuantity').value(simulation.getInstalmentsQuantity()))
                .andExpect(jsonPath('monthlyInterestRate').value(simulation.getMonthlyInterestRate()))
                .andExpect(jsonPath('requestedAmount').value(simulation.getRequestedAmount()))
                .andExpect(jsonPath('instalmentValue').value(simulation.getInstalmentValue()))
                .andExpect(jsonPath('totalAmount').value(simulation.getTotalAmount()))
                .andExpect(jsonPath('simulationDate').exists())
    }

    def 'Should delete a product and return no content status'() {
        given:
        def id = 'id1'
        1 * simulationService.delete(id)

        when:
        def result = mvc.perform(delete('/simulations/' + id))

        then:
        result.andExpect(status().isNoContent())
    }

    def 'Should return not found status when simulation id is not found'() {
        given:
        def id = 'id1'
        def expectedErrorResponseBody = [
                status : 404,
                error  : 'Not found.',
                message: 'Simulation not found.',
                path   : '/simulations/' + id
        ]
        1 * simulationService.findById(id) >> { throw new ObjectNotFoundException(expectedErrorResponseBody.message) }

        when:
        def result = mvc.perform(get('/simulations/' + id))

        then:
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath('timestamp').exists())
                .andExpect(jsonPath('status').value(expectedErrorResponseBody.status))
                .andExpect(jsonPath('error').value(expectedErrorResponseBody.error))
                .andExpect(jsonPath('messages').value(expectedErrorResponseBody.message))
                .andExpect(jsonPath('path').value(expectedErrorResponseBody.path))
    }

    def 'Should return bad request status when request body is invalid'() {
        given:
        def request = [
                instalmentsQuantity: 24,
                monthlyInterestRate: 2.69,
                requestedAmount    : 1200
        ]
        def expectedErrorResponseBody = [
                status : 400,
                error  : 'Request body is not valid.',
                message: 'Simulation\'s productId cannot be null',
                path   : '/simulations'
        ]

        when:
        def result = mvc.perform(post('/simulations').contentType('application/json').content(toJson(request)))

        then:
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath('timestamp').exists())
                .andExpect(jsonPath('status').value(expectedErrorResponseBody.status))
                .andExpect(jsonPath('error').value(expectedErrorResponseBody.error))
                .andExpect(jsonPath('messages').value(expectedErrorResponseBody.message))
                .andExpect(jsonPath('path').value(expectedErrorResponseBody.path))
    }

    def 'Should return unprocessable entity status when simulation values are invalid'() {
        given:
        def request = [
                productId          : 'id1',
                instalmentsQuantity: 24,
                monthlyInterestRate: 8.99,
                requestedAmount    : 1200
        ]
        def expectedErrorResponseBody = [
                status : 422,
                error  : 'Simulation values are not valid.',
                message: 'One or more simulation values are invalid.',
                path   : '/simulations'
        ]
        1 * simulationService.simulate(_) >> { throw new NumberFormatException(expectedErrorResponseBody.message) }

        when:
        def result = mvc.perform(post('/simulations').contentType('application/json').content(toJson(request)))

        then:
        result.andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath('timestamp').exists())
                .andExpect(jsonPath('status').value(expectedErrorResponseBody.status))
                .andExpect(jsonPath('error').value(expectedErrorResponseBody.error))
                .andExpect(jsonPath('messages').value(expectedErrorResponseBody.message))
                .andExpect(jsonPath('path').value(expectedErrorResponseBody.path))
    }
}
