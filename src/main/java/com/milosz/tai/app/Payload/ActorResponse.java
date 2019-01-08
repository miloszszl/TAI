package com.milosz.tai.app.Payload;

public class ActorResponse {
    private Long id;
    private String fullName;
    private String roleName;
    private byte[] image;

    public ActorResponse(Long id, String fullName, String roleName, byte[] image) {
        this.id = id;
        this.fullName = fullName;
        this.roleName = roleName;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
