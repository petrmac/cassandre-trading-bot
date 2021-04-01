package tech.cassandre.trading.bot.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import tech.cassandre.trading.bot.util.base.configuration.BaseConfiguration;
import tech.cassandre.trading.bot.util.mapper.MapperService;
import tech.cassandre.trading.bot.util.parameters.DatabaseParameters;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Database configures the database connection.
 */
@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
@EntityScan(basePackages = "tech.cassandre.trading.bot.domain")
@EnableJpaRepositories(basePackages = "tech.cassandre.trading.bot.repository")
@EnableConfigurationProperties(DatabaseParameters.class)
public class DatabaseAutoConfiguration extends BaseConfiguration {

    /** Precision. */
    public static final int PRECISION = 16;

    /** Scale. */
    public static final int SCALE = 8;

    /** Database parameters. */
    private final DatabaseParameters databaseParameters;

    /**
     * Constructor.
     *
     * @param newDatabaseParameters database parameters.
     */
    public DatabaseAutoConfiguration(final DatabaseParameters newDatabaseParameters,
                                     final MapperService mapperService) {
        super(mapperService);
        this.databaseParameters = newDatabaseParameters;
    }

    /**
     * Gives to Hiraki the configuration of the default datasource.
     *
     * @return datasource configuration
     */
    @Bean
    @Primary
    public DataSourceProperties dataSourceProperties() {
        DataSourceProperties p = new DataSourceProperties();
        p.setDriverClassName(databaseParameters.getDatasource().getDriverClassName());
        p.setUrl(databaseParameters.getDatasource().getUrl());
        p.setUsername(databaseParameters.getDatasource().getUsername());
        p.setPassword(databaseParameters.getDatasource().getPassword());
        return p;
    }

    /**
     * Makes ZonedDateTime compatible with auditing fields.
     *
     * @return DateTimeProvider
     */
    @Bean
    public DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(ZonedDateTime.now());
    }

}
