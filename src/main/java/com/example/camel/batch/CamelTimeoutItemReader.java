package com.example.camel.batch;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.component.spring.batch.support.CamelItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CamelTimeoutItemReader<I> extends CamelItemReader<I> {
    private static final Logger LOG = LoggerFactory.getLogger(CamelTimeoutItemReader.class);

    private final ConsumerTemplate consumerTemplate;

    private final String endpointUri;


    public CamelTimeoutItemReader(ConsumerTemplate consumerTemplate, String endpointUri) {
        super(consumerTemplate, endpointUri);
        this.consumerTemplate = consumerTemplate;
        this.endpointUri = "direct:camelroute";
    }

    @Override
    @SuppressWarnings("unchecked")
    public I read() throws Exception {
        LOG.trace("reading new item...");
        I item = (I) consumerTemplate.receiveBody(endpointUri, 5000);
        LOG.trace("read item [{}]", item);
        return item;
    }
}
