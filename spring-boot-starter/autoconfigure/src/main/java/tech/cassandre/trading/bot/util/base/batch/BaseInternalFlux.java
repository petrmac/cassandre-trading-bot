package tech.cassandre.trading.bot.util.base.batch;

import tech.cassandre.trading.bot.util.mapper.*;

/**
 * Base external flux.
 *
 * @param <T> flux type
 */
public abstract class BaseInternalFlux<T> extends BaseFlux<T> {

    /**
     * Constructor.
     *
     * @param mapperService mapper service
     */
    public BaseInternalFlux(final MapperService mapperService) {
        super(mapperService);
    }
}
