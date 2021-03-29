package tech.cassandre.trading.bot.strategy

import spock.lang.Specification
import spock.lang.Unroll
import tech.cassandre.trading.bot.domain.Order
import tech.cassandre.trading.bot.domain.Trade
import tech.cassandre.trading.bot.dto.position.PositionDTO
import tech.cassandre.trading.bot.dto.position.PositionRulesDTO
import tech.cassandre.trading.bot.dto.position.PositionTypeDTO
import tech.cassandre.trading.bot.dto.strategy.StrategyDTO
import tech.cassandre.trading.bot.dto.trade.OrderDTO
import tech.cassandre.trading.bot.dto.trade.OrderStatusDTO
import tech.cassandre.trading.bot.dto.trade.TradeDTO
import tech.cassandre.trading.bot.dto.user.AccountDTO
import tech.cassandre.trading.bot.dto.util.CurrencyAmountDTO
import tech.cassandre.trading.bot.dto.util.CurrencyDTO
import tech.cassandre.trading.bot.dto.util.CurrencyPairDTO
import tech.cassandre.trading.bot.repository.OrderRepository
import tech.cassandre.trading.bot.repository.TradeRepository


@Unroll
class BasicCassandreStrategySpec extends Specification {

    TestStrategy strategy
    OrderRepository orderRepository
    TradeRepository tradeRepository

    Order order1
    Order order2

    Trade trade1

    def setup() {
        strategy = new TestStrategy()

        orderRepository = Mock(OrderRepository)
        tradeRepository = Mock(TradeRepository)
        strategy.orderRepository = orderRepository
        strategy.tradeRepository = tradeRepository
        order1 = Mock(Order)
        order2 = Mock(Order)

        trade1 = Mock(Trade)

    }

    def "should construct strategy"() {
        expect:
        strategy

        strategy.amountsLockedByPosition == [:]
        strategy.accounts == [:]
        strategy.lastTickers == [:]
    }

    def "should get list of orders"() {
        when:
        def orders = strategy.getOrders()

        then:
        1 * orderRepository.findByOrderByTimestampAsc() >> [order1, order2]

        1 * order1.orderId >> 'order1'
        1 * order1.trades >> []
        1 * order1.currencyPair >> 'BTC/USD'

        1 * order2.orderId >> 'order2'
        1 * order2.trades >> []
        1 * order2.currencyPair >> 'BTC/USD'

        orders
        orders.keySet().size() == 2
        orders.get('order1')
        orders.get('order2')

    }

    def "should get list of trades"() {
        when:
        def trades = strategy.getTrades()

        then:
        1 * tradeRepository.findByOrderByTimestampAsc() >> [trade1]

        1 * trade1.tradeId >> 13654
        1 * trade1.orderId >> 'orderid'
        1 * trade1.currencyPair >> 'BTC/USD'

        trades
        trades instanceof Map
        trades.keySet().size() == 1
        trades.containsValue(createTradeDTO('13654', new CurrencyPairDTO("BTC/USD"), 'orderid'))

    }

    def "should process position update and lock 0 when there is no opening position dto"() {
        when:
        strategy.positionUpdate(createNewLongPosition("o1"))

        then:
        strategy.lockedAmounts[1l] == createCurrencyAmount(CurrencyDTO.BTC, 0 as BigDecimal)
    }

    def "should process position update and lock amount when there is opening position dto"() {
        given:
        PositionDTO positionToUpdate = createNewLongPosition("orderId1")
        def amount = createCurrencyAmount(CurrencyDTO.BTC, 0.2 as BigDecimal)

        def order = createOrderDTO(amount, "orderId1")

        when: 'position is updated with order'
        def updateResult = positionToUpdate.orderUpdate(order)

        then:
        updateResult

        when: 'position is updated with position dto'
        def position = createPositionDTO(order, 'BTC/USD', amount)
        strategy.positionUpdate(position)

        then:
        strategy.lockedAmounts[1l] == createCurrencyAmount(CurrencyDTO.BTC, 0.2 as BigDecimal)
    }


    // TEST data
    class TestStrategy extends BasicCassandreStrategy {

        @Override
        Set<CurrencyPairDTO> getRequestedCurrencyPairs() {
            return null
        }

        @Override
        Optional<AccountDTO> getTradeAccount(Set<AccountDTO> accounts) {
            return null
        }

        Map<Long, CurrencyAmountDTO> getLockedAmounts() {
            return amountsLockedByPosition;
        }
    }

    PositionDTO createNewLongPosition(String orderId) {
        CurrencyPairDTO pair = new CurrencyPairDTO.CurrencyPairDTOBuilder()
                .baseCurrency(CurrencyDTO.BTC)
                .quoteCurrency(CurrencyDTO.USD)
                .build()
        PositionDTO dto = new PositionDTO(1L, PositionTypeDTO.LONG, GroovyMock(StrategyDTO), pair, 0.2, orderId, Mock(PositionRulesDTO))
        return dto
    }

    CurrencyAmountDTO createCurrencyAmount(final CurrencyDTO currencyDtoO, final BigDecimal amount) {
        return new CurrencyAmountDTO.CurrencyAmountDTOBuilder()
                .currency(currencyDtoO)
                .value(amount)
                .build()
    }

    OrderDTO createOrderDTO(CurrencyAmountDTO amount, String orderId) {
        return new OrderDTO.OrderDTOBuilder()
                .amount(amount)

                .orderId(orderId)
                .status(OrderStatusDTO.OPEN)
                .build()
    }

    PositionDTO createPositionDTO(OrderDTO order, String currencyPair, CurrencyAmountDTO currencyAmount) {
        return new PositionDTO.PositionDTOBuilder()
                .currencyPair(new CurrencyPairDTO(currencyPair))
                .amount(currencyAmount)
                .openingOrder(order)
                .build()
    }

    TradeDTO createTradeDTO(String tradeId, CurrencyPairDTO pair, String orderId) {
        return new TradeDTO.TradeDTOBuilder()
                .tradeId(tradeId)
                .currencyPair(pair)
                .orderId(orderId)
                .build()
    }
}
