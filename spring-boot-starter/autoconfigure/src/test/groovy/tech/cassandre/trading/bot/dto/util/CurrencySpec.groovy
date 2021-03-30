package tech.cassandre.trading.bot.dto.util

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class CurrencySpec extends Specification {

    def "should return '#expected' currency from string value: '#currencyString'"(String currencyString,
                                                                                  Currency expected,
                                                                                  String expectedDisplay,
                                                                                  String expectedSymbol) {
        when:
        def result = Currency.forString(currencyString)

        then:
        result == expected
        result.displayName == expectedDisplay
        result.symbol == expectedSymbol

        where:
        currencyString | expected     | expectedDisplay               | expectedSymbol
        'AED'          | Currency.AED | 'United Arab Emirates Dirham' | 'AED'
        'AFN'          | Currency.AFN | 'Afghan Afghani'              | 'AFN'
        'ALL'          | Currency.ALL | 'Albanian Lek'                | 'ALL'
        'AMD'          | Currency.AMD | 'Armenian Dram'               | 'AMD'
        'USD'          | Currency.USD | 'United States Dollar'        | '$'


    }
}
