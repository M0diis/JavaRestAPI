package me.m0dii.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;

    @NotBlank
    @Email
    private String senderEmail;

    @NotBlank
    @Email
    private String receiverEmail;

    private Long timestamp;

    public Message() {

    }

    public Message(String senderEmail, String receiverEmail, String content) {
        this.content = content;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;

        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Returns the message's id.
     *
     * @return message id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the message's id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the message content.
     *
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the message content.
     *
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the message sender's email address.
     *
     * @return senderEmail
     */
    public String getSenderEmail() {
        return senderEmail;
    }

    /**
     * Sets the message sender's email address.
     *
     * @param senderEmail the sender's email address
     */
    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    /**
     * Gets the message receivers's email address.
     *
     * @return receiverEmail
     */
    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    /**
     * Returns the timestamp when the message was sent.
     *
     * @return timestamp
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the message was sent.
     *
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the date when the message was sent.
     *
     * @return date from timestamp
     * @see Date
     */
    public Date getDate() {
        return new Date(timestamp);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", senderEmail='" + senderEmail + '\'' +
                ", receiverEmail='" + receiverEmail + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
