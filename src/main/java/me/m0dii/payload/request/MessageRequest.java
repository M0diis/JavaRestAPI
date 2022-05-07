package me.m0dii.payload.request;

import javax.validation.constraints.NotBlank;

public class MessageRequest
{
    @NotBlank
    private String content;
    
    @NotBlank
    private String receiverEmail;
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public String getReceiverEmail()
    {
        return receiverEmail;
    }
    
    public void setReceiverEmail(String receiverEmail)
    {
        this.receiverEmail = receiverEmail;
    }
    
    @Override
    public String toString()
    {
        return "MessageRequest [content=" + content + ", receiverEmail=" + receiverEmail + "]";
    }
}
