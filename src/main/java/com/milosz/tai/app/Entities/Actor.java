package com.milosz.tai.app.Entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.milosz.tai.app.Entities.Parameters.ConstraintValues.*;

@Entity
@Table(name = "actor")
public class Actor {

    @EmbeddedId
    private MovieMoviePersonId id = new MovieMoviePersonId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("moviePersonId")
    private MoviePerson moviePerson;

    @NotNull
    @Size(max = MOVIE_MOVIE_PERSON_ROLE_NAME_LENGTH)
    private String roleName;

    public MovieMoviePersonId getId() {
        return id;
    }

    public void setId(MovieMoviePersonId id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public MoviePerson getMoviePerson() {
        return moviePerson;
    }

    public void setMoviePerson(MoviePerson moviePerson) {
        this.moviePerson = moviePerson;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
