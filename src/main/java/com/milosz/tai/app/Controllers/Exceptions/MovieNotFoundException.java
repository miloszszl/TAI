package com.milosz.tai.app.Controllers.Exceptions;

public class MovieNotFoundException extends Exception {
    public MovieNotFoundException(Long id) {
        super("Could not find movie " + id);
    }
}