package com.milosz.tai.app.Entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

@Embeddable
public class MoviePersonCommentId implements Serializable {

    @NotNull
    @Column(name = "movie_person_id")
    private Long moviePersonId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

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
