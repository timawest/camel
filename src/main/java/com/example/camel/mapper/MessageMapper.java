package com.example.camel.mapper;

import com.example.camel.DTO.MessagDTO;
import com.example.camel.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageMapper {
    public Message toMessage(MessagDTO messagDTO){
        return new Message(messagDTO.getNumber(), messagDTO.getFirstname()+" "+messagDTO.getLastname()+" "+messagDTO.getMiddlename());
    }
}
