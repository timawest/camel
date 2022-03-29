package com.example.camel.batch;

import com.example.camel.DTO.MessagDTO;
import com.example.camel.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageItemProcessor implements ItemProcessor<String, MessagDTO> {
    private static final Logger log = LoggerFactory.getLogger(MessageItemProcessor.class);
    private final ObjectMapper objectMapper;
    private final MessageService messageService;

    @Override
    public MessagDTO process(String messagDTO) throws Exception {
        MessagDTO messageDTO = objectMapper.readValue((String)messagDTO, MessagDTO.class);
        messageService.save(messageDTO);
        log.info("Данные сохранены "+messagDTO);
                return messageDTO;
    }
}
