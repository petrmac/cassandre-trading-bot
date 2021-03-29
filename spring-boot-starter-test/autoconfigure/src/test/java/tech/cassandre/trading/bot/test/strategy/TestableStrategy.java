package tech.cassandre.trading.bot.test.strategy;

import tech.cassandre.trading.bot.dto.market.Ticker;
import tech.cassandre.trading.bot.dto.user.AccountDTO;
import tech.cassandre.trading.bot.dto.util.CurrencyPair;
import tech.cassandre.trading.bot.strategy.BasicCassandreStrategy;
import tech.cassandre.trading.bot.strategy.CassandreStrategy;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static tech.cassandre.trading.bot.dto.util.Currency.BTC;
import static tech.cassandre.trading.bot.dto.util.Currency.ETH;
import static tech.cassandre.trading.bot.dto.util.Currency.USDT;

/**
 * Testable strategy.
 */
@SuppressWarnings("unused")
@CassandreStrategy(strategyName = "Testable strategy")
public final class TestableStrategy extends BasicCassandreStrategy {

    /** Tickers update received. */
    private final List<Ticker> tickersUpdateReceived = new LinkedList<>();

    @Override
    public Set<CurrencyPair> getRequestedCurrencyPairs() {
        Set<CurrencyPair> list = new LinkedHashSet<>();
        list.add(CurrencyPair.forValues(BTC, USDT));
        list.add(CurrencyPair.forValues(ETH, BTC));
        return list;
    }

    @Override
    public Optional<AccountDTO> getTradeAccount(Set<AccountDTO> accounts) {
        return accounts.stream().filter(a -> a.getAccountId().equals("trade")).findFirst();
    }

    @Override
    public void onTickerUpdate(Ticker ticker) {
        tickersUpdateReceived.add(ticker);
    }

    /**
     * Getter tickersUpdateReceived.
     *
     * @return tickersUpdateReceived
     */
    public final List<Ticker> getTickersUpdateReceived() {
        return tickersUpdateReceived;
    }

}
