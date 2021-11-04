package com.jimmy.vo;

public class MessageResponse<T> {
    private String message;
    private T body;

    public MessageResponse(String message, T body) {
        this.message = message;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "MessageResponse [message=" + message + ", body=" + body + "]";
    }

}
