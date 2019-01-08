package com.milosz.tai.app.Views;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "movie_years")
public class YearsView {

    @Id
    @Column
    private int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
