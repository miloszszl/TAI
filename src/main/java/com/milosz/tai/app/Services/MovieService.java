package com.milosz.tai.app.Services;

import com.milosz.tai.app.Entities.Movie;
import com.milosz.tai.app.Entities.MovieComment;
import com.milosz.tai.app.Entities.MovieRate;
import com.milosz.tai.app.Entities.User;
import com.milosz.tai.app.Exceptions.ResourceNotFoundException;
import com.milosz.tai.app.Payload.CommentRequest;
import com.milosz.tai.app.Payload.CommentResponse;
import com.milosz.tai.app.Payload.UserInComment;
import com.milosz.tai.app.Repositories.MovieCommentRepository;
import com.milosz.tai.app.Repositories.MovieRepository;
import com.milosz.tai.app.Repositories.UserRepository;
import com.milosz.tai.app.Security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieCommentRepository movieCommentRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @Autowired
    public MovieService(MovieCommentRepository movieCommentRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.movieCommentRepository = movieCommentRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public CommentResponse addMovieComment(Long movieId, CommentRequest commentRequest, UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", currentUser.getId()));

        Movie movie = movieRepository.findById(movieId).orElseThrow(() ->
                new ResourceNotFoundException("Movie", "id", movieId));

        MovieComment movieComment = new MovieComment();
        movieComment.setMovie(movie);
        movieComment.setUser(user);
        movieComment.setContent(commentRequest.getContent());

        movieComment = movieCommentRepository.save(movieComment);

        UserInComment userInComment = new UserInComment(user.getId(), user.getNickname(), user.getImage());

        return new CommentResponse(userInComment, movieComment.getContent(), movieComment.getCommentedAt());
    }

    public BigDecimal updateMovieRate(Movie movie) {

        OptionalDouble avg = movie.getMovieRateList().stream().filter(Objects::nonNull)
                .map(MovieRate::getValue).filter(Objects::nonNull).mapToDouble(a -> a).average();

        if(avg.isPresent()){
            movie.setRate(new BigDecimal(avg.getAsDouble()).setScale(1, RoundingMode.DOWN));
            movieRepository.save(movie);
            return movie.getRate();
        }

        return null;
    }
}
