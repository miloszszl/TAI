package com.milosz.tai.app.Repositories;

import com.milosz.tai.app.Entities.MovieComment;
import com.milosz.tai.app.Entities.MovieUserId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCommentRepository extends CrudRepository<MovieComment, MovieUserId> {
}
