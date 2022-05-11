package me.m0dii.controllers;

import me.m0dii.exceptions.UserNotFoundException;
import me.m0dii.models.Message;
import me.m0dii.models.User;
import me.m0dii.payload.response.UserStatResponse;
import me.m0dii.repository.MessageRepository;
import me.m0dii.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController
{
    private final UserRepository userRepository;
    
    private final MessageRepository messageRepository;
    
    public UserController(UserRepository userRepository, MessageRepository messageRepository)
    {
        this.userRepository = userRepository;
        
        this.messageRepository = messageRepository;
    }

    @GetMapping("/all")
    public List<User> getUsers()
    {
        return userRepository.findAll();
    }
    
    @GetMapping("/get/{id}")
    public User getUser(@PathVariable Long id)
    {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
    
    @PutMapping("/put/{id}")
    public User updateUser(@PathVariable Long id, User user)
    {
        return userRepository.findById(id).map(u ->
        {
            u.setEmail(user.getEmail());
            return userRepository.save(u);
        }).orElseGet(() ->
        {
            user.setId(id);
            return userRepository.save(user);
        });
    }
    
    @GetMapping("/statistics/{id}")
    public ResponseEntity<UserStatResponse> getStatistics(@PathVariable Long id)
    {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        
        int messageCount = messageRepository.countBySenderEmail(user.getEmail());
        
        return ResponseEntity.ok(new UserStatResponse(
                user,
                messageCount,
                getMessageDate(user, false),
                getMessageDate(user, true),
                getAverageMessageContentLength(user),
                getLastMessageContent(user))
        );
    }
    
    private double getAverageMessageContentLength(User user)
    {
        Optional<List<Message>> messages = messageRepository.findBySenderEmail(user.getEmail());
        
        if(messages.isEmpty())
        {
            return 0;
        }
    
        double messageCount = messages.get().size();
        
        double totalMessageLength = messages.get().stream().mapToInt(msg -> msg.getContent().length()).sum();
        
        return totalMessageLength / messageCount;
    }
    
    private String getLastMessageContent(User user)
    {
        Optional<Message> message = messageRepository.findFirstBySenderEmailOrderByTimestampDesc(user.getEmail());
        
        return message.isPresent() ? message.get().getContent() : "";
    }
    
    private String getMessageDate(User user, boolean last)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        if(last)
        {
            Date date = messageRepository.findFirstBySenderEmailOrderByTimestampDesc(user.getEmail())
                    .map(Message::getDate).orElse(null);
            
            return date != null ? sdf.format(date) : "";
        }
        
        Date date = messageRepository.findFirstBySenderEmailOrderByTimestampAsc(user.getEmail())
                .map(Message::getDate).orElse(null);
        
        return date != null ? sdf.format(date) : "";
    }
}


