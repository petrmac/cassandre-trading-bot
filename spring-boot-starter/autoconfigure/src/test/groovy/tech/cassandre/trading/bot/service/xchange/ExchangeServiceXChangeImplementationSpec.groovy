package tech.cassandre.trading.bot.service.xchange

import org.knowm.xchange.Exchange
import org.knowm.xchange.currency.CurrencyPair
import org.knowm.xchange.dto.meta.CurrencyPairMetaData
import org.knowm.xchange.dto.meta.ExchangeMetaData
import spock.lang.Specification

class ExchangeServiceXChangeImplementationSpec extends Specification {

    ExchangeServiceXChangeImplementation service

    Exchange exchange
    ExchangeMetaData metaData

    CurrencyPair btcUsd

    def setup() {
        exchange = Mock(Exchange)
        service = new ExchangeServiceXChangeImplementation(exchange)

        btcUsd = new CurrencyPair('BTC/USD')
    }

    def "should get available currency pairs"() {
        given:
        metaData = Mock(ExchangeMetaData)
        Map<CurrencyPair, CurrencyPairMetaData> metaDataMap = new HashMap<>()
        metaDataMap.put(btcUsd, Mock(CurrencyPairMetaData))

        when:
        def pairs = service.availableCurrencyPairs

        then:
        pairs
        pairs.size() == 1
        pairs.contains(tech.cassandre.trading.bot.dto.util.CurrencyPair.forValue('BTC/USD'))

        1 * exchange.exchangeMetaData >> metaData
        1 * metaData.currencyPairs >>  metaDataMap
    }

    def "should throw NPE when asking for the available currency pairs when meta data is not present "() {
        when:
        service.availableCurrencyPairs

        then:
        thrown(NullPointerException)

        1 * exchange.exchangeMetaData >> null
    }
}
