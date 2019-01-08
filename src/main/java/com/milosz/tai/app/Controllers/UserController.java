package com.milosz.tai.app.Controllers;

import com.milosz.tai.app.Entities.*;
import com.milosz.tai.app.Exceptions.ResourceNotFoundException;
import com.milosz.tai.app.Payload.RatePayload;
import com.milosz.tai.app.Payload.UserSummary;
import com.milosz.tai.app.Projections.MoviePersonThumbnail;
import com.milosz.tai.app.Projections.MovieThumbnail;
import com.milosz.tai.app.Repositories.*;
import com.milosz.tai.app.Security.CurrentUser;
import com.milosz.tai.app.Security.UserPrincipal;
import com.milosz.tai.app.Services.MoviePersonService;
import com.milosz.tai.app.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    private final MovieRepository movieRepository;

    private final MoviePersonRepository moviePersonRepository;

    private final MovieRateRepository movieRateRepository;

    private final MoviePersonRateRepository moviePersonRateRepository;

    private final MovieService movieService;

    private final MoviePersonService moviePersonService;

    @Autowired
    public UserController(UserRepository userRepository, MovieRepository movieRepository,
                          MoviePersonRepository moviePersonRepository, MovieRateRepository movieRateRepository,
                          MovieService movieService, MoviePersonRateRepository moviePersonRateRepository, MoviePersonService moviePersonService) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.moviePersonRepository = moviePersonRepository;
        this.movieRateRepository = movieRateRepository;
        this.movieService = movieService;
        this.moviePersonRateRepository = moviePersonRateRepository;
        this.moviePersonService = moviePersonService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        Boolean isAdmin = currentUser.getAuthorities().stream().anyMatch(item ->
                item.getAuthority().equals("ROLE_ADMIN"));
        return new UserSummary(currentUser.getId(), currentUser.getNickname(), currentUser.getEmail(), isAdmin);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public User getUserProfile(@PathVariable Long id, @CurrentUser UserPrincipal currentUser) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", "username", currentUser.getId()));
    }

    @GetMapping("/{id}/commentedMovies")
    @PreAuthorize("hasRole('USER')")
    public Collection<MovieThumbnail> getUserCommentedMovies(@PathVariable Long id) {
        return movieRepository.findDistinctByMovieCommentListUser_UserId(id);
    }

    @GetMapping("/{id}/commentedMoviePersons")
    @PreAuthorize("hasRole('USER')")
    public Collection<MoviePersonThumbnail> getUserCommentedMoviePersons(@PathVariable Long id) {
        return moviePersonRepository.findDistinctByMoviePersonCommentListUser_UserId(id);
    }

    @GetMapping("/{id}/ratedMovies")
    @PreAuthorize("hasRole('USER')")
    public Collection<MovieThumbnail> getUserRatedMovies(@PathVariable Long id) {
        return movieRepository.findDistinctByMovieRateListUser_UserId(id);
    }

    @GetMapping("/{id}/ratedMoviePersons")
    @PreAuthorize("hasRole('USER')")
    public Collection<MoviePersonThumbnail> getUserRatedMoviePersons(@PathVariable Long id) {
        return moviePersonRepository.findDistinctByMoviePersonRateListUser_UserId(id);
    }

    @GetMapping("/me/movies/{id}/rate")
    @PreAuthorize("hasRole('USER')")
    public Integer getMyMovieRate(@PathVariable Long id, @CurrentUser UserPrincipal currentUser) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));
        MovieUserId muId = new MovieUserId(movie.getId(), currentUser.getId());

        MovieRate res = movieRateRepository.findMovieRateById(muId).orElseThrow(
                () -> new ResourceNotFoundException("MovieUserId", "id", movie.getId() + ", " + currentUser.getId()));
        return res.getValue();
    }

    @PutMapping("/me/movies/{id}/rate")
    @PreAuthorize("hasRole('USER')")
    public BigDecimal rateMovie(@PathVariable Long id, @CurrentUser UserPrincipal currentUser,
                                @RequestBody RatePayload rate) {

        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));

        User user = userRepository.findById(currentUser.getId()).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", currentUser.getId()));

        movieRateRepository.findMovieRateById(new MovieUserId(movie.getId(), user.getId())).map(movieRate -> {
            movieRate.setValue(rate.getRate());
            return movieRateRepository.save(movieRate);
        }).orElseGet(() -> {
            MovieRate movieRate = new MovieRate();
            movieRate.setMovie(movie);
            movieRate.setUser(user);
            movieRate.setValue(rate.getRate());
            return movieRateRepository.save(movieRate);
        });

        return movieService.updateMovieRate(movie);
    }

    @GetMapping("/me/stars/{id}/rate")
    @PreAuthorize("hasRole('USER')")
    public Integer getMyMoviePersonRate(@PathVariable Long id, @CurrentUser UserPrincipal currentUser) {
        MoviePerson moviePerson = moviePersonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MoviePerson", "id", id));

        MoviePersonUserId mpuId = new MoviePersonUserId(moviePerson.getId(), currentUser.getId());

        MoviePersonRate res = moviePersonRateRepository.findMoviePersonRateById(mpuId).orElseThrow(
                () -> new ResourceNotFoundException("MoviePersonUserId", "id",
                        moviePerson.getId() + ", " + currentUser.getId()));
        return res.getValue();
    }


    @PutMapping("/me/stars/{id}/rate")
    @PreAuthorize("hasRole('USER')")
    public BigDecimal rateMoviePerson(@PathVariable Long id, @CurrentUser UserPrincipal currentUser,
                                      @RequestBody RatePayload rate) {

        MoviePerson moviePerson = moviePersonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MoviePerson", "id", id));

        User user = userRepository.findById(currentUser.getId()).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", currentUser.getId()));


        moviePersonRateRepository.findMoviePersonRateById(
                new MoviePersonUserId(moviePerson.getId(), user.getId())).map(movieRate -> {
            movieRate.setValue(rate.getRate());
            return moviePersonRateRepository.save(movieRate);
        }).orElseGet(() -> {
            MoviePersonRate moviePersonRate = new MoviePersonRate();
            moviePersonRate.setMoviePerson(moviePerson);
            moviePersonRate.setUser(user);
            moviePersonRate.setValue(rate.getRate());
            return moviePersonRateRepository.save(moviePersonRate);
        });

        return moviePersonService.updateMoviePersonRate(moviePerson);
    }
}
