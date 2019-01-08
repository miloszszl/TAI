package com.milosz.tai.app.Projections;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface MoviePersonThumbnail {
    Long getId();
    @Value("#{target.name + ' ' + target.surname}")
    String getFullName();
    byte[] getImage();
    BigDecimal getRate();
}