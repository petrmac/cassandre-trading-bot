package tech.cassandre.trading.bot.test.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import tech.cassandre.trading.bot.dto.market.Ticker;
import tech.cassandre.trading.bot.dto.market.TickerDTO;
import tech.cassandre.trading.bot.dto.util.CurrencyPair;
import tech.cassandre.trading.bot.service.MarketService;
import tech.cassandre.trading.bot.test.strategy.TestableStrategy;
import tech.cassandre.trading.bot.test.util.BaseTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tech.cassandre.trading.bot.dto.util.Currency.BTC;
import static tech.cassandre.trading.bot.dto.util.Currency.ETH;
import static tech.cassandre.trading.bot.dto.util.Currency.KCS;
import static tech.cassandre.trading.bot.dto.util.Currency.USDT;

@SpringBootTest
@Import(TickerFluxMock.class)
@DisplayName("Ticker flux mock test")
public class TickerFluxMockTest extends BaseTest {

    @Autowired
    private TestableStrategy strategy;

    @Autowired
    private TickerFluxMock tickerFluxMock;

    @Autowired
    private MarketService marketService;

    @Test
    @DisplayName("Check tickers received")
    public void checkTickersReceived() {
        CurrencyPair cp1 = CurrencyPair.forValues(BTC, USDT);
        CurrencyPair cp2 = CurrencyPair.forValues(ETH, BTC);
        CurrencyPair cp3 = CurrencyPair.forValues(KCS, USDT);

        // Check the files we found.
        List<Resource> resources = tickerFluxMock.getFilesToLoad();
        assertEquals(3, resources.size());

        // Check file 1 (BTC-USDT).
        Resource file1 = resources.get(0);
        assertNotNull(file1);
        assertNotNull(file1.getFilename());
        assertTrue(file1.getFilename().contains("tickers-BTC-USDT.tsv"));
        assertEquals(cp1, tickerFluxMock.getCurrencyPairFromFileName(file1));

        // Check file 2 (ETC-BTC).
        Resource file2 = resources.get(1);
        assertNotNull(file2);
        assertNotNull(file2.getFilename());
        assertTrue(file2.getFilename().contains("tickers-ETH-BTC.tsv"));
        assertEquals(cp2, tickerFluxMock.getCurrencyPairFromFileName(file2));

        // Check file 2 (ETC-BTC).
        Resource file3 = resources.get(2);
        assertNotNull(file3);
        assertNotNull(file3.getFilename());
        assertTrue(file3.getFilename().contains("tickers-KCS-USDT.csv"));
        assertEquals(cp3, tickerFluxMock.getCurrencyPairFromFileName(file3));

        // Checking results.
        await().untilAsserted(() -> assertTrue(tickerFluxMock.isFluxDone(cp1)));
        await().untilAsserted(() -> assertTrue(tickerFluxMock.isFluxDone(cp2)));
        assertFalse(tickerFluxMock.isFluxDone(cp3));
        assertFalse(tickerFluxMock.isFluxDone());
        List<Ticker> tickersReceived = strategy.getTickersUpdateReceived();
        assertEquals(1508371200000L, tickersReceived.get(0).getTimestamp().toInstant().toEpochMilli());
        assertEquals(1508544000000L, tickersReceived.get(1).getTimestamp().toInstant().toEpochMilli());
        assertEquals(1508457600000L, tickersReceived.get(2).getTimestamp().toInstant().toEpochMilli());
        assertEquals(1508630400000L, tickersReceived.get(3).getTimestamp().toInstant().toEpochMilli());
        assertEquals(1508803200000L, tickersReceived.get(4).getTimestamp().toInstant().toEpochMilli());
        assertEquals(1508716800000L, tickersReceived.get(5).getTimestamp().toInstant().toEpochMilli());

        // Checking some data.
        final Optional<TickerDTO> ticker1 = marketService.getTicker(cp3);
        assertTrue(ticker1.isPresent());
        assertEquals(1601596800000L, ticker1.get().getTimestamp().toInstant().toEpochMilli());
        assertEquals(0, new BigDecimal("0.85652").compareTo(ticker1.get().getLast()));
    }

}
