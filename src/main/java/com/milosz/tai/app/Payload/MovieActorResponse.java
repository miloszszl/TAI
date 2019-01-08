package com.milosz.tai.app.Payload;

public class MovieActorResponse extends MovieResponse {

    private String roleName;

    public MovieActorResponse(Long id, String movieName, byte[] image, String roleName) {
        super(id, movieName, image);
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
