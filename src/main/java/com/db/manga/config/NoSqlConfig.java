package com.db.manga.config;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.db.manga.dao.nosql")
@EnableTransactionManagement
@EntityScan(basePackages = "com.db.manga.entity.nosql")
public class NoSqlConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "BazyDanych";
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

}
