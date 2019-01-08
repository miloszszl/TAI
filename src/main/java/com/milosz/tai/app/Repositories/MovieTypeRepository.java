package com.milosz.tai.app.Repositories;

import com.milosz.tai.app.Entities.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieTypeRepository extends JpaRepository<MovieType, Long> {
}
