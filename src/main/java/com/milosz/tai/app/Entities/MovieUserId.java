package com.milosz.tai.app.Entities;

import javax.persistence.Column;

import javax.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class MovieUserId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "user_id")
    private Long userId;

    public MovieUserId(){}

    public MovieUserId(Long movieId, Long userId) {
        this.movieId = movieId;
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
