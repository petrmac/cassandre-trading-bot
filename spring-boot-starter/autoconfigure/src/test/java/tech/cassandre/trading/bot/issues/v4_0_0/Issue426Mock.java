package tech.cassandre.trading.bot.issues.v4_0_0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import tech.cassandre.trading.bot.batch.OrderFlux;
import tech.cassandre.trading.bot.batch.TickerFlux;
import tech.cassandre.trading.bot.batch.TradeFlux;
import tech.cassandre.trading.bot.dto.market.TickerDTO;
import tech.cassandre.trading.bot.dto.trade.OrderDTO;
import tech.cassandre.trading.bot.dto.trade.TradeDTO;
import tech.cassandre.trading.bot.dto.util.CurrencyAmountDTO;
import tech.cassandre.trading.bot.dto.util.CurrencyPair;
import tech.cassandre.trading.bot.dto.util.CurrencyPairDTO;
import tech.cassandre.trading.bot.repository.OrderRepository;
import tech.cassandre.trading.bot.repository.PositionRepository;
import tech.cassandre.trading.bot.repository.TradeRepository;
import tech.cassandre.trading.bot.service.MarketService;
import tech.cassandre.trading.bot.service.TradeService;
import tech.cassandre.trading.bot.test.util.junit.BaseTest;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static tech.cassandre.trading.bot.dto.trade.OrderStatusDTO.FILLED;
import static tech.cassandre.trading.bot.dto.trade.OrderStatusDTO.NEW;
import static tech.cassandre.trading.bot.dto.trade.OrderTypeDTO.ASK;
import static tech.cassandre.trading.bot.dto.util.CurrencyDTO.BTC;
import static tech.cassandre.trading.bot.dto.util.CurrencyDTO.ETH;
import static tech.cassandre.trading.bot.dto.util.CurrencyDTO.USDT;

@SuppressWarnings("unchecked")
@TestConfiguration
public class Issue426Mock extends BaseTest {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Bean
    @Primary
    public TickerFlux tickerFlux() {
        return new TickerFlux(marketService());
    }

    @Bean
    @Primary
    public OrderFlux orderFlux() {
        return new OrderFlux(tradeService(), orderRepository);
    }

    @Bean
    @Primary
    public TradeFlux tradeFlux() {
        return new TradeFlux(tradeService(), orderRepository, tradeRepository);
    }

    @Bean
    @Primary
    public MarketService marketService() {
        // Creates the mock.
        MarketService marketService = mock(MarketService.class);

        // Replies for ETH / BTC.
        final CurrencyPair cp1 = CurrencyPair.forValues(ETH, BTC);
        given(marketService
                .getTicker(cp1))
                .willReturn(
                        Optional.of(TickerDTO.builder().currencyPair(cp1).timestamp(createZonedDateTime(1)).last(new BigDecimal("0.2")).build()),
                        Optional.of(TickerDTO.builder().currencyPair(cp1).timestamp(createZonedDateTime(2)).last(new BigDecimal("0.3")).build()),
                        Optional.of(TickerDTO.builder().currencyPair(cp1).timestamp(createZonedDateTime(3)).last(new BigDecimal("0.4")).build()),
                        Optional.of(TickerDTO.builder().currencyPair(cp1).timestamp(createZonedDateTime(4)).last(new BigDecimal("0.4")).build())
                );
        // Replies for ETH / USDT.
        final CurrencyPair cp2 = CurrencyPair.forValues(ETH, USDT);
        given(marketService
                .getTicker(cp2))
                .willReturn(
                        Optional.of(TickerDTO.builder().currencyPair(cp2).timestamp(createZonedDateTime(5)).last(new BigDecimal("0.3")).build()),
                        Optional.of(TickerDTO.builder().currencyPair(cp2).timestamp(createZonedDateTime(6)).last(new BigDecimal("0.3")).build()),
                        Optional.of(TickerDTO.builder().currencyPair(cp2).timestamp(createZonedDateTime(7)).last(new BigDecimal("0.6")).build()),
                        Optional.of(TickerDTO.builder().currencyPair(cp2).timestamp(createZonedDateTime(8)).last(new BigDecimal("0.1")).build())
                );
        return marketService;
    }

    @Bean
    @Primary
    public TradeService tradeService() {
        TradeService service = mock(TradeService.class);

        // getTrades() replies.
        given(service.getTrades(any())).willReturn(
                Set.of(TradeDTO.builder()
                                .tradeId("TRADE_000001")
                                .type(ASK)
                                .orderId("ORDER_000001")
                                .currencyPair(cp1)
                                .amount(new CurrencyAmountDTO("0.5", cp1.getBaseCurrency()))
                                .price(new CurrencyAmountDTO("1", cp1.getQuoteCurrency()))
                                .build(),
                        TradeDTO.builder()
                                .tradeId("TRADE_000001")
                                .type(ASK)
                                .orderId("ORDER_000001")
                                .currencyPair(cp1)
                                .amount(new CurrencyAmountDTO("0.5", cp1.getBaseCurrency()))
                                .price(new CurrencyAmountDTO("2", cp1.getQuoteCurrency()))
                                .build(),
                        TradeDTO.builder()
                                .tradeId("TRADE_000002")
                                .type(ASK)
                                .orderId("ORDER_000001")
                                .currencyPair(cp1)
                                .amount(new CurrencyAmountDTO("0.5", cp1.getBaseCurrency()))
                                .price(new CurrencyAmountDTO("2", cp1.getQuoteCurrency()))
                                .build(),
                        TradeDTO.builder()
                                .tradeId("TRADE_000003")
                                .type(ASK)
                                .orderId("ORDER_000002")
                                .currencyPair(cp1)
                                .amount(new CurrencyAmountDTO("0.5", cp1.getBaseCurrency()))
                                .price(new CurrencyAmountDTO("2", cp1.getQuoteCurrency()))
                                .build())
        );

        LinkedHashSet<OrderDTO> orders = new LinkedHashSet<>();
        orders.add(OrderDTO.builder()
                .orderId("ORDER_000001")
                .type(ASK)
                .currencyPair(cp1)
                .amount(new CurrencyAmountDTO("1", cp1.getBaseCurrency()))
                .averagePrice(new CurrencyAmountDTO("3", cp1.getQuoteCurrency()))
                .limitPrice(new CurrencyAmountDTO("5", cp1.getQuoteCurrency()))
                .leverage("leverage1")
                .status(NEW)
                .cumulativeAmount(new CurrencyAmountDTO("2", cp1.getBaseCurrency()))
                .userReference("MY_REF_1")
                .timestamp(ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")))
                .build());
        orders.add(OrderDTO.builder()
                .orderId("ORDER_000001")
                .type(ASK)
                .currencyPair(cp1)
                .amount(new CurrencyAmountDTO("1", cp1.getBaseCurrency()))
                .averagePrice(new CurrencyAmountDTO("3", cp1.getQuoteCurrency()))
                .limitPrice(new CurrencyAmountDTO("5", cp1.getQuoteCurrency()))
                .leverage("leverage1")
                .status(FILLED)
                .cumulativeAmount(new CurrencyAmountDTO("2", cp1.getBaseCurrency()))
                .userReference("MY_REF_1")
                .timestamp(ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")))
                .build());

        // getOrders() replies.
        given(service.getOrders()).willReturn(orders);

        return service;
    }

}
