package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createNewMessage(Message newMessage){
        //  message_text is not blank, is under 255 characters, Refers to existing user
        if(newMessage.getMessageText().length() >= 255 || newMessage.getMessageText().isBlank()
        || accountRepository.findById(newMessage.getPostedBy()).isEmpty()){
            return null;
        }
        return messageRepository.save(newMessage);
    }

    public List<Message> getAllMessages(){
        return (List<Message>)messageRepository.findAll();
    }

    public Message getMessageById(int messageId){
        return messageRepository.findById(messageId).orElse(null);
    }

    public Integer deleteMessageById(int messageId){
        // Check if message exists
        Message exist = getMessageById(messageId);
        if (exist == null){
            return null;
        }else{
            messageRepository.deleteById(messageId);
            return 1;
        }
    }

    public Integer patchMessageById(int messageId, String messageText){
        Message updatedMessage = messageRepository.findById(messageId).orElse(null);
        //  message_text is not blank, is under 255 characters, Refers to existing message
        if(messageText.length() >= 255 || messageText.isBlank()
        || updatedMessage == null){
            return null;
        }
        updatedMessage.setMessageText(messageText);
        messageRepository.save(updatedMessage);
        return 1;
    }

    public List<Message> getAllMessagesByAccountId(int accountId){
        return messageRepository.getMessagesByAccountId(accountId);
    }

}
