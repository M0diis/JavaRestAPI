package me.m0dii.payload.response;

import me.m0dii.models.User;

public class UserStatResponse {
    private User user;

    private int messageCount;

    private String firstMessageDate;

    private String lastMessageDate;

    private double averageMessageLength;

    private String lastMessageContent;

    public UserStatResponse(User user, int messageCount, String firstMessage, String lastMessage, double averageMessageLength,
                            String lastMessageContent) {
        this.user = user;
        this.messageCount = messageCount;
        this.firstMessageDate = firstMessage;
        this.lastMessageDate = lastMessage;
        this.averageMessageLength = averageMessageLength;
        this.lastMessageContent = lastMessageContent;
    }

    /**
     * Returns the user.
     *
     * @return the user
     * @see User
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the message count.
     *
     * @return the message count
     */
    public int getMessageCount() {
        return messageCount;
    }

    /**
     * Returns the first message date as string in "yyyy-MM-dd HH:mm:ss" format.
     *
     * @return date as string
     */
    public String getFirstMessageDate() {
        return firstMessageDate;
    }

    /**
     * Returns the last message date as string in "yyyy-MM-dd HH:mm:ss" format.
     *
     * @return date as string
     */
    public String getLastMessageDate() {
        return lastMessageDate;
    }

    /**
     * Returns the average message length.
     *
     * @return the average length
     */
    public double getAverageMessageLength() {
        return averageMessageLength;
    }

    /**
     * Returns the last message content.
     *
     * @return the message content
     */
    public String getLastMessageContent() {
        return lastMessageContent;
    }

    @Override
    public String toString() {
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
