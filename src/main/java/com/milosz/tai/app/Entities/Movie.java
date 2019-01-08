package com.milosz.tai.app.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.milosz.tai.app.Entities.Parameters.ConstraintValues.MOVIE_DESCRIPTION_LENGTH;
import static com.milosz.tai.app.Entities.Parameters.ConstraintValues.MOVIE_TITLE_LENGTH;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = MOVIE_TITLE_LENGTH)
    @Column(unique = true)
    private String title;

    @Size(max = MOVIE_DESCRIPTION_LENGTH)
    private String description;

    @JsonFormat(pattern="dd-MM-yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date worldPremiere;

    @JsonFormat(pattern="dd-MM-yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date polandPremiere;

    @Lob
    private byte[] image;

    @JsonIgnore
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "movie_movie_type",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_type_id")
    )
    private List<MovieType> movieTypeList;

    @OneToMany(
            mappedBy = "movie",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Actor> actorList;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<MoviePerson> directorList;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<MoviePerson> scenaristList;

    @Digits(integer = 2, fraction = 1)
    private BigDecimal rate;

    @Min(value = 0)
    private Long boxoffice;     //$

    @Min(value = 0)
    private Integer time;       //minutes

    @OneToMany(mappedBy = "movie",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MovieComment> movieCommentList;

    @OneToMany(mappedBy = "movie",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MovieRate> movieRateList;

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

    public List<MovieType> getMovieTypeList() {
        return movieTypeList;
    }

    public void setMovieTypeList(List<MovieType> movieTypeList) {
        this.movieTypeList = movieTypeList;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    public List<MoviePerson> getDirectorList() {
        return directorList;
    }

    public void setDirectorList(List<MoviePerson> directorList) {
        this.directorList = directorList;
    }

    public List<MoviePerson> getScenaristList() {
        return scenaristList;
    }

    public void setScenaristList(List<MoviePerson> scenaristList) {
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

    public List<MovieComment> getMovieCommentList() {
        return movieCommentList;
    }

    public void setMovieCommentList(List<MovieComment> movieCommentList) {
        this.movieCommentList = movieCommentList;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<MovieRate> getMovieRateList() {
        return movieRateList;
    }

    public void setMovieRateList(List<MovieRate> movieRateList) {
        this.movieRateList = movieRateList;
    }
}
