package tech.cassandre.trading.bot.integration.kucoin;

import io.qase.api.annotation.CaseId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import tech.cassandre.trading.bot.dto.util.CurrencyPair;
import tech.cassandre.trading.bot.service.ExchangeService;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tech.cassandre.trading.bot.dto.util.Currency.BTC;
import static tech.cassandre.trading.bot.dto.util.Currency.ETH;
import static tech.cassandre.trading.bot.dto.util.Currency.KCS;
import static tech.cassandre.trading.bot.dto.util.Currency.USDT;

@SpringBootTest
@ActiveProfiles("schedule-disabled")
@TestPropertySource(properties = {
		"cassandre.trading.bot.exchange.name=${KUCOIN_NAME}",
		"cassandre.trading.bot.exchange.modes.sandbox=true",
		"cassandre.trading.bot.exchange.modes.dry=false",
		"cassandre.trading.bot.exchange.username=${KUCOIN_USERNAME}",
		"cassandre.trading.bot.exchange.passphrase=${KUCOIN_PASSPHRASE}",
		"cassandre.trading.bot.exchange.key=${KUCOIN_KEY}",
		"cassandre.trading.bot.exchange.secret=${KUCOIN_SECRET}",
		"cassandre.trading.bot.exchange.rates.account=100",
		"cassandre.trading.bot.exchange.rates.ticker=101",
		"cassandre.trading.bot.exchange.rates.trade=102",
		"cassandre.trading.bot.database.datasource.driver-class-name=org.hsqldb.jdbc.JDBCDriver",
		"cassandre.trading.bot.database.datasource.url=jdbc:hsqldb:mem:cassandre-database;shutdown=true",
		"cassandre.trading.bot.database.datasource.username=sa",
		"cassandre.trading.bot.database.datasource.password=",
		"testableStrategy.enabled=true",
		"invalidStrategy.enabled=false"
})
@DisplayName("Kucoin - Exchange service")
public class ExchangeServiceTest {

	@Autowired
	private ExchangeService exchangeService;

	@Test
	@Tag("integration")
	@CaseId(84)
	@DisplayName("Check get available currency pairs")
	public void checkGetAvailableCurrencyPairs() {
		// Expected values.
		final int expectedMinimumNumberOfAvailableCurrencyPairs = 4;

		// =============================================================================================================
		// Retrieve the available currency pairs.
		Set<CurrencyPair> currencyPairs = exchangeService.getAvailableCurrencyPairs();

		// ====================================symbols=========================================================================
		// Tests results.
		assertEquals(expectedMinimumNumberOfAvailableCurrencyPairs, currencyPairs.size());

		assertTrue(currencyPairs.contains(CurrencyPair.forValues("KCS", "USDT")));
		assertTrue(currencyPairs.contains(CurrencyPair.forValues(KCS, USDT)));
		assertTrue(currencyPairs.contains(CurrencyPair.forValues("ETH", "USDT")));
		assertTrue(currencyPairs.contains(CurrencyPair.forValues(ETH, USDT)));
		assertTrue(currencyPairs.contains(CurrencyPair.forValues("BTC", "USDT")));
		assertTrue(currencyPairs.contains(CurrencyPair.forValues(BTC, USDT)));
		assertTrue(currencyPairs.contains(CurrencyPair.forValues("ETH", "BTC")));
		assertTrue(currencyPairs.contains(CurrencyPair.forValues(ETH, BTC)));
	}

}
