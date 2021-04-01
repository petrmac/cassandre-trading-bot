package tech.cassandre.trading.bot.util.base.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.cassandre.trading.bot.util.mapper.*;

/**
 * Base configuration.
 */
public abstract class BaseConfiguration {

    /** Logger. */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    protected final MapperService mapperService;

    public BaseConfiguration(final MapperService mapperService) {
        this.mapperService = mapperService;
    }
}
