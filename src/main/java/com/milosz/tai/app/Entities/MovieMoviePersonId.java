package com.milosz.tai.app.Entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

@Embeddable
public class MovieMoviePersonId implements Serializable {

    @NotNull
    @Column(name = "movie_id")
    private Long movieId;

    @NotNull
    @Column(name = "movie_person_id")
    private Long moviePersonId;

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getMoviePersonId() {
        return moviePersonId;
    }

    public void setMoviePersonId(Long moviePersonId) {
        this.moviePersonId = moviePersonId;
    }
}
