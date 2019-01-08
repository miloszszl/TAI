package com.milosz.tai.app.Repositories;

import com.milosz.tai.app.Entities.MovieRate;
import com.milosz.tai.app.Entities.MovieUserId;
import com.milosz.tai.app.Projections.MovieRateValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRateRepository extends CrudRepository<MovieRate, MovieUserId> {
        Optional<MovieRate> findMovieRateById(MovieUserId id);
}
