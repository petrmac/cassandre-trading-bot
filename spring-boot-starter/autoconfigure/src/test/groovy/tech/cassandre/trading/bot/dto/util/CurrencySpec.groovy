package tech.cassandre.trading.bot.dto.util

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class CurrencySpec extends Specification {

    def "should return '#expected' currency from string value: '#currencyString"(String currencyString, Currency expected) {
        when:
        def currency = Currency.forString(currency)

        then:
        currency == expected

        where:
        currency     | expected
        Currency.AED | "AED"

    }
}
