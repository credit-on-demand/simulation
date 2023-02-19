package com.creditoonde.simulation.controller

import com.creditoonde.simulation.controller.exception.ObjectNotFoundException
import com.creditoonde.simulation.domain.Product
import com.creditoonde.simulation.service.ProductService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static groovy.json.JsonOutput.toJson
import static org.hamcrest.Matchers.containsString
import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@AutoConfigureMockMvc
@SpringBootTest
class ProductControllerTest extends Specification {

    @Autowired
    MockMvc mvc
    @SpringBean
    ProductService productService = Mock()

    def 'Should return all products in the response body'() {
        given:
        def productList =
                [new Product('id1', 'product1', 'indexer1', 1.0, 2.0, 'institution1'),
                 new Product('id2', 'product2', 'indexer2', 3.0, 4.0, 'institution1')]
        1 * productService.findAll() >> productList

        when:
        def result = mvc.perform(get('/products'))

        then:
        result.andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(2)))
                .andExpect(jsonPath('$[0].id').value('id1'))
                .andExpect(jsonPath('$[0].name').value('product1'))
                .andExpect(jsonPath('$[1].id').value('id2'))
                .andExpect(jsonPath('$[1].name').value('product2'))
    }

    def 'Should return the correct product filtered by id in the response body'() {
        given:
        def id = 'id1'
        def product = new Product(id, 'product', 'indexer', 1.0, 2.5, 'institution')
        1 * productService.findById(id) >> product

        when:
        def result = mvc.perform(get('/products/' + id))

        then:
        result.andExpect(status().isOk())
                .andExpect(jsonPath('id').value(id))
                .andExpect(jsonPath('name').value('product'))
                .andExpect(jsonPath('rateIndexer').value('indexer'))
                .andExpect(jsonPath('minInterestRate').value(1.0))
                .andExpect(jsonPath('maxInterestRate').value(2.5))
                .andExpect(jsonPath('financialInstitution').value('institution'))
    }

    def 'Should return products filtered by financial institution in the response body'() {
        given:
        def productList = [new Product('id1', 'product1', 'indexer1', 1.0, 2.0, 'institution1')]
        1 * productService.findByFinancialInstitution('institution1') >> productList

        when:
        def result = mvc.perform(get('/products/financialInstitution').param('name', 'institution1'))

        then:
        result.andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(1)))
                .andExpect(jsonPath('$[0].id').value('id1'))
                .andExpect(jsonPath('$[0].financialInstitution').value('institution1'))
    }

    def 'Should create a product and return status created, empty response body and location header'() {
        given:
        def product = new Product('id1', 'product', 'indexer', 1.0, 2.5, 'institution')
        1 * productService.insert(_) >> product
        def request =
                [name                : 'product',
                 rateIndexer         : 'indexer',
                 minInterestRate     : 1.0,
                 maxInterestRate     : 2.5,
                 financialInstitution: 'institution']

        when:
        def result = mvc.perform(post('/products').contentType('application/json').content(toJson(request)))

        then:
        result.andExpect(status().isCreated())
                .andExpect(content().string(''))
                .andExpect(header().string('Location', containsString('id1')))
    }

    def 'Should delete a product and return no content status'() {
        given:
        def id = 'id1'
        1 * productService.delete(id)

        when:
        def result = mvc.perform(delete('/products/' + id))

        then:
        result.andExpect(status().isNoContent())
    }

    def 'Should update a product and return no content status'() {
        given:
        def id = 'id1'
        def product = new Product(id, 'product', 'indexer', 1.0, 2.5, 'institution')
        1 * productService.fromDTO(_) >> product
        1 * productService.update(product)

        def request =
                [name                : 'product',
                 rateIndexer         : 'indexer',
                 minInterestRate     : 1.0,
                 maxInterestRate     : 2.5,
                 financialInstitution: 'institution']

        when:
        def result = mvc.perform(put('/products/' + id).contentType('application/json').content(toJson(request)))

        then:
        result.andExpect(status().isNoContent())
    }

    def 'Should return not found status when product id is not found'() {
        given:
        def id = 'id1'
        def expectedErrorResponseBody = [
                status : 404,
                error  : 'Not found.',
                message: 'Product not found.',
                path   : '/products/' + id
        ]
        1 * productService.findById(id) >> { throw new ObjectNotFoundException(expectedErrorResponseBody.message) }

        when:
        def result = mvc.perform(get('/products/' + id))

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
                rateIndexer         : 'indexer',
                minInterestRate     : 1.0,
                maxInterestRate     : 2.5,
                financialInstitution: 'institution'
        ]
        def expectedErrorResponseBody = [
                status : 400,
                error  : 'Request body is not valid.',
                message: 'Product\'s name cannot be null',
                path   : '/products'
        ]

        when:
        def result = mvc.perform(post('/products').contentType('application/json').content(toJson(request)))

        then:
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath('timestamp').exists())
                .andExpect(jsonPath('status').value(expectedErrorResponseBody.status))
                .andExpect(jsonPath('error').value(expectedErrorResponseBody.error))
                .andExpect(jsonPath('messages').value(expectedErrorResponseBody.message))
                .andExpect(jsonPath('path').value(expectedErrorResponseBody.path))
    }
}
