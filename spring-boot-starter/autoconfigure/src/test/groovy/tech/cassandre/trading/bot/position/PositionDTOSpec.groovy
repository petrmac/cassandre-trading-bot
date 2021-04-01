package tech.cassandre.trading.bot.position

import spock.lang.Specification
import tech.cassandre.trading.bot.dto.position.PositionDTO
import tech.cassandre.trading.bot.dto.position.PositionStatusDTO
import tech.cassandre.trading.bot.dto.trade.OrderDTO
import tech.cassandre.trading.bot.dto.util.CurrencyAmountDTO
import tech.cassandre.trading.bot.dto.util.CurrencyDTO
import tech.cassandre.trading.bot.dto.util.CurrencyPair
import tech.cassandre.trading.bot.dto.util.Currency
import tech.cassandre.trading.bot.dto.util.CurrencyPairDTO

class PositionDTOSpec extends Specification {

    OrderDTO order
    PositionDTO position

    def setup() {
        order = GroovyMock(OrderDTO)
        position = new PositionDTO.PositionDTOBuilder()
        .amount(CurrencyAmountDTO.forValues(100 as BigDecimal, Currency.forValue("USD")))
        .currencyPair(CurrencyPair.forValue("BTC/USD"))
        .status(PositionStatusDTO.OPENED)
        .openingOrder(order)
        .openingOrderId('openingId')
        .build()
    }

    def "should do orderUpdate"() {
        given:
        OrderDTO order2 = GroovyMock(OrderDTO)

        when:
        def result = position.orderUpdate(order2)

        then:
        1 * order2.orderId >> 'openingId'
        result
    }

}
