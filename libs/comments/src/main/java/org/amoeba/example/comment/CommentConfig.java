package org.amoeba.example.comment;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "org.amoeba.example.comment.repository",
        entityManagerFactoryRef = "commentPostgresqlEntityManagerFactory",
        transactionManagerRef = "commentPostgresqlTransactionManager"

)
public class CommentConfig {

    @Bean
    @Qualifier("postgresJpaProperties")
    @Primary
    @ConfigurationProperties("spring.datasource.postgresql")
    public JpaProperties postgresJpaProperties(){
        return new JpaProperties();
    }

    @Bean(name = "commentPostgresqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean commentPostgresqlEntityManagerFactory(
            @Qualifier("postgresDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder,
            @Qualifier("postgresJpaProperties") JpaProperties jpaProperties) {

        return builder
                .dataSource(dataSource)
                .packages("org.amoeba.example.comment.repository")
                .persistenceUnit("postgresql")
                .properties(jpaProperties.getProperties())
                .build();
    }

    @Bean(name = "commentPostgresqlTransactionManager")
    public PlatformTransactionManager commentPostgresqlTransactionManager(
            @Qualifier("commentPostgresqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
