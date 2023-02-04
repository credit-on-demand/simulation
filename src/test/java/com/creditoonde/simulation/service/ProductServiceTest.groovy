package com.creditoonde.simulation.service

import com.creditoonde.simulation.domain.Product
import com.creditoonde.simulation.repository.ProductRepository
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ProductServiceTest extends Specification {

    def productService = new ProductService()
    def productRepository = Mock(ProductRepository)

    def setup() {
        productService.repository = productRepository
    }

    def "Should insert new product"() {
        given:
        def product = new Product()

        when:
        productService.insert(product)

        then:
        1 * productRepository.insert(product)
    }

    def "Should find all products"() {
        when:
        productService.findAll()

        then:
        1 * productRepository.findAll()
    }

}
