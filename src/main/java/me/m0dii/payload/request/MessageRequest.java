package me.m0dii.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MessageRequest {
    @NotBlank
    @Size(min = 1, max = 100)
    private String content;

    @NotBlank
    @Email
    private String receiverEmail;

    /**
     * Get the message content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the message content.
     *
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get the receiver email.
     *
     * @return the receiverEmail
     */
    public String getReceiverEmail() {
        return receiverEmail;
    }

    /**
     * Set the receiver email.
     *
     * @param receiverEmail the email to set
     */
    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    @Override
    public String toString() {
        return "MessageRequest [content=" + content + ", receiverEmail=" + receiverEmail + "]";
    }
}
