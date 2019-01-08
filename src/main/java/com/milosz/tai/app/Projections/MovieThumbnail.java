package com.milosz.tai.app.Projections;

import java.math.BigDecimal;

public interface MovieThumbnail {
    Long getId();
    String getTitle();
    byte[] getImage();
    BigDecimal getRate();
}