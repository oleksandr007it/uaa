package com.idevhub.tapas.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

    private final String BOOT_STRAP_KEY = "bootstrap.servers";
    private String bootStrapServers = "localhost:9092";
    private Map<String, String> consumer = new HashMap<>();

    private Map<String, String> producer = new HashMap<>();

    public String getBootStrapServers() {
        return bootStrapServers;
    }

    public void setBootStrapServers(String bootStrapServers) {
        this.bootStrapServers = bootStrapServers;
    }

    public Map<String, Object> getConsumerProps() {
        Map<String, Object> properties = new HashMap<>(this.consumer);
        if (!properties.containsKey(BOOT_STRAP_KEY)) {
            properties.put(BOOT_STRAP_KEY, this.bootStrapServers);
        }
        return properties;
    }

    public void setConsumer(Map<String, String> consumer) {
        this.consumer = consumer;
    }

    public Map<String, Object> getProducerProps() {
        Map<String, Object> properties = new HashMap<>(this.producer);
        if (!properties.containsKey(BOOT_STRAP_KEY)) {
            properties.put(BOOT_STRAP_KEY, this.bootStrapServers);
        }
        return properties;
    }

    public void setProducer(Map<String, String> producer) {
        this.producer = producer;
    }
}
