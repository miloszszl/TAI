package com.milosz.tai.app.Payload;

public class MovieResponse {
    private Long id;
    private String movieName;
    private byte[] image;

    public MovieResponse(){}

    public MovieResponse(Long id, String movieName,byte[] image) {
        this.id = id;
        this.movieName = movieName;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
