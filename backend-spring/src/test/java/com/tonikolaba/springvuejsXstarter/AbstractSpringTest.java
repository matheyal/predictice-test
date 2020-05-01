package com.tonikolaba.springvuejsXstarter;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
public abstract class AbstractSpringTest {

    @Container
    public static ElasticsearchContainer elasticsearch;
    static {
        elasticsearch = new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:7.5.2");
        elasticsearch.start();
        String httpHostAddress = elasticsearch.getHttpHostAddress();
        System.setProperty("elasticsearch.clusterNodes", httpHostAddress);
    }
}
