package com.creditoonde.simulation.service

import com.creditoonde.simulation.controller.exception.ObjectNotFoundException
import com.creditoonde.simulation.domain.Product
import com.creditoonde.simulation.dto.ProductDTO
import com.creditoonde.simulation.repository.ProductRepository
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
class ProductServiceTest extends Specification {

    @Shared
    ProductService productService
    @Shared
    ProductRepository productRepository

    def setup() {
        productRepository = Mock(ProductRepository)
        productService = new ProductService(repository: productRepository)
    }

    def 'Should insert a product and return it'() {
        given:
        def product = new Product('id1', 'product1', 'indexer1', 1.0, 2.0, 'institution1')
        1 * productRepository.insert(product) >> product

        when:
        def created = productService.insert(product)

        then:
        created == product
    }

    def 'Should find all products'() {
        given:
        def productList =
                [new Product('id1', 'product1', 'indexer1', 1.0, 2.0, 'institution1'),
                 new Product('id2', 'product2', 'indexer2', 3.0, 4.0, 'institution2')]
        1 * productRepository.findAll() >> productList

        when:
        def result = productService.findAll()

        then:
        result == productList
    }

    def 'Should find product by id when product exists'() {
        given:
        def product = new Product('id1', 'product1', 'indexer1', 1.0, 2.0, 'institution1')
        1 * productRepository.findById('id1') >> Optional.of(product)

        when:
        def result = productService.findById('id1')

        then:
        result == product
    }

    def 'Should throw exception when product does not exist'() {
        given:
        productRepository.findById('id1') >> Optional.empty()

        when:
        productService.findById('id1')

        then:
        def t = thrown(ObjectNotFoundException)
        t.message == 'Product not found.'
    }

    def 'Should find product by FinancialInstitution'() {
        given:
        def productList =
                [new Product('id1', 'product1', 'indexer1', 1.0, 2.0, 'institution1'),
                 new Product('id2', 'product2', 'indexer2', 3.0, 4.0, 'institution1')]
        1 * productRepository.findByFinancialInstitutionContainingIgnoreCase('institution1') >> productList

        when:
        def result = productService.findByFinancialInstitution('institution1')

        then:
        result == productList
    }

    def 'Should delete a product'() {
        given:
        def product = new Product('id1', 'product1', 'indexer1', 1.0, 2.0, 'institution1')

        when:
        productService.delete('id1')

        then:
        1 * productRepository.findById('id1') >> Optional.of(product)
        1 * productRepository.deleteById('id1')
    }

    def 'Should update a product and return it'() {
        given:
        def product = new Product('123', 'Test product', 'CDI', 5.5, 10.0, 'Financial Institution')
        def updatedProduct = new Product('123', 'Test product updated', 'CDI', 5.5, 10.0, 'Financial Institution')
        1 * productRepository.save(product) >> updatedProduct
        1 * productRepository.findById('123') >> Optional.of(product)

        when:
        def result = productService.update(updatedProduct)

        then:
        result == updatedProduct
    }

    def 'Should convert ProductDTO to new Product'() {
        given:
        def productDTO = new ProductDTO(id: '123', name: 'test', financialInstitution: 'institution',
                minInterestRate: 0.1, maxInterestRate: 0.2, rateIndexer: 'indexer')

        when:
        def product = productService.fromDTO(productDTO)

        then:
        product.id == '123'
        product.name == 'test'
        product.financialInstitution == 'institution'
        product.minInterestRate == 0.1
        product.maxInterestRate == 0.2
    }

}
