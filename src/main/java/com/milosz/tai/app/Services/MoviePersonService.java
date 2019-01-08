package com.milosz.tai.app.Services;

import com.milosz.tai.app.Entities.*;
import com.milosz.tai.app.Exceptions.ResourceNotFoundException;
import com.milosz.tai.app.Payload.CommentRequest;
import com.milosz.tai.app.Payload.CommentResponse;
import com.milosz.tai.app.Payload.UserInComment;
import com.milosz.tai.app.Repositories.*;
import com.milosz.tai.app.Security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.OptionalDouble;

@Service
public class MoviePersonService {

    private final MoviePersonCommentRepository moviePersonCommentRepository;
    private final MoviePersonRepository moviePersonRepository;
    private final UserRepository userRepository;

    @Autowired
    public MoviePersonService(UserRepository userRepository, MoviePersonRepository moviePersonRepository,
                              MoviePersonCommentRepository moviePersonCommentRepository) {
        this.userRepository = userRepository;
        this.moviePersonRepository = moviePersonRepository;
        this.moviePersonCommentRepository = moviePersonCommentRepository;
    }

    public CommentResponse addMoviePersonComment(Long moviePersonId, CommentRequest commentRequest, UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", currentUser.getId()));

        MoviePerson moviePerson = moviePersonRepository.findById(moviePersonId).orElseThrow(() ->
                new ResourceNotFoundException("MoviePerson", "id", moviePersonId));

        UserInComment userInComment = new UserInComment(user.getId(), user.getNickname(), user.getImage());

        MoviePersonComment moviePersonComment = new MoviePersonComment();
        moviePersonComment.setUser(user);
        moviePersonComment.setContent(commentRequest.getContent());
        moviePersonComment.setMoviePerson(moviePerson);
        moviePersonComment = moviePersonCommentRepository.save(moviePersonComment);

        return new CommentResponse(userInComment, moviePersonComment.getContent(), moviePersonComment.getCommentedAt());
    }

    public BigDecimal updateMoviePersonRate(MoviePerson moviePerson) {

        OptionalDouble avg = moviePerson.getMoviePersonRateList().stream().filter(Objects::nonNull)
                .map(MoviePersonRate::getValue).filter(Objects::nonNull).mapToDouble(a -> a).average();

        if(!avg.isPresent()){
            return null;
        }else{
            moviePerson.setRate(new BigDecimal(avg.getAsDouble()).setScale(1, RoundingMode.DOWN));
            moviePersonRepository.save(moviePerson);
            return moviePerson.getRate();
        }
    }
}
