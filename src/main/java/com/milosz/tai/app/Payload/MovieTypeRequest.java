package com.milosz.tai.app.Payload;

import javax.validation.constraints.Size;

import static com.milosz.tai.app.Entities.Parameters.ConstraintValues.MOVIE_TYPE_NAME_LENGTH;

public class MovieTypeRequest {

    @Size(max = MOVIE_TYPE_NAME_LENGTH)
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
