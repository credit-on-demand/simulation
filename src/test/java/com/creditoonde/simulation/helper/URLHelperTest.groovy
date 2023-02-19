package com.creditoonde.simulation.helper

import spock.lang.Specification

class URLHelperTest extends Specification {

    def 'Should decode the text successfully'() {
        given:
        def textToDecode = 'hello%20world'

        when:
        def result = URLHelper.decodeParam(textToDecode)

        then:
        result == 'hello world'
    }

    def 'Should return empty string when decoding unsupported text'() {
        given:
        def textToDecode = 'invalid%'

        when:
        def result = URLHelper.decodeParam(textToDecode)

        then:
        result == ''
    }
}
