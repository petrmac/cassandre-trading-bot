package tech.cassandre.trading.bot.test.service.dry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import tech.cassandre.trading.bot.batch.TickerFlux;
import tech.cassandre.trading.bot.dto.market.TickerDTO;
import tech.cassandre.trading.bot.dto.util.CurrencyPair;
import tech.cassandre.trading.bot.service.MarketService;
import tech.cassandre.trading.bot.test.util.junit.BaseTest;
import tech.cassandre.trading.bot.util.mapper.MapperService;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static tech.cassandre.trading.bot.dto.util.Currency.BTC;
import static tech.cassandre.trading.bot.dto.util.Currency.ETH;
import static tech.cassandre.trading.bot.dto.util.Currency.USDT;

@SuppressWarnings("unchecked")
@TestConfiguration
public class PositionServiceDryModeTestMock extends BaseTest {


    @Autowired
    protected MapperService mapperService;

    @Bean
    @Primary
    public TickerFlux tickerFlux() {
        return new TickerFlux(mapperService, marketService());
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

}
