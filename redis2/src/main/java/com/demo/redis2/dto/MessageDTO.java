package com.demo.redis2.dto;

public class MessageDTO {
    private String data;
    private String author;

    public MessageDTO() {
    }

    public MessageDTO(String data, String author) {
        this.data = data;
        this.author = author;
    }

    public String getData() {
        return data;
    }

    public MessageDTO setData(String data) {
        this.data = data;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public MessageDTO setAuthor(String author) {
        this.author = author;
        return this;
    }

    @Override
    public String toString() {
        return "Message{" +
                "data='" + data + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
