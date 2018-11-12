/*
 * Created by zhangzxiang91@gmail.com on 2017/10/10.
 */
package com.anniu.ordercall.core.mongo;

import com.anniu.ordercall.repository.mongo.MongoScan;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author zhangzxiang91@gmail.com
 * @date 2017/10/10.
 */
@Configuration
@EnableMongoRepositories(basePackageClasses = MongoScan.class)
public class MongoConfig {

    @Value("${mongodb.ordercall.uri}")
    private String ordercallUri;

    @Bean({"mongoTemplate", "mongoOperations"})
    public MongoTemplate mongoTemplate() {
        int timeOut = 30000;
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder()//.writeConcern(WriteConcern.W1.withJournal(true))
                .connectTimeout(timeOut)
                .socketTimeout(timeOut)
                .maxWaitTime(timeOut)
                .serverSelectionTimeout(timeOut).heartbeatConnectTimeout(timeOut).heartbeatSocketTimeout(timeOut)
                .connectionsPerHost(20);

        MongoClientURI uri = new MongoClientURI(ordercallUri, builder);
        return new MongoTemplate(new MongoClient(uri), uri.getDatabase());
    }

}
