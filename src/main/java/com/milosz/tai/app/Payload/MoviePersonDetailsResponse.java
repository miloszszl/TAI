package com.milosz.tai.app.Payload;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MoviePersonDetailsResponse {
    private Long id;
    private String name;
    private String surname;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date birthdate;
    private String birthPlace;
    private Integer height;     //cm
    private byte[] image;
    private List<CommentResponse> moviePersonCommentList;
    private List<MovieResponse> actorMovieList;
    private BigDecimal rate;
    private List<MovieResponse> directorMovieList;
    private List<MovieResponse> scenaristMovieList;

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<CommentResponse> getMoviePersonCommentList() {
        return moviePersonCommentList;
    }

    public void setMoviePersonCommentList(List<CommentResponse> moviePersonCommentList) {
        this.moviePersonCommentList = moviePersonCommentList;
    }

    public List<MovieResponse> getActorMovieList() {
        return actorMovieList;
    }

    public void setActorMovieList(List<MovieResponse> actorMovieList) {
        this.actorMovieList = actorMovieList;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public List<MovieResponse> getDirectorMovieList() {
        return directorMovieList;
    }

    public void setDirectorMovieList(List<MovieResponse> directorMovieList) {
        this.directorMovieList = directorMovieList;
    }

    public List<MovieResponse> getScenaristMovieList() {
        return scenaristMovieList;
    }

    public void setScenaristMovieList(List<MovieResponse> scenaristMovieList) {
        this.scenaristMovieList = scenaristMovieList;
    }
}
