package org.amoeba.example.postgrsql;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class PostgresConfig {

    @Bean
    @Qualifier("postgresDataSourceProperties")
    @ConfigurationProperties("spring.datasource.postgresql")
    public DataSourceProperties postgresDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Qualifier("postgresDataSource")
    public DataSource postgresDataSource() {
        return postgresDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    @Qualifier("postgresJdbcTemplate")
    public JdbcTemplate postgresJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
