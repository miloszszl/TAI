package com.milosz.tai.app.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "movie_comment")
public class MovieComment extends Comment {

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Movie movie;

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
