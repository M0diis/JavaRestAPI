package me.m0dii.controllers;

import me.m0dii.advices.MessageNotFoundException;
import me.m0dii.advices.UserNotFoundException;
import me.m0dii.models.Message;
import me.m0dii.models.User;
import me.m0dii.payload.request.MessageRequest;
import me.m0dii.payload.response.MessageResponse;
import me.m0dii.repository.MessageRepository;
import me.m0dii.repository.UserRepository;
import me.m0dii.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/message/")
public class MessageController
{
    private final UserRepository userRepository;
    
    private final MessageRepository messageRepository;
    
    public MessageController(UserRepository userRepository, MessageRepository messageRepository)
    {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }
    
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('USER')")
    public Message getMessage(@PathVariable Long id)
    {
        return messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException(id));
    }
    
    @GetMapping("/get/all")
    @PreAuthorize("hasRole('USER')")
    public Iterable<Message> getMessages()
    {
        return messageRepository.findAll();
    }
    
    @GetMapping("/get/last")
    @PreAuthorize("hasRole('ADMIN')")
    public Message getLastSentMessage()
    {
        Message m = null;
    
        for(Message message : messageRepository.findAll())
        {
            if(m == null)
            {
                m = message;
            }
            
            if(message.getDate().after(m.getDate()))
            {
                m = message;
            }
        }
        
        return m;
    }
    
    @GetMapping("/get/averagelength")
    @PreAuthorize("hasRole('ADMIN')")
    public Double getAverageLength()
    {
        List<Message> all = messageRepository.findAll();
        
        return all.stream().mapToDouble(message -> message.getContent().length()).sum() / all.size();
    }
    
    @PostMapping("/send")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> authenticateUser(@Valid @RequestBody MessageRequest messageRequest)
    {
        Optional<User> receiver = userRepository.findByEmail(messageRequest.getReceiverEmail());
        
        if (receiver.isEmpty())
        {
            return ResponseEntity.ok(new MessageResponse("User not found."));
        }
        
        // Get current authorized receiver
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Get current authorized receiver info
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Optional<User> currentUser = userRepository.findByUsername(userDetails.getUsername());
        
        if (currentUser.isEmpty())
        {
            return ResponseEntity.ok(new MessageResponse("User not found."));
        }

        Message message = new Message(currentUser.get().getEmail(),
                receiver.get().getEmail(), messageRequest.getContent());
        
        messageRepository.save(message);
    
        return ResponseEntity.ok(new MessageResponse("Message successfully sent."));
    }
}
