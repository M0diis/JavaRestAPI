package me.m0dii.payload.response;

import me.m0dii.models.User;

import java.util.Date;

public class UserStatResponse
{
    private User user;
    
    private int messageCount;
    
    private Date firstMessageDate;
    
    private Date lastMessageDate;
    
    private double averageMessageLength;
    
    private String lastMessageContent;
    
    public UserStatResponse(User user, int messageCount, Date firstMessage, Date lastMessage, double averageMessageLength,
                            String lastMessageContent)
    {
        this.user = user;
        this.messageCount = messageCount;
        this.firstMessageDate = firstMessage;
        this.lastMessageDate = lastMessage;
        this.averageMessageLength = averageMessageLength;
        this.lastMessageContent = lastMessageContent;
    }
    
    public User getUser()
    {
        return user;
    }
    public int getMessageCount()
    {
        return messageCount;
    }
    
    public Date getFirstMessageDate()
    {
        return firstMessageDate;
    }
    
    public Date getLastMessageDate()
    {
        return lastMessageDate;
    }
    
    public double getAverageMessageLength()
    {
        return averageMessageLength;
    }
    
    public String getLastMessageContent()
    {
        return lastMessageContent;
    }
    
    @Override
    public String toString()
    {
        return "UserStatResponse{" +
                "user=" + user +
                ", messageCount=" + messageCount +
                ", firstMessageDate=" + firstMessageDate +
                ", lastMessageDate=" + lastMessageDate +
                ", averageMessageLength=" + averageMessageLength +
                ", lastMessageContent='" + lastMessageContent + '\'' +
                '}';
    }
}
