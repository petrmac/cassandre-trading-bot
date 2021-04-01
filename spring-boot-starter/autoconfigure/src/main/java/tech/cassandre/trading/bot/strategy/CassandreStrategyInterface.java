package tech.cassandre.trading.bot.strategy;

import tech.cassandre.trading.bot.dto.market.Ticker;
import tech.cassandre.trading.bot.dto.position.PositionDTO;
import tech.cassandre.trading.bot.dto.strategy.StrategyDTO;
import tech.cassandre.trading.bot.dto.trade.OrderDTO;
import tech.cassandre.trading.bot.dto.trade.TradeDTO;
import tech.cassandre.trading.bot.dto.user.AccountDTO;
import tech.cassandre.trading.bot.dto.util.CurrencyPair;
import tech.cassandre.trading.bot.repository.OrderRepository;
import tech.cassandre.trading.bot.repository.PositionRepository;
import tech.cassandre.trading.bot.repository.TradeRepository;
import tech.cassandre.trading.bot.service.PositionService;
import tech.cassandre.trading.bot.service.TradeService;
import tech.cassandre.trading.bot.util.mapper.MapperService;

import java.util.Optional;
import java.util.Set;

/**
 * Cassandre strategy interface.
 * This allows the framework to communicate with the strategy.
 */
@SuppressWarnings("unused")
public interface CassandreStrategyInterface {

    /**
     * Setter strategyDTO.
     *
     * @param newStrategyDTO strategy DTO.
     */
    void setStrategyDTO(StrategyDTO newStrategyDTO);

    /**
     * Setter order repository.
     *
     * @param newOrderRepository order repository
     */
    void setOrderRepository(OrderRepository newOrderRepository);

    /**
     * Setter trade repository.
     *
     * @param newTradeRepository trade repository.
     */
    void setTradeRepository(TradeRepository newTradeRepository);

    /**
     * Setter positionRepository.
     *
     * @param newPositionRepository the positionRepository to set
     */
    void setPositionRepository(PositionRepository newPositionRepository);

    /**
     * Setter for tradeService.
     *
     * @param newTradeService the tradeService to set
     */
    void setTradeService(TradeService newTradeService);

    /**
     * Stter for the mapper service
     * @param mapperService mapper service
     */
    void setMapperService(MapperService mapperService);

    /**
     * Setter for positionService.
     *
     * @param newPositionService position service
     */
    void setPositionService(PositionService newPositionService);

    /**
     * Method called by streams at every account update.
     *
     * @param account account
     */
    void accountUpdate(AccountDTO account);

    /**
     * Method called by streams at every ticker update.
     *
     * @param ticker ticker
     */
    void tickerUpdate(Ticker ticker);

    /**
     * Method called by streams on every order update.
     *
     * @param order order
     */
    void orderUpdate(OrderDTO order);

    /**
     * Method called by streams on every trade update.
     *
     * @param trade trade
     */
    void tradeUpdate(TradeDTO trade);

    /**
     * Method called by streams on every position update.
     *
     * @param position trade
     */
    void positionUpdate(PositionDTO position);

    /**
     * Implements this method to tell the bot which currency pairs your strategy will receive.
     *
     * @return the list of currency pairs tickers your want to receive
     */
    Set<CurrencyPair> getRequestedCurrencyPairs();

    /**
     * Implements this method to tell the bot which account from the accounts you own is the trading one.
     *
     * @param accounts all your accounts
     * @return trading account
     */
    Optional<AccountDTO> getTradeAccount(Set<AccountDTO> accounts);

    /**
     * Returns your trading account.
     *
     * @return trading account
     */
    Optional<AccountDTO> getTradeAccount();

    /**
     * Method triggered at every account update.
     *
     * @param account account
     */
    void onAccountUpdate(AccountDTO account);

    /**
     * Method triggered at every ticker update.
     *
     * @param ticker ticker
     */
    void onTickerUpdate(Ticker ticker);

    /**
     * Method triggered on every order update.
     *
     * @param order order
     */
    void onOrderUpdate(OrderDTO order);

    /**
     * Method triggered on every trade update.
     *
     * @param trade trade
     */
    void onTradeUpdate(TradeDTO trade);

    /**
     * Method triggered on every position update.
     *
     * @param position position
     */
    void onPositionUpdate(PositionDTO position);

    /**
     * Method triggered on every position status update.
     *
     * @param position position
     */
    void onPositionStatusUpdate(PositionDTO position);

}
