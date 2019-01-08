package com.milosz.tai.app.Entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

@Embeddable
public class MoviePersonUserId implements Serializable {

    private static final long serialVersionUID = 2L;

    @NotNull
    @Column(name = "movie_person_id")
    private Long moviePersonId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    public MoviePersonUserId(){}

    public MoviePersonUserId(@NotNull Long moviePersonId, @NotNull Long userId) {
        this.moviePersonId = moviePersonId;
        this.userId = userId;
    }

    public Long getMoviePersonId() {
        return moviePersonId;
    }

    public void setMoviePersonId(Long moviePersonId) {
        this.moviePersonId = moviePersonId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
