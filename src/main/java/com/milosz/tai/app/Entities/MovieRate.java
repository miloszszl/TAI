package com.milosz.tai.app.Entities;

import javax.persistence.*;

@Entity
@Table(name = "movie_rates")
public class MovieRate extends Rate{

    @EmbeddedId
    private MovieUserId id = new MovieUserId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    public MovieUserId getId() {
        return id;
    }

    public void setId(MovieUserId id) {
        this.id = id;
    }

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
