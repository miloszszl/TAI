package com.milosz.tai.app.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "movie_person_comment")
public class MoviePersonComment extends Comment {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private MoviePerson moviePerson;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public MoviePerson getMoviePerson() {
        return moviePerson;
    }

    public void setMoviePerson(MoviePerson moviePerson) {
        this.moviePerson = moviePerson;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
