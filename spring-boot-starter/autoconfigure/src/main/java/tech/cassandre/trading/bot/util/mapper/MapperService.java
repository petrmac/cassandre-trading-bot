package tech.cassandre.trading.bot.util.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MapperService {

    /** Logger. */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final UtilMapper utilMapper;
    private final CurrencyMapper currencyMapper;
    private final CurrencyPairMapper currencyPairMapper;
    private final CurrencyAmountMapper currencyAmountMapper;
    private final StrategyMapper strategyMapper;
    private final AccountMapper accountMapper;
    private final TickerMapper tickerMapper;
    private final OrderMapper orderMapper;
    private final TradeMapper tradeMapper;
    private final PositionMapper positionMapper;

    public MapperService(UtilMapper utilMapper, CurrencyMapper currencyMapper, CurrencyPairMapper currencyPairMapper,
                CurrencyAmountMapper currencyAmountMapper, StrategyMapper strategyMapper, AccountMapper accountMapper,
                TickerMapper tickerMapper, OrderMapper orderMapper, TradeMapper tradeMapper,
                PositionMapper positionMapper) {
        this.utilMapper = utilMapper;
        this.currencyMapper = currencyMapper;
        this.currencyPairMapper = currencyPairMapper;
        this.currencyAmountMapper = currencyAmountMapper;
        this.strategyMapper = strategyMapper;
        this.accountMapper = accountMapper;
        this.tickerMapper = tickerMapper;
        this.orderMapper = orderMapper;
        this.tradeMapper = tradeMapper;
        this.positionMapper = positionMapper;
    }

    public UtilMapper getUtilMapper() {
        return utilMapper;
    }

    public CurrencyMapper getCurrencyMapper() {
        return currencyMapper;
    }

    public CurrencyPairMapper getCurrencyPairMapper() {
        return currencyPairMapper;
    }

    public CurrencyAmountMapper getCurrencyAmountMapper() {
        return currencyAmountMapper;
    }

    public StrategyMapper getStrategyMapper() {
        return strategyMapper;
    }

    public AccountMapper getAccountMapper() {
        return accountMapper;
    }

    public TickerMapper getTickerMapper() {
        return tickerMapper;
    }

    public OrderMapper getOrderMapper() {
        return orderMapper;
    }

    public TradeMapper getTradeMapper() {
        return tradeMapper;
    }

    public PositionMapper getPositionMapper() {
        return positionMapper;
    }
}
