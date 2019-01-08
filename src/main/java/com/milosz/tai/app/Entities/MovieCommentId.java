package com.milosz.tai.app.Entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

@Embeddable
public class MovieCommentId implements Serializable {

    @NotNull
    @Column(name = "movie_id")
    private Long movieId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

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
