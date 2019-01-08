package com.milosz.tai.app.Payload;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

public class SignUpRequest {
    @NotBlank
    @Size(min = 4, max = 25)
    private String nickname;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    MultipartFile image;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
