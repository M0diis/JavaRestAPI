package me.m0dii.payload.response;

public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    /**
     * Returns the message of the response.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message of the response.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}