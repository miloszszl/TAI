package com.milosz.tai.app.Payload;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MovieDetailsResponse {

    private Long id;
    private String title;
    private String description;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date worldPremiere;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date polandPremiere;
    private byte[] image;
    private List<String> movieTypes;
    private List<ActorResponse> actorList;
    private List<MoviePersonResponse> directorList;
    private List<MoviePersonResponse> scenaristList;
    private BigDecimal rate;
    private Long boxoffice;     //$
    private Integer time;       //minutes
    private List<CommentResponse> movieCommentList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getWorldPremiere() {
        return worldPremiere;
    }

    public void setWorldPremiere(Date worldPremiere) {
        this.worldPremiere = worldPremiere;
    }

    public Date getPolandPremiere() {
        return polandPremiere;
    }

    public void setPolandPremiere(Date polandPremiere) {
        this.polandPremiere = polandPremiere;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<String> getMovieTypes() {
        return movieTypes;
    }

    public void setMovieTypes(List<String> movieTypes) {
        this.movieTypes = movieTypes;
    }

    public List<ActorResponse> getActorList() {
        return actorList;
    }

    public void setActorList(List<ActorResponse> actorList) {
        this.actorList = actorList;
    }

    public List<MoviePersonResponse> getDirectorList() {
        return directorList;
    }

    public void setDirectorList(List<MoviePersonResponse> directorList) {
        this.directorList = directorList;
    }

    public List<MoviePersonResponse> getScenaristList() {
        return scenaristList;
    }

    public void setScenaristList(List<MoviePersonResponse> scenaristList) {
        this.scenaristList = scenaristList;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Long getBoxoffice() {
        return boxoffice;
    }

    public void setBoxoffice(Long boxoffice) {
        this.boxoffice = boxoffice;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public List<CommentResponse> getMovieCommentList() {
        return movieCommentList;
    }

    public void setMovieCommentList(List<CommentResponse> movieCommentList) {
        this.movieCommentList = movieCommentList;
    }
}
