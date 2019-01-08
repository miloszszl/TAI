package com.milosz.tai.app.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

import static com.milosz.tai.app.Entities.Parameters.ConstraintValues.MOVIE_TYPE_NAME_LENGTH;

@Entity
@Table(name = "movie_type")
public class MovieType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = MOVIE_TYPE_NAME_LENGTH)
    @Column(unique = true)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "movieTypeList")
    private List<Movie> movieList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
