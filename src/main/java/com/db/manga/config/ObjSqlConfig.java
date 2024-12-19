package com.db.manga.config;


import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.db.manga.dao.sql")
@EnableTransactionManagement
@EntityScan(basePackages = "com.db.manga.entity.sql")
public class ObjSqlConfig {

    @Bean
    public DataSource dataSource(){
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/dbmangav2");
        dataSource.setUsername("postgres");
        dataSource.setPassword("...");

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource){
        return builder
                .dataSource(dataSource)
                .packages("com.db.manga.entity.sql")
                .persistenceUnit("manga")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
