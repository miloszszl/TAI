package com.milosz.tai.app.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.milosz.tai.app.Entities.Parameters.ConstraintValues.*;

@Entity
@Table(name = "user")
public class User extends DateAudit{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NaturalId
    @NotNull
    @Size(max = USER_NICKNAME_LENGTH)
    @Column(unique = true)
    private String nickname;

    @NotNull
    @Size(max = USER_EMAIL_LENGTH)
    @Column(unique = true)
    @Email
    private String email;

    @Lob
    private byte[] image;

    @JsonIgnore
    @NotNull
    @Size(max = USER_HASHED_PASSWORD_LENGTH)
    private String password;

    @JsonIgnore
    @NotNull
    @Size(max = USER_SALT_LENGTH)
    private String salt;

    @NotNull
    private boolean active;

    @NotNull
    private boolean banned;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MovieComment> movieCommentList;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MoviePersonComment> moviePersonCommentList;

    @JsonIgnore
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MovieRate> movieRateList;

    @JsonIgnore
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MoviePersonRate> moviePersonRateList;

    public User() {
    }

    public User(String nickname, String email, String password, Set<Role> roles, byte[] image) {
        this.nickname = nickname;
        this.email = email;
        this.image = image;
        this.password = password;
        this.salt = "123";
        this.active = true;
        this.banned = false;
        this.roles = roles;
        this.movieCommentList = null;
        this.moviePersonCommentList = null;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<MovieComment> getMovieCommentList() {
        return movieCommentList;
    }

    public void setMovieCommentList(List<MovieComment> movieCommentList) {
        this.movieCommentList = movieCommentList;
    }

    public List<MoviePersonComment> getMoviePersonCommentList() {
        return moviePersonCommentList;
    }

    public void setMoviePersonCommentList(List<MoviePersonComment> moviePersonCommentList) {
        this.moviePersonCommentList = moviePersonCommentList;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<MovieRate> getMovieRateList() {
        return movieRateList;
    }

    public void setMovieRateList(List<MovieRate> movieRateList) {
        this.movieRateList = movieRateList;
    }

    public List<MoviePersonRate> getMoviePersonRateList() {
        return moviePersonRateList;
    }

    public void setMoviePersonRateList(List<MoviePersonRate> moviePersonRateList) {
        this.moviePersonRateList = moviePersonRateList;
    }
}
