package com.milosz.tai.app.Repositories;

import com.milosz.tai.app.Entities.MoviePersonRate;
import com.milosz.tai.app.Entities.MoviePersonUserId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoviePersonRateRepository extends CrudRepository<MoviePersonRate, MoviePersonUserId> {
        Optional<MoviePersonRate> findMoviePersonRateById(MoviePersonUserId id);
}
