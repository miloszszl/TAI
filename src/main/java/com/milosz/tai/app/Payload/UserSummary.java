package com.milosz.tai.app.Payload;

public class UserSummary {
    private Long id;
    private String nickname;
    private String email;
    private Boolean isAdmin;

    public UserSummary(Long id, String nickname, String email, Boolean isAdmin) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
