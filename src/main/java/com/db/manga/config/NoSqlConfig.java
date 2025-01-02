package com.db.manga.config;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Profile("nosql")
@EnableMongoRepositories(
        basePackages = "com.db.manga.dao.nosql"
)
public class NoSqlConfig extends AbstractMongoClientConfiguration {

    @Profile("nosql")
    @Override
    protected String getDatabaseName() {
        return "BazyDanych";
    }

    @Profile("nosql")
    @Override
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

}
