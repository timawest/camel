package com.example.camel.service;

import com.example.camel.DTO.MessagDTO;
import com.example.camel.mapper.MessageMapper;
import com.example.camel.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import com.example.camel.model.Message;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageService {
   private final MessageRepository messageRepository;
   private final MessageMapper messageMapper;

    public Message save(MessagDTO messagDTO) {
        Message message = messageMapper.toMessage(messagDTO);
        return messageRepository.save(message);
    }
}
