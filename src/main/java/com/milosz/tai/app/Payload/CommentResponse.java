package com.milosz.tai.app.Payload;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CommentResponse implements Comparable<CommentResponse> {

    private UserInComment user;
    private String content;

    @JsonFormat(pattern = "HH:mm dd-MM-yyyy")
    private Date date;

    public CommentResponse() {
    }

    public CommentResponse(UserInComment user, String content, Date date) {
        this.user = user;
        this.content = content;
        this.date = date;
    }

    public UserInComment getUser() {
        return user;
    }

    public void setUser(UserInComment user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(CommentResponse o) {
        return -getDate().compareTo(o.getDate());
    }
}
