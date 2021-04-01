package tech.cassandre.trading.bot.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import tech.cassandre.trading.bot.dto.market.Ticker;
import tech.cassandre.trading.bot.dto.position.PositionCreationResultDTO;
import tech.cassandre.trading.bot.dto.position.PositionDTO;
import tech.cassandre.trading.bot.dto.position.PositionRulesDTO;
import tech.cassandre.trading.bot.dto.position.PositionStatusDTO;
import tech.cassandre.trading.bot.dto.strategy.StrategyDTO;
import tech.cassandre.trading.bot.dto.trade.OrderCreationResultDTO;
import tech.cassandre.trading.bot.dto.trade.OrderDTO;
import tech.cassandre.trading.bot.dto.trade.TradeDTO;
import tech.cassandre.trading.bot.dto.user.AccountDTO;
import tech.cassandre.trading.bot.dto.user.BalanceDTO;
import tech.cassandre.trading.bot.dto.util.Currency;
import tech.cassandre.trading.bot.dto.util.CurrencyAmountDTO;
import tech.cassandre.trading.bot.dto.util.CurrencyPair;
import tech.cassandre.trading.bot.dto.util.GainDTO;
import tech.cassandre.trading.bot.repository.OrderRepository;
import tech.cassandre.trading.bot.repository.PositionRepository;
import tech.cassandre.trading.bot.repository.TradeRepository;
import tech.cassandre.trading.bot.service.PositionService;
import tech.cassandre.trading.bot.service.TradeService;
import tech.cassandre.trading.bot.util.mapper.CurrencyMapper;
import tech.cassandre.trading.bot.util.mapper.MapperService;
import tech.cassandre.trading.bot.util.mapper.OrderMapper;
import tech.cassandre.trading.bot.util.mapper.PositionMapper;
import tech.cassandre.trading.bot.util.mapper.TradeMapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;
import static tech.cassandre.trading.bot.dto.position.PositionStatusDTO.CLOSED;

/**
 * Generic Cassandre strategy implementation.
 */
@SuppressWarnings("checkstyle:DesignForExtension")
public abstract class GenericCassandreStrategy implements CassandreStrategyInterface {

    /** Mapper service. */
    protected MapperService mapperService;

    /** Strategy. */
    private StrategyDTO strategyDTO;

    /** Order repository. */
    private OrderRepository orderRepository;

    /** Trade repository. */
    private TradeRepository tradeRepository;

    /** Position repository. */
    private PositionRepository positionRepository;

    /** Trade service. */
    private TradeService tradeService;

    /** Position service. */
    private PositionService positionService;

    /** The accounts owned by the user. */
    private final Map<String, AccountDTO> accounts = new LinkedHashMap<>();

    /** Positions previous status. */
    private final Map<Long, PositionStatusDTO> previousPositionsStatus = new LinkedHashMap<>();

    /** Amounts locked by positions. */
    private final Map<Long, CurrencyAmountDTO> amountsLockedByPosition = new ConcurrentHashMap<>();

    /** Last ticker received. */
    private final Map<CurrencyPair, Ticker> lastTickers = new LinkedHashMap<>();

    // =================================================================================================================
    // Internal methods to setup dependencies.

    /**
     * Getter strategyDTO.
     *
     * @return strategyDTO
     */
    public final StrategyDTO getStrategyDTO() {
        return strategyDTO;
    }

    @Override
    public final void setStrategyDTO(final StrategyDTO newStrategyDTO) {
        this.strategyDTO = newStrategyDTO;
    }

    @Override
    public final void setPositionRepository(final PositionRepository newPositionRepository) {
        this.positionRepository = newPositionRepository;
    }

    @Override
    public final void setOrderRepository(final OrderRepository newOrderRepository) {
        this.orderRepository = newOrderRepository;
    }

    @Override
    public final void setTradeRepository(final TradeRepository newTradeRepository) {
        this.tradeRepository = newTradeRepository;
    }

    @Override
    public final void setTradeService(final TradeService newTradeService) {
        this.tradeService = newTradeService;
    }

    public void setMapperService(final MapperService mapperService) {
        this.mapperService = mapperService;
    }

    @Override
    public final void setPositionService(final PositionService newPositionService) {
        this.positionService = newPositionService;
        // We set the previous positions status from database.
        this.positionService.getPositions()
                .stream()
                .filter(p -> p.getStatus() != CLOSED)
                .forEach(p -> previousPositionsStatus.put(p.getId(), p.getStatus()));
        // We set the locked amount from database.
        // TODO Add a test on this.
        this.positionService.getPositions()
                .stream()
                .filter(p -> p.getStatus() != CLOSED)
                .forEach(p -> amountsLockedByPosition.put(p.getId(), p.getAmountToLock()));
    }

    // =================================================================================================================
    // Internal methods for event management.

    @Override
    public void accountUpdate(final AccountDTO account) {
        accounts.put(account.getAccountId(), account);
        onAccountUpdate(account);
    }

    @Override
    public void tickerUpdate(final Ticker ticker) {
        lastTickers.put(ticker.getCurrencyPair(), ticker);
        onTickerUpdate(ticker);
    }

    @Override
    public void orderUpdate(final OrderDTO order) {
        onOrderUpdate(order);
    }

    @Override
    public void tradeUpdate(final TradeDTO trade) {
        onTradeUpdate(trade);
    }

    @Override
    public void positionUpdate(final PositionDTO position) {
        // On every position update, we update the position locked.
        amountsLockedByPosition.put(position.getId(), position.getAmountToLock());
        if (position.getStatus() == CLOSED) {
            amountsLockedByPosition.remove(position.getId());
        }

        // For every position update.
        onPositionUpdate(position);

        // From positionUpdate(), we see if it's also a onPositionStatusUpdate().
        if (previousPositionsStatus.get(position.getId()) != position.getStatus()) {
            previousPositionsStatus.put(position.getId(), position.getStatus());
            if (position.getStatus() == CLOSED) {
                // As CLOSED positions cannot change anymore, we don't need to store their previous positions.
                previousPositionsStatus.remove(position.getId());
            }
            onPositionStatusUpdate(position);
        }
    }

    // =================================================================================================================
    // Related to accounts.

    /**
     * Returns list of accounts.
     *
     * @return accounts
     */
    public final Map<String, AccountDTO> getAccounts() {
        return accounts;
    }

    /**
     * Search and return and account by its id.
     *
     * @param accountId account id
     * @return account
     */
    public final Optional<AccountDTO> getAccountByAccountId(final String accountId) {
        if (accounts.containsKey(accountId)) {
            return Optional.of(accounts.get(accountId));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public final Optional<AccountDTO> getTradeAccount() {
        return getTradeAccount(new LinkedHashSet<>(getAccounts().values()));
    }

    /**
     * Getter amountsLockedByPosition.
     *
     * @return amountsLockedByPosition
     */
    public final Map<Long, CurrencyAmountDTO> getAmountsLockedByPosition() {
        return amountsLockedByPosition;
    }

    /**
     * Returns the amount locked by currency.
     *
     * @param currency currency
     * @return amount
     */
    public final BigDecimal getAmountsLockedByCurrency(final Currency currency) {
        return amountsLockedByPosition.values()
                .stream()
                .filter(currencyAmount -> currencyAmount.getCurrency().equals(currency))
                .map(CurrencyAmountDTO::getValue)
                .reduce(ZERO, BigDecimal::add);
    }

    // =================================================================================================================
    // Related to tickers.

    /**
     * Return last received tickers.
     *
     * @return ticker
     */
    public final Map<CurrencyPair, Ticker> getLastTickers() {
        return lastTickers;
    }

    /**
     * Return the last ticker for a currency pair.
     *
     * @param currencyPair currency pair
     * @return last ticker received
     */
    public final Optional<Ticker> getLastTickerByCurrencyPair(final String currencyPair) {
        if (currencyPair == null) {
            return Optional.empty();
        } else {
            return getLastTickerByCurrencyPair(CurrencyPair.forValue(currencyPair));
        }
    }

    /**
     * Return the last ticker for a currency pair.
     *
     * @param currencyPair currency pair
     * @return last ticker received
     */
    public final Optional<Ticker> getLastTickerByCurrencyPair(final CurrencyPair currencyPair) {
        if (currencyPair == null) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(lastTickers.get(currencyPair));
        }
    }

    // =================================================================================================================
    // Related to orders.

    /**
     * Returns list of orders.
     *
     * @return orders
     */
    public final Map<String, OrderDTO> getOrders() {
        return orderRepository.findByOrderByTimestampAsc()
                .stream()
                .map(mapperService.getOrderMapper()::mapToOrderDTO)
                .collect(Collectors.toMap(OrderDTO::getOrderId, orderDTO -> orderDTO));
    }

    /**
     * Get an order by its id.
     *
     * @param orderId order id
     * @return order
     */
    public final Optional<OrderDTO> getOrderByOrderId(final String orderId) {
        return orderRepository.findByOrderId(orderId).map(mapperService.getOrderMapper()::mapToOrderDTO);
    }

    // =================================================================================================================
    // Related to trades.

    /**
     * Returns list of trades.
     *
     * @return trades
     */
    public final Map<String, TradeDTO> getTrades() {
        return tradeRepository.findByOrderByTimestampAsc()
                .stream()
                .map(mapperService.getTradeMapper()::mapToTradeDTO)
                .collect(Collectors.toMap(TradeDTO::getTradeId, tradeDTO -> tradeDTO));
    }

    /**
     * Get a trade by its id.
     *
     * @param tradeId trade id
     * @return trade
     */
    public final Optional<TradeDTO> getTradeByTradeId(final String tradeId) {
        return tradeRepository.findByTradeId(tradeId).map(mapperService.getTradeMapper()::mapToTradeDTO);
    }

    // =================================================================================================================
    // Related to positions.

    /**
     * Returns list of positions.
     *
     * @return positions
     */
    public final Map<Long, PositionDTO> getPositions() {
        return positionRepository.findByOrderById()
                .stream()
                .map(mapperService.getPositionMapper()::mapToPositionDTO)
                .collect(Collectors.toMap(PositionDTO::getId, positionDTO -> positionDTO));
    }

    /**
     * Get a position by its id.
     *
     * @param positionId position id
     * @return position
     */
    public final Optional<PositionDTO> getPositionByPositionId(final long positionId) {
        return positionRepository.findByPositionId(positionId).map(mapperService.getPositionMapper()::mapToPositionDTO);
    }

    /**
     * Returns gains of all positions.
     *
     * @return total gains
     */
    public final HashMap<Currency, GainDTO> getGains() {
        return positionService.getGains();
    }

    // =================================================================================================================
    // Methods related to creating of orders & positions.

    /**
     * Creates a buy market order.
     *
     * @param currencyPair currency pair
     * @param amount       amount
     * @return order result (order id or error)
     */
    public OrderCreationResultDTO createBuyMarketOrder(final CurrencyPair currencyPair,
                                                       final BigDecimal amount) {
        return tradeService.createBuyMarketOrder(strategyDTO, currencyPair, amount);
    }

    /**
     * Creates a sell market order.
     *
     * @param currencyPair currency pair
     * @param amount       amount
     * @return order result (order id or error)
     */
    public OrderCreationResultDTO createSellMarketOrder(final CurrencyPair currencyPair,
                                                        final BigDecimal amount) {
        return tradeService.createSellMarketOrder(strategyDTO, currencyPair, amount);
    }

    /**
     * Creates a buy limit order.
     *
     * @param currencyPair currency pair
     * @param amount       amount
     * @param limitPrice   the highest acceptable price
     * @return order result (order id or error)
     */
    public OrderCreationResultDTO createBuyLimitOrder(final CurrencyPair currencyPair,
                                                      final BigDecimal amount,
                                                      final BigDecimal limitPrice) {
        return tradeService.createBuyLimitOrder(strategyDTO, currencyPair, amount, limitPrice);
    }

    /**
     * Creates a sell limit order.
     *
     * @param currencyPair currency pair
     * @param amount       amount
     * @param limitPrice   the lowest acceptable price
     * @return order result (order id or error)
     */
    public OrderCreationResultDTO createSellLimitOrder(final CurrencyPair currencyPair,
                                                       final BigDecimal amount,
                                                       final BigDecimal limitPrice) {
        return tradeService.createSellLimitOrder(strategyDTO, currencyPair, amount, limitPrice);
    }

    /**
     * Creates a long position with its associated rules.
     * Long position is nothing but buying share.
     * If you are bullish (means you think that price of X share will rise) at that time you buy some amount of Share is called taking Long Position in share.
     *
     * @param currencyPair currency pair
     * @param amount       amount
     * @param rules        rules
     * @return position creation result
     */
    public PositionCreationResultDTO createLongPosition(final CurrencyPair currencyPair,
                                                        final BigDecimal amount,
                                                        final PositionRulesDTO rules) {
        return positionService.createLongPosition(strategyDTO, currencyPair, amount, rules);
    }

    /**
     * Creates a short position with its associated rules.
     * Short position is nothing but selling share.
     * If you are bearish (means you think that price of xyz share are going to fall) at that time you sell some amount of share is called taking Short Position in share.
     *
     * @param currencyPair currency pair
     * @param amount       amount
     * @param rules        rules
     * @return position creation result
     */
    public PositionCreationResultDTO createShortPosition(final CurrencyPair currencyPair,
                                                         final BigDecimal amount,
                                                         final PositionRulesDTO rules) {
        return positionService.createShortPosition(strategyDTO, currencyPair, amount, rules);
    }

    // =================================================================================================================
    // Methods that can be implemented by strategies.

    @Override
    public void onAccountUpdate(final AccountDTO account) {

    }

    @Override
    public void onTickerUpdate(final Ticker ticker) {

    }

    @Override
    public void onOrderUpdate(final OrderDTO order) {

    }

    @Override
    public void onTradeUpdate(final TradeDTO trade) {

    }

    @Override
    public void onPositionUpdate(final PositionDTO position) {

    }

    @Override
    public void onPositionStatusUpdate(final PositionDTO position) {

    }

    // =================================================================================================================
    // Related to canBuy & canSell methods.

    /**
     * Returns the cost of buying an amount of a currency pair.
     *
     * @param currencyPair currency pair
     * @param amount       amount
     * @return cost
     */
    public final Optional<CurrencyAmountDTO> getEstimatedBuyingCost(final CurrencyPair currencyPair, final BigDecimal amount) {
        /*
            symbol=ETH-BTC
            {
              "time": 1598626640265,
              "sequence": "1594421123246",
              "price": "0.034227",
              "size": "0.0200088",
              "bestBid": "0.034226",
              "bestBidSize": "6.3384368",
              "bestAsk": "0.034227",
              "bestAskSize": "18.6378851"
            }
            This means 1 Ether can be bought with 0.034227 Bitcoin.
         */

        // We get the last ticker from the last values received.
        final Ticker ticker = lastTickers.get(currencyPair);
        if (ticker == null) {
            // No ticker for this currency pair.
            return Optional.empty();
        } else {
            // Make the calculation.
            return Optional.of(CurrencyAmountDTO.builder()
                    .value(ticker.getLast().multiply(amount))
                    .currency(currencyPair.getQuoteCurrency())
                    .build());
        }
    }

    /**
     * Returns true if we have enough assets to buy.
     *
     * @param currencyPair currency pair
     * @param amount       amount
     * @return true if we there is enough assets to buy
     */
    public final boolean canBuy(final CurrencyPair currencyPair,
                                final BigDecimal amount) {
        final Optional<AccountDTO> tradeAccount = getTradeAccount(new LinkedHashSet<>(accounts.values()));
        return tradeAccount.filter(account -> canBuy(account, currencyPair, amount)).isPresent();
    }

    /**
     * Returns true if we have enough assets to buy.
     *
     * @param currencyPair        currency pair
     * @param amount              amount
     * @param minimumBalanceAfter minimum balance that should be left after buying
     * @return true if we there is enough assets to buy
     */
    public final boolean canBuy(final CurrencyPair currencyPair,
                                final BigDecimal amount,
                                final BigDecimal minimumBalanceAfter) {
        final Optional<AccountDTO> tradeAccount = getTradeAccount(new LinkedHashSet<>(accounts.values()));
        return tradeAccount.filter(account -> canBuy(account, currencyPair, amount, minimumBalanceAfter)).isPresent();
    }

    /**
     * Returns true if we have enough assets to buy.
     *
     * @param account      account
     * @param currencyPair currency pair
     * @param amount       amount
     * @return true if we there is enough assets to buy
     */
    public final boolean canBuy(final AccountDTO account,
                                final CurrencyPair currencyPair,
                                final BigDecimal amount) {
        return canBuy(account, currencyPair, amount, ZERO);
    }

    /**
     * Returns true if we have enough assets to buy and if minimumBalanceAfter is left on the account after.
     *
     * @param account             account
     * @param currencyPair        currency pair
     * @param amount              amount
     * @param minimumBalanceAfter minimum balance that should be left after buying
     * @return true if we there is enough assets to buy
     */
    public final boolean canBuy(final AccountDTO account,
                                final CurrencyPair currencyPair,
                                final BigDecimal amount,
                                final BigDecimal minimumBalanceAfter) {
        // We get the amount.
        final Optional<BalanceDTO> balance = account.getBalance(currencyPair.getQuoteCurrency());
        if (balance.isPresent()) {
            // We get the estimated cost of buying.
            final Optional<CurrencyAmountDTO> estimatedBuyingCost = getEstimatedBuyingCost(currencyPair, amount);

            // We calculate.
            // Balance in the account
            // Minus
            // Estimated cost
            // Must be superior to zero
            // If there is no way to calculate the price for the moment (no ticker).
            return estimatedBuyingCost.filter(currencyAmountDTO -> balance.get().getAvailable()
                    .subtract(currencyAmountDTO.getValue().add(minimumBalanceAfter).add(getAmountsLockedByCurrency(currencyAmountDTO.getCurrency())))
                    .compareTo(ZERO) > 0).isPresent() || estimatedBuyingCost.filter(currencyAmountDTO -> balance.get().getAvailable()
                    .subtract(currencyAmountDTO.getValue().add(minimumBalanceAfter).add(getAmountsLockedByCurrency(currencyAmountDTO.getCurrency())))
                    .compareTo(ZERO) == 0).isPresent();
        } else {
            // If the is no balance in this currency, we can't buy.
            return false;
        }
    }

    /**
     * Returns true if we have enough assets to sell.
     *
     * @param currency currency
     * @param amount   amount
     * @return true if we there is enough assets to sell
     */
    public final boolean canSell(final Currency currency,
                                 final BigDecimal amount) {
        final Optional<AccountDTO> tradeAccount = getTradeAccount(new LinkedHashSet<>(accounts.values()));
        return tradeAccount.filter(account -> canSell(account, currency, amount)).isPresent();
    }

    /**
     * Returns true if we have enough assets to sell.
     *
     * @param currency            currency
     * @param amount              amount
     * @param minimumBalanceAfter minimum balance that should be left after selling
     * @return true if we there is enough assets to sell
     */
    public final boolean canSell(final Currency currency,
                                 final BigDecimal amount,
                                 final BigDecimal minimumBalanceAfter) {
        final Optional<AccountDTO> tradeAccount = getTradeAccount(new LinkedHashSet<>(accounts.values()));
        return tradeAccount.filter(account -> canSell(account, currency, amount, minimumBalanceAfter)).isPresent();
    }

    /**
     * Returns true if we have enough assets to sell.
     *
     * @param account  account
     * @param currency currency pair
     * @param amount   amount
     * @return true if we there is enough assets to sell
     */
    public final boolean canSell(final AccountDTO account,
                                 final Currency currency,
                                 final BigDecimal amount) {
        return canSell(account, currency, amount, ZERO);
    }

    /**
     * Returns true if we have enough assets to sell and if minimumBalanceAfter is left on the account after.
     *
     * @param account             account
     * @param currency            currency
     * @param amount              amount
     * @param minimumBalanceAfter minimum balance that should be left after selling
     * @return true if we there is enough assets to sell
     */
    public final boolean canSell(final AccountDTO account,
                                 final Currency currency,
                                 final BigDecimal amount,
                                 final BigDecimal minimumBalanceAfter) {
        // We get the amount.
        final Optional<BalanceDTO> balance = account.getBalance(currency);
        // public int compareTo(BigDecimal bg) returns
        // 1 : if value of this BigDecimal is greater than that of BigDecimal object passed as parameter.
        // If the is no balance in this currency, we can't buy.
        return balance.filter(balanceDTO -> balanceDTO.getAvailable().subtract(amount).subtract(minimumBalanceAfter).subtract(getAmountsLockedByCurrency(currency)).compareTo(ZERO) > 0
                || balanceDTO.getAvailable().subtract(amount).subtract(minimumBalanceAfter).subtract(getAmountsLockedByCurrency(currency)).compareTo(ZERO) == 0).isPresent();
    }

}
