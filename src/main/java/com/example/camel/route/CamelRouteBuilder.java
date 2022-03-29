package com.example.camel.route;

import com.example.camel.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CamelRouteBuilder extends RouteBuilder {
   private final ObjectMapper objectMapper;
    private final MessageService messageService;
    protected boolean autoStartup;
    private String schedulerProcessName;
    private String schedulerProcessCron;

    public CamelRouteBuilder(CamelContext context, ObjectMapper objectMapper, MessageService messageService, @Value("${source.ibmmq.enabled}") boolean autoStartup,
                             @Value("${source.ibmmq.camel.scheduler.upload.name}")  String schedulerProcessName, @Value("${source.ibmmq.camel.scheduler.upload.cron}")  String schedulerProcessCron) {
        super(context);
        this.objectMapper = objectMapper;
        this.messageService = messageService;
        this.autoStartup=autoStartup;
        this.schedulerProcessName=schedulerProcessName;
        this.schedulerProcessCron=schedulerProcessCron;

    }

    public void configure() throws Exception {
//        onException (Exception.class)
//                .handled (true)
//                .process(exchange -> {
//                   log.info("Не валидные данные");
//                   log.info(exchange.getIn().getBody().toString());
//                });
        from("file:target/import/")
                .routeId("generate-route")
                .autoStartup(autoStartup)
                .process(exchange -> {
                    log.info("Получение данных");
                    log.info("########"+exchange.getIn().getBody());
                    String json = exchange.getIn().getBody(String.class);
                       log.info(json);
////                    InjectableValues inject = new InjectableValues.Std().addValue(String.class, "fromJson");
//////                    MessagDTO messagDTO = objectMapper.reader(inject).forType(MessagDTO.class).readValue(json);
////                    MessagDTO messagDTO = objectMapper.readValue(json, MessagDTO.class);
////                    log.info(messagDTO.toString());
////                    messageService.save(messagDTO);
//                    log.info("Данные сохранены");
                })
                .unmarshal(new String())
                .multicast().parallelProcessing()
                .to("direct:camelroute")
                .to("spring-batch:importUserJob");
    }
}