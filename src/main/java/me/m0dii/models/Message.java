package me.m0dii.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "messages")
public class Message
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;
    
    private String senderEmail;
    
    private String receiverEmail;
    
    private Long timestamp;
    
    public Message()
    {
    
    }
    
    public Message(String senderEmail, String receiverEmail, String content)
    {
        this.content = content;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        
        this.timestamp = System.currentTimeMillis();
    }
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public String getSenderEmail()
    {
        return senderEmail;
    }
    
    public void setSenderEmail(String senderEmail)
    {
        this.senderEmail = senderEmail;
    }
    
    public String getReceiverEmail()
    {
        return receiverEmail;
    }
    
    public void setReceiverEmail(String receiverEmail)
    {
        this.receiverEmail = receiverEmail;
    }
    
    public Long getTimestamp()
    {
        return timestamp;
    }
    
    public Date getDate()
    {
        return new Date(timestamp);
    }
    
    public void setTimestamp(Long timestamp)
    {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString()
    {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", senderEmail='" + senderEmail + '\'' +
                ", receiverEmail='" + receiverEmail + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
