package tech.cassandre.trading.bot.util.base.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;
import tech.cassandre.trading.bot.util.mapper.*;

import java.util.Optional;

import static reactor.core.publisher.FluxSink.OverflowStrategy.LATEST;

/**
 * Base flux.
 *
 * @param <T> flux
 */
public abstract class BaseFlux<T> {

    /** Logger. */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    protected final MapperService mapperService;

    /** Flux. */
    protected final Flux<T> flux;

    /** Flux sink. */
    protected FluxSink<T> fluxSink;

    /**
     * Constructor.
     */
    public BaseFlux(MapperService mapperService) {
        this.mapperService = mapperService;
        Flux<T> fluxTemp = Flux.create(newFluxSink -> this.fluxSink = newFluxSink, getOverflowStrategy());
        flux = fluxTemp.publishOn(Schedulers.boundedElastic());
    }

    /**
     * Set the default overflow strategy - override to change it.
     *
     * @return overflow strategy
     */
    @SuppressWarnings("SameReturnValue")
    protected FluxSink.OverflowStrategy getOverflowStrategy() {
        return LATEST;
    }


    /**
     * Implements this method to backup each update.
     *
     * @param newValue new value
     * @return the value saved
     */
    protected abstract Optional<T> saveValue(T newValue);

    /**
     * Emit a new value.
     *
     * @param newValue new value
     */
    public void emitValue(final T newValue) {
        saveValue(newValue)
                .ifPresent(t -> {
                    logger.debug("{} flux emits a new value : {}", this.getClass().getName(), t);
                    fluxSink.next(t);
                });
    }

    /**
     * Getter for flux.
     *
     * @return flux
     */
    public Flux<T> getFlux() {
        return flux;
    }

}
