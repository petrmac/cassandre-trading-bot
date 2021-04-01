package tech.cassandre.trading.bot.util.base.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import tech.cassandre.trading.bot.util.mapper.MapperService;

import java.time.Duration;

/**
 * Base service.
 */
public abstract class BaseService {

    /** Bucket. */
    private final Bucket bucket;

    private final MapperService mapperService;

    /**
     * Construct a base service without rate limit.
     */
    public BaseService(final MapperService mapperService) {
        this.mapperService = mapperService;
        Bandwidth limit = Bandwidth.simple(1, Duration.ofMillis(1));
        bucket = Bucket4j.builder().addLimit(limit).build();

    }

    /**
     * Constructs a base service with a rate limit.
     *
     * @param rate rate in ms
     */
    public BaseService(final MapperService mapperService, final long rate) {
        this.mapperService = mapperService;
        Bandwidth limit = Bandwidth.simple(1, Duration.ofMillis(rate));
        bucket = Bucket4j.builder().addLimit(limit).build();
    }

    /**
     * Getter for bucket.
     *
     * @return bucket
     */
    public final Bucket getBucket() {
        return bucket;
    }

    public MapperService getMapperService() {
        return mapperService;
    }
}
