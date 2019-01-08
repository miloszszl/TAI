package com.milosz.tai.app.Payload;

public class UserInComment {
    private Long id;
    private String nickname;
    private byte [] image;

    public UserInComment(Long id, String nickname, byte[] image) {
        this.id = id;
        this.nickname = nickname;
        this.image = image;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
