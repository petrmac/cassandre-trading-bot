package tech.cassandre.trading.bot.service.xchange;

import org.knowm.xchange.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.cassandre.trading.bot.dto.util.CurrencyPair;
import tech.cassandre.trading.bot.service.ExchangeService;
import tech.cassandre.trading.bot.util.base.service.BaseService;
import tech.cassandre.trading.bot.util.mapper.MapperService;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Exchange service - XChange implementation.
 */
public class ExchangeServiceXChangeImplementation extends BaseService implements ExchangeService {

    /** Logger. */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /** XChange service. */
    private final Exchange exchange;

    /**
     * Constructor.
     *
     * @param newExchange exchange
     */
    public ExchangeServiceXChangeImplementation(final Exchange newExchange, final MapperService mapperService) {
        super(mapperService);
        this.exchange = newExchange;
    }

    @Override
    public final Set<CurrencyPair> getAvailableCurrencyPairs() {
        logger.debug("ExchangeService - Retrieving available currency pairs");
        return exchange.getExchangeMetaData()
                .getCurrencyPairs()
                .keySet()
                .stream()
                .peek(cp -> logger.debug("ExchangeService - Adding currency pair {} ", cp))
                .map(getMapperService().getCurrencyPairMapper()::toDomainCurrencyPair)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

}
