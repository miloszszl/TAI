package com.milosz.tai.app.Entities;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class Rate {

    @NotNull
    @Min(1)
    @Max(5)
    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
