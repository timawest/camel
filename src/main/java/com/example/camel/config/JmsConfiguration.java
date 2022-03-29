package com.example.camel.config;

import java.lang.String;

import org.apache.camel.component.jms.JmsComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JmsConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(JmsConfiguration.class);
    private String host = "localhost";
    private int port = 1414;
    private String queueManager = "QM1";
    private String channel = "DEV.APP.SVRCONN";
    private String username = "admin";
    private String password = "passw0rd";

    @Bean
    public JmsComponent mq() {
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(mqQueueConnectionFactory());
        return jmsComponent;
    }

    @Bean
    public MQQueueConnectionFactory mqQueueConnectionFactory() {
        MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
        mqQueueConnectionFactory.setHostName(host);
        try {
            mqQueueConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            mqQueueConnectionFactory.setChannel(channel);
            mqQueueConnectionFactory.setPort(port);
            mqQueueConnectionFactory.setQueueManager(queueManager);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return mqQueueConnectionFactory;
    }

    @Bean
    public UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter(
            MQQueueConnectionFactory mqQueueConnectionFactory) {
        UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter =
                new UserCredentialsConnectionFactoryAdapter();
        userCredentialsConnectionFactoryAdapter.setUsername(username);
        userCredentialsConnectionFactoryAdapter.setPassword(password);
        userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(mqQueueConnectionFactory);
        return userCredentialsConnectionFactoryAdapter;
    }

    @Bean
    @Primary
    public CachingConnectionFactory cachingConnectionFactory(
            UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setTargetConnectionFactory(userCredentialsConnectionFactoryAdapter);
        cachingConnectionFactory.setSessionCacheSize(500);
        cachingConnectionFactory.setReconnectOnException(true);
        return cachingConnectionFactory;
    }
}
