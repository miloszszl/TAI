package com.milosz.tai.app.Repositories;

import com.milosz.tai.app.Entities.Movie;
import com.milosz.tai.app.Projections.MovieShort;
import com.milosz.tai.app.Projections.MovieThumbnail;
import com.milosz.tai.app.Views.YearsView;


import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    @Query("SELECT m.id as id, m.title as title FROM Movie m")
    List<MovieShort> findAllShort();

    @Query("SELECT y FROM YearsView y")
    Collection<YearsView> findDistinctYears();

    @Query("SELECT m.id as id, m.title as title, m.image as image, m.rate as rate FROM Movie m")
    List<MovieThumbnail> findAll(@Param("sort") Sort sort);

    @Query("SELECT DISTINCT m.id as id, m.title as title, m.image as image, m.rate as rate FROM Movie m " +
            "INNER JOIN m.movieTypeList t WHERE t.id in :typeIds")
    List<MovieThumbnail> findDistinctByMovieTypeListIdIn(@Param("typeIds") List<Long> typeIds, @Param("sort") Sort sort);

    @Query("SELECT DISTINCT m.id as id, m.title as title, m.image as image, m.rate as rate FROM Movie m " +
            "INNER JOIN m.movieTypeList t WHERE t.id in :typeIds AND LOWER(m.title) like CONCAT('%',LOWER(:title),'%')")
    List<MovieThumbnail> findDistinctByMovieTypeListIdInAndTitleContainingIgnoreCase(@Param("typeIds") List<Long> typeIds,
                                                                                     @Param("title") String title, @Param("sort") Sort sort);

    @Query("SELECT m.id as id, m.title as title, m.image as image, m.rate as rate FROM Movie m WHERE " +
            "LOWER(m.title) like CONCAT('%',LOWER(:title),'%')")
    List<MovieThumbnail> findByTitleContainingIgnoreCase(@Param("title") String title, @Param("sort") Sort sort);

    @Query("SELECT m.id as id, m.title as title, m.image as image, m.rate as rate FROM Movie m WHERE " +
            "function('YEAR',m.polandPremiere) in :years or function('YEAR',m.worldPremiere) in :years")
    List<MovieThumbnail> findByPremiereYearIn(@Param("years") List<Integer> years, @Param("sort") Sort sort);

    @Query("SELECT m.id as id, m.title as title, m.image as image, m.rate as rate FROM Movie m WHERE " +
            "(function('YEAR',m.polandPremiere) in :years or function('YEAR',m.worldPremiere) in :years) and " +
            "LOWER(m.title) like CONCAT('%',LOWER(:title),'%')")
    List<MovieThumbnail> findByPremiereYearInAndTitle(@Param("years") List<Integer> years, @Param("title") String title, @Param("sort") Sort sort);

    @Query("SELECT DISTINCT m.id as id, m.title as title, m.image as image, m.rate as rate FROM Movie m INNER JOIN m.movieTypeList t WHERE " +
            "t.id in :typeIds and " +
            "(function('YEAR',m.polandPremiere) in :years or function('YEAR',m.worldPremiere) in :years)")
    List<MovieThumbnail> findDistinctByMovieTypeAndPremiereYear(@Param("typeIds") List<Long> typeIds, @Param("years") List<Integer> years, @Param("sort") Sort sort);

    @Query("SELECT DISTINCT m.id as id, m.title as title, m.image as image, m.rate as rate FROM Movie m INNER JOIN m.movieTypeList t WHERE " +
            "t.id in :typeIds and " +
            "(function('YEAR',m.polandPremiere) in :years or function('YEAR',m.worldPremiere) in :years) and " +
            "LOWER(m.title) like CONCAT('%',LOWER(:title),'%')")
    List<MovieThumbnail> findDistinctByMovieTypeAndPremiereYearAndTitle(@Param("typeIds") List<Long> typeIds, @Param("years") List<Integer> years,
                                                                        @Param("title") String title, @Param("sort") Sort sort);

    List<MovieThumbnail> findDistinctByMovieCommentListUser_UserId(Long id);

    List<MovieThumbnail> findDistinctByMovieRateListUser_UserId(Long id);

}