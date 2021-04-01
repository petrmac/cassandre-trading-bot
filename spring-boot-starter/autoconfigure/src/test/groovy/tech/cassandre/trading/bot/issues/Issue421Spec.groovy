package tech.cassandre.trading.bot.issues

import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification
import tech.cassandre.trading.bot.batch.AccountFlux
import tech.cassandre.trading.bot.batch.OrderFlux
import tech.cassandre.trading.bot.batch.TickerFlux
import tech.cassandre.trading.bot.batch.TradeFlux
import tech.cassandre.trading.bot.configuration.ExchangeAutoConfiguration
import tech.cassandre.trading.bot.dto.position.PositionCreationResultDTO
import tech.cassandre.trading.bot.dto.position.PositionRulesDTO
import tech.cassandre.trading.bot.dto.trade.OrderDTO
import tech.cassandre.trading.bot.dto.util.CurrencyAmountDTO
import tech.cassandre.trading.bot.dto.util.CurrencyPair
import tech.cassandre.trading.bot.repository.OrderRepository
import tech.cassandre.trading.bot.service.dry.TradeServiceDryModeImplementation
import tech.cassandre.trading.bot.service.dry.UserServiceDryModeImplementation
import tech.cassandre.trading.bot.test.configuration.mapper.TestMapperConfiguration
import tech.cassandre.trading.bot.test.service.dry.PositionServiceDryModeTestMock
import tech.cassandre.trading.bot.test.util.junit.configuration.Configuration
import tech.cassandre.trading.bot.test.util.junit.configuration.Property
import tech.cassandre.trading.bot.test.util.strategies.TestableCassandreStrategy

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD
import static tech.cassandre.trading.bot.dto.trade.OrderStatusDTO.NEW
import static tech.cassandre.trading.bot.dto.trade.OrderTypeDTO.BID
import static tech.cassandre.trading.bot.dto.util.Currency.BTC
import static tech.cassandre.trading.bot.dto.util.Currency.ETH
import static tech.cassandre.trading.bot.test.util.junit.configuration.ConfigurationExtension.PARAMETER_EXCHANGE_DRY

@SpringBootTest
@DisplayName("Github issue 421")
@Configuration([
        @Property(key = PARAMETER_EXCHANGE_DRY, value = "true")
])
@ContextConfiguration(classes = [TradeServiceDryModeImplementation.class,
        UserServiceDryModeImplementation.class,
        AccountFlux.class,
        OrderFlux.class,
        TradeFlux.class
])
@EnableAutoConfiguration(
        exclude = [ExchangeAutoConfiguration.class])
@TestPropertySource(properties = [
        "cassandre.trading.bot.database.datasource.driver-class-name=org.hsqldb.jdbc.JDBCDriver",
        "cassandre.trading.bot.database.datasource.url=jdbc:hsqldb:mem:cassandre-database;shutdown=true",
        "cassandre.trading.bot.database.datasource.username=sa",
        "cassandre.trading.bot.database.datasource.password=",
])
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@Import([PositionServiceDryModeTestMock.class, TestMapperConfiguration.class])
class Issue421Spec extends Specification {

    @Autowired
    private TestableCassandreStrategy strategy

    @Autowired
    private OrderRepository orderRepository

    @Autowired
    private TickerFlux tickerFlux

    @Autowired
    private OrderFlux orderFlux

    CurrencyPair cp1


    def setup() {
        cp1 = CurrencyPair.forValues(ETH, BTC)
    }

    @DisplayName("Duplicated orders in database")
    def "should handle duplicate order correctly and produce only 1 trade"() {
        when:
        // First tickers - cp1 & cp2 (dry mode).
        // ETH, BTC - bid 0.2 / ask 0.2.
        // ETH, USDT - bid 0,3 / ask 0.3.
        tickerFlux.update()
        tickerFlux.update()

        // =============================================================================================================
        // The orders created arrives before the order is created locally by the position.
        orderFlux.emitValue(OrderDTO.builder()
                .orderId("DRY_ORDER_000000001")
                .type(BID)
                .strategy(strategy.getStrategyDTO())
                .currencyPair(cp1)
                .amount(new CurrencyAmountDTO(new BigDecimal("0.0001"), cp1.getBaseCurrency()))
                .status(NEW)
                .build())

        orderFlux.emitValue(OrderDTO.builder()
                .orderId("DRY_ORDER_000000001")
                .type(BID)
                .strategy(strategy.getStrategyDTO())
                .currencyPair(cp1)
                .amount(new CurrencyAmountDTO(new BigDecimal("0.0001"), cp1.getBaseCurrency()))
                .status(NEW)
                .build())


        // =============================================================================================================
        // Creates position 1 (ETH/BTC, 0.0001, 100% stop gain, price of 0.2).
        // As the order is validated and the trade arrives, the position should be opened.
        final PositionCreationResultDTO position1Result = strategy.createLongPosition(cp1,
                new BigDecimal("0.0001"),
                PositionRulesDTO.builder().stopGainPercentage(100f).build())

        then:
        position1Result.isSuccessful()
        orderRepository.count() == 1
    }

}
