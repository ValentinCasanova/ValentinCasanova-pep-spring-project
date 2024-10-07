package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */


@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    // Leverage Constructor Based Dependancy Injection
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }
    
    // POST localhost:8080/register
    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account newAccount){
        Account savedAccount = accountService.register(newAccount);
        if(savedAccount == null){
            if((newAccount.getUsername().isBlank() || newAccount.getPassword().length() < 4)){
                return ResponseEntity.status(400).body(newAccount);
            }
            else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body(newAccount);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(savedAccount);
    }

    // POST localhost:8080/login
    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account loggedInAccount = accountService.login(account);
        return loggedInAccount == null ? ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(account) : ResponseEntity.status(HttpStatus.OK).body(loggedInAccount);
    }

    // POST localhost:8080/messages
    @PostMapping("messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message newMessage){
        Message createdMessage = messageService.createNewMessage(newMessage);
        return createdMessage == null ? ResponseEntity.status(400).body(newMessage) : ResponseEntity.status(HttpStatus.OK).body(createdMessage);
    }

    // GET localhost:8080/messages
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessages());
    }

    // GET localhost:8080/messages/{message_id}
    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessageById(message_id));
    }

    // DELETE localhost:8080/messages/{message_id}
    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int message_id){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.deleteMessageById(message_id));
    }

    // PATCH localhost:8080/messages/{message_id}
    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> patchMessageById(@PathVariable int message_id, @RequestBody Message newMessage){
        if(messageService.patchMessageById(message_id, newMessage.getMessageText()) != null){
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else{
            return ResponseEntity.status(400).build();
        }   
    }

    // GET localhost:8080/accounts/{account_id}/messages
    @GetMapping("accounts/{account_id}/messages") 
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable int account_id){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessagesByAccountId(account_id));
    }
}
