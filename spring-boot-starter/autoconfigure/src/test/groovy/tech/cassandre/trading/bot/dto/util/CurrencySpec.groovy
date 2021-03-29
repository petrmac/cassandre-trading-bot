package tech.cassandre.trading.bot.dto.util

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class CurrencySpec extends Specification {

    def "should return '#expected' currency from string value: '#currencyString'"(String currencyString,
                                                                                 Currency expected,
                                                                                 String expectedDisplay) {
        when:
        def result = Currency.forString(currencyString)

        then:
        result == expected
        result.displayName == expectedDisplay

        where:
        currencyString | expected     | expectedDisplay
        'AED'          | Currency.AED | 'United Arab Emirates Dirham'
        'AFN'          | Currency.AFN | 'Afghan Afghani'
        'ALL'          | Currency.ALL | 'Albanian Lek'
        'AMD'          | Currency.AMD | 'Armenian Dram'


    }
}
