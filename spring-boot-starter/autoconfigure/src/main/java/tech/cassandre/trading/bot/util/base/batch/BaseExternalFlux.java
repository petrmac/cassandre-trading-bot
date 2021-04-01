package tech.cassandre.trading.bot.util.base.batch;

import tech.cassandre.trading.bot.util.mapper.*;

import java.util.Set;

/**
 * Base external flux.
 *
 * @param <T> flux type
 */
public abstract class BaseExternalFlux<T> extends BaseFlux<T> {

    /**
     * Constructor.
     *
     * @param mapperService the mapper service
    */
    public BaseExternalFlux(final MapperService mapperService) {
        super(mapperService);
    }

    /**
     * Implements this method to return all the new values. Those values will be sent to the strategy.
     *
     * @return list of new values
     */
    protected abstract Set<T> getNewValues();

    /**
     * Method executed when values must be updated (usually called by schedulers).
     */
    public final void update() {
        getNewValues().forEach(this::emitValue);
    }

}
