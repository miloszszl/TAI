package com.milosz.tai.app.Repositories;

import com.milosz.tai.app.Entities.MoviePersonComment;
import com.milosz.tai.app.Entities.MovieUserId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviePersonCommentRepository extends CrudRepository<MoviePersonComment, MovieUserId> {
}
