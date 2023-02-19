package com.creditoonde.simulation.helper

import spock.lang.Specification
import spock.lang.Unroll

class SimulationHelperTest extends Specification {

    @Unroll
    def 'Should calculate instalment value'() {
        expect:
        SimulationHelper
                .calculateInstalmentValue(instalmentsQuantity, monthlyInterest, totalAmount) == result
        where:
        instalmentsQuantity | monthlyInterest          | totalAmount               | result
        12                  | BigDecimal.valueOf(2)    | BigDecimal.valueOf(1200)  | 113.47
        24                  | BigDecimal.valueOf(2)    | BigDecimal.valueOf(1200)  | 63.45
        48                  | BigDecimal.valueOf(3.69) | BigDecimal.valueOf(1380)  | 61.77
        120                 | BigDecimal.valueOf(4.99) | BigDecimal.valueOf(80500) | 4028.63
        2                   | BigDecimal.valueOf(0.1)  | BigDecimal.valueOf(100)   | 50.08
    }

    @Unroll
    def 'Should show error message when calculating instalment values are invalid'() {
        when:
        SimulationHelper
                .calculateInstalmentValue(instalmentsQuantity, monthlyInterest, totalAmount)
        then:
        def e = thrown(NumberFormatException)
        e.getMessage() == 'One or more simulation values are invalid.'

        where:
        instalmentsQuantity | monthlyInterest       | totalAmount
        0                   | BigDecimal.valueOf(2) | BigDecimal.valueOf(1200)
        12                  | BigDecimal.valueOf(0) | BigDecimal.valueOf(1200)
        12                  | BigDecimal.valueOf(2) | BigDecimal.valueOf(0)
        12                  | null                  | BigDecimal.valueOf(1200)
        12                  | BigDecimal.valueOf(2) | null
    }

    @Unroll
    def 'Should calculate total amount'() {
        expect:
        SimulationHelper
                .calculateTotalAmount(instalmentsQuantity, instalmentsValue) == result

        where:
        instalmentsQuantity | instalmentsValue            | result
        24                  | BigDecimal.valueOf(63.45)   | 1522.8
        48                  | BigDecimal.valueOf(1058.29) | 50797.92
        2                   | BigDecimal.valueOf(88.88)   | 177.76
    }

    @Unroll
    def 'Should show error message when calculating total amount values are invalid'() {
        when:
        SimulationHelper
                .calculateTotalAmount(instalmentsQuantity, instalmentsValue)

        then:
        def e = thrown(NumberFormatException)
        e.getMessage() == 'One or more values are invalid.'

        where:
        instalmentsQuantity | instalmentsValue
        0                   | BigDecimal.valueOf(63.45)
        12                  | BigDecimal.valueOf(0)
        12                  | null
    }
}
