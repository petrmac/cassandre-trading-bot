package tech.cassandre.trading.bot.test.configuration.mapper;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import tech.cassandre.trading.bot.util.mapper.*;

@TestConfiguration
public class TestMapperConfiguration {

    private CurrencyAmountMapper currencyAmountMapper;
    private OrderMapper orderMapper;
    private StrategyMapper strategyMapper;
    private TradeMapper tradeMapper;
    private UtilMapper utilMapper;
    private CurrencyPairMapper currencyPairMapper;
    private CurrencyMapper currencyMapper;
    private AccountMapperImpl accountMapper;
    private TickerMapperImpl tickerMapper;
    private PositionMapperImpl positionMapper;

    public TestMapperConfiguration() {
        currencyMapper = new CurrencyMapperImpl();
        currencyPairMapper = new CurrencyPairMapperImpl();
        utilMapper = new UtilMapperImpl();
        tradeMapper = new TradeMapperImpl(utilMapper, currencyMapper, currencyPairMapper);
        strategyMapper = new StrategyMapperImpl();
        orderMapper = new OrderMapperImpl(utilMapper, currencyMapper, currencyPairMapper, tradeMapper, strategyMapper);
        currencyAmountMapper = new CurrencyAmountMapperImpl();
        accountMapper = new AccountMapperImpl(currencyMapper);
        tickerMapper = new TickerMapperImpl(currencyPairMapper);
        positionMapper = new PositionMapperImpl(currencyMapper, currencyPairMapper, currencyAmountMapper, orderMapper,
                strategyMapper);
    }

    @Bean
    @Primary
    public MapperService getMapperService() {
        return new MapperService(new UtilMapperImpl(), currencyMapper, currencyPairMapper, currencyAmountMapper,
                strategyMapper, accountMapper, tickerMapper,
                orderMapper, tradeMapper, positionMapper);
    }

    @Bean
    @Primary
    public CurrencyAmountMapper getCurrencyAmountMapper() {
        return currencyAmountMapper;
    }

    @Bean
    @Primary
    public OrderMapper getOrderMapper() {
        return orderMapper;
    }

    @Bean
    @Primary
    public StrategyMapper getStrategyMapper() {
        return strategyMapper;
    }

    @Bean
    @Primary
    public TradeMapper getTradeMapper() {
        return tradeMapper;
    }

    @Bean
    @Primary
    public UtilMapper getUtilMapper() {
        return utilMapper;
    }

    @Bean
    @Primary
    public CurrencyPairMapper getCurrencyPairMapper() {
        return currencyPairMapper;
    }

    @Bean
    @Primary
    public CurrencyMapper getCurrencyMapper() {
        return currencyMapper;
    }

    @Bean
    @Primary
    public AccountMapperImpl getAccountMapper() {
        return accountMapper;
    }

    @Bean
    @Primary
    public TickerMapperImpl getTickerMapper() {
        return tickerMapper;
    }

    @Bean
    @Primary
    public PositionMapperImpl getPositionMapper() {
        return positionMapper;
    }
}
