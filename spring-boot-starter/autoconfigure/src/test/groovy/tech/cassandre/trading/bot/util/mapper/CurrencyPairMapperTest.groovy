package tech.cassandre.trading.bot.util.mapper

import org.knowm.xchange.derivative.FuturesContract
import spock.lang.Specification
import tech.cassandre.trading.bot.dto.util.Currency
import tech.cassandre.trading.bot.dto.util.CurrencyPair

class CurrencyPairMapperTest extends Specification {

    CurrencyPairMapper mapper

    def setup() {
        mapper = new CurrencyPairMapperImpl()
    }

    def "should map to domain object from string"() {
        when:
        def result = mapper.toDomainCurrencyPair("BTC/USD")

        then:
        result
        result instanceof CurrencyPair
    }

    def "should map to domain object from instrument"() {
        when:
        def result = mapper.toDomainCurrencyPair(new org.knowm.xchange.currency.CurrencyPair('BTC/USD'))

        then:
        result
        result instanceof CurrencyPair
    }

    def "should not map to domain object from instrument when instrument is futures contract"() {
        when:
        def result = mapper.toDomainCurrencyPair(new FuturesContract('MES'))

        then:
        thrown(IllegalArgumentException)
    }

    def "should map from domain object to Xchange"() {
        when:
        def result = mapper.toXchangeCurrencyPair('BTC/USD')

        then:
        result
        result instanceof org.knowm.xchange.currency.CurrencyPair
    }

    def "should map from domain object to String"() {
        when:
        def result = mapper.toString(CurrencyPair.forValues(Currency.BTC, Currency.USD))

        then:
        result == 'BTC/USD'
    }
}
