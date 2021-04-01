package tech.cassandre.trading.bot.integration.coinbasepro;

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

@SpringBootTest
@ActiveProfiles("schedule-disabled")
@TestPropertySource(properties = {
		"cassandre.trading.bot.exchange.name=${COINBASE_PRO_NAME}",
		"cassandre.trading.bot.exchange.modes.sandbox=true",
		"cassandre.trading.bot.exchange.modes.dry=false",
		"cassandre.trading.bot.exchange.username=${COINBASE_PRO_USERNAME}",
		"cassandre.trading.bot.exchange.passphrase=${COINBASE_PRO_PASSPHRASE}",
		"cassandre.trading.bot.exchange.key=${COINBASE_PRO_KEY}",
		"cassandre.trading.bot.exchange.secret=${COINBASE_PRO_SECRET}",
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
@DisplayName("Coinbase pro - Exchange service")
public class ExchangeServiceTest {

	@Autowired
	private ExchangeService exchangeService;

	@Test
	@Tag("integration")
	@CaseId(101)
	@DisplayName("Check get available currency pairs")
	public void checkGetAvailableCurrencyPairs() {
		// Expected values.
		final int expectedMinimumNumberOfAvailableCurrencyPairs = 6;

		// =============================================================================================================
		// Retrieve the available currency pairs.
		Set<CurrencyPair> currencyPairs = exchangeService.getAvailableCurrencyPairs();

		// ====================================symbols=========================================================================
		// Tests results.
		assertEquals(expectedMinimumNumberOfAvailableCurrencyPairs, currencyPairs.size());

		assertTrue(currencyPairs.contains(CurrencyPair.forValues("LINK", "USDC")));
		assertTrue(currencyPairs.contains(CurrencyPair.forValues("BTC", "EUR")));
		assertTrue(currencyPairs.contains(CurrencyPair.forValues("ETH", "BTC")));
		assertTrue(currencyPairs.contains(CurrencyPair.forValues("BAT", "USDC")));
		assertTrue(currencyPairs.contains(CurrencyPair.forValues("BTC", "GBP")));
		assertTrue(currencyPairs.contains(CurrencyPair.forValues("BTC", "USD")));
	}

}
