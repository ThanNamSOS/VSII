package com.vsii.utils;

import com.vsii.config.PropertiesConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class JMSUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JMSUtils.class);
    @Autowired
    private PropertiesConfig propertiesConfig;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        LOGGER.info("sendMessage: START at -" + LocalDateTime.now());
        LOGGER.info("sendMessage: fullPath = " + message);
        LOGGER.info("sendMessage: queueName = " + propertiesConfig.getTopicNameNew());
        this.kafkaTemplate.send(propertiesConfig.getTopicNameNew(), message);
    }

    public void sendMessageAdd(String message) {
        LOGGER.info("sendMessage: START at -" + LocalDateTime.now());
        LOGGER.info("sendMessage: fullPath = " + message);
        LOGGER.info("sendMessage: queueName = " + propertiesConfig.getTopicNameAdd());
        this.kafkaTemplate.send(propertiesConfig.getTopicNameAdd(), message);
    }

    public void sendMessageScanAdd(String message) {
        LOGGER.info("sendMessage: START at -" + LocalDateTime.now());
        LOGGER.info("sendMessage: fullPath = " + message);
        LOGGER.info("sendMessage: queueName = " + propertiesConfig.getTopicNameAdd());
        this.kafkaTemplate.send(propertiesConfig.getTopicNameScanADD(), message);
    }

    public void sendMessageScanNew(String message) {
        LOGGER.info("sendMessage: START at -" + LocalDateTime.now());
        LOGGER.info("sendMessage: fullPath = " + message);
        LOGGER.info("sendMessage: queueName = " + propertiesConfig.getTopicNameAdd());
        this.kafkaTemplate.send(propertiesConfig.getTopicNameScanNew(), message);
    }

    public void sendMessageImport(String message) {
        LOGGER.info("sendMessage: START at -" + LocalDateTime.now());
        LOGGER.info("sendMessage: fullPath = " + message);
        LOGGER.info("sendMessage: queueName = " + propertiesConfig.getTopicNameAdd());
        this.kafkaTemplate.send(propertiesConfig.getTopicNameImport(), message);
    }

    @Bean
    public NewTopic createTopicNew() {
        return TopicBuilder.name(propertiesConfig.getTopicNameNew()).build();
    }

    @Bean
    public NewTopic createTopicAdd() {
        return TopicBuilder.name(propertiesConfig.getTopicNameAdd()).build();
    }

    @Bean
    public NewTopic createTopicScanNew() {
        return TopicBuilder.name(propertiesConfig.getTopicNameScanNew()).build();
    }

    @Bean
    public NewTopic createTopicScanAdd() {
        return TopicBuilder.name(propertiesConfig.getTopicNameScanADD()).build();
    }

    @Bean
    public NewTopic createTopicImport() {
        return TopicBuilder.name(propertiesConfig.getTopicNameImport()).build();
    }
}
