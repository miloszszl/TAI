package com.milosz.tai.app.Entities;

import javax.persistence.*;

@Entity
@Table(name="movie_person_rates")
public class MoviePersonRate extends Rate{

    @EmbeddedId
    private MoviePersonUserId id = new MoviePersonUserId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("moviePersonId")
    private MoviePerson moviePerson;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    public MoviePersonUserId getId() {
        return id;
    }

    public void setId(MoviePersonUserId id) {
        this.id = id;
    }

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
