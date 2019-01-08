package com.milosz.tai.app.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.milosz.tai.app.Entities.Parameters.ConstraintValues.*;

@Entity
@Table(name = "movie_person")
public class MoviePerson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = MOVIE_PERSON_NAME_LENGTH)
    private String name;

    @NotNull
    @Size(max = MOVIE_PERSON_SURNAME_LENGTH)
    private String surname;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthdate;

    @NotNull
    @Size(max = MOVIE_PERSON_BIRTH_PLACE_LENGTH)
    private String birthPlace;

    @Min(value = 0)
    @Max(value = 350)
    private Integer height;     //cm

    @Lob
    private byte[] image;

    @OneToMany(mappedBy = "moviePerson",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MoviePersonComment> moviePersonCommentList;

    @OneToMany(
            mappedBy = "moviePerson",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Actor> actorMovieList;

    @Digits(integer = 2, fraction = 1)
    private BigDecimal rate;

    @ManyToMany(mappedBy = "directorList")
    private List<Movie> directorMovieList;

    @ManyToMany(mappedBy = "scenaristList")
    private List<Movie> scenaristMovieList;

    @JsonIgnore
    @OneToMany(mappedBy = "moviePerson",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MoviePersonRate> moviePersonRateList;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<MoviePersonComment> getMoviePersonCommentList() {
        return moviePersonCommentList;
    }

    public void setMoviePersonCommentList(List<MoviePersonComment> moviePersonCommentList) {
        this.moviePersonCommentList = moviePersonCommentList;
    }

    public List<Actor> getActorMovieList() {
        return actorMovieList;
    }

    public void setActorMovieList(List<Actor> actorMovieList) {
        this.actorMovieList = actorMovieList;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public List<Movie> getDirectorMovieList() {
        return directorMovieList;
    }

    public void setDirectorMovieList(List<Movie> directorMovieList) {
        this.directorMovieList = directorMovieList;
    }

    public List<Movie> getScenaristMovieList() {
        return scenaristMovieList;
    }

    public void setScenaristMovieList(List<Movie> scenaristMovieList) {
        this.scenaristMovieList = scenaristMovieList;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<MoviePersonRate> getMoviePersonRateList() {
        return moviePersonRateList;
    }

    public void setMoviePersonRateList(List<MoviePersonRate> moviePersonRateList) {
        this.moviePersonRateList = moviePersonRateList;
    }
}
