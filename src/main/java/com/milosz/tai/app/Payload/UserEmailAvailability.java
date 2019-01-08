package com.milosz.tai.app.Payload;

public class UserEmailAvailability {

    Boolean available;

    public UserEmailAvailability(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
