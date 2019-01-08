package com.milosz.tai.app.Repositories;

import com.milosz.tai.app.Entities.MoviePerson;

import com.milosz.tai.app.Projections.MoviePersonThumbnail;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviePersonRepository extends CrudRepository<MoviePerson, Long> {

    @Query("SELECT m.id as id, m.name as name, m.surname as surname, m.image as image, m.rate as rate " +
            "FROM MoviePerson m")
    List<MoviePersonThumbnail> findAll(@Param("sort") Sort sort);

    @Query("SELECT m.id as id, m.name as name, m.surname as surname, m.image as image, m.rate as rate " +
            "FROM MoviePerson m " +
            "WHERE LOWER(CONCAT(m.name,' ',m.surname)) LIKE CONCAT('%',LOWER(:fullName),'%') OR " +
            "LOWER(CONCAT(m.surname,' ',m.name)) LIKE CONCAT('%',LOWER(:fullName),'%')")
    List<MoviePersonThumbnail> findByNameAndSurnameContainingCustom(@Param("fullName") String fullName, @Param("sort") Sort sort);

    List<MoviePersonThumbnail> findDistinctByMoviePersonCommentListUser_UserId(Long id);

    List<MoviePersonThumbnail> findDistinctByMoviePersonRateListUser_UserId(Long id);
}
