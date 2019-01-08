package com.milosz.tai.app.Controllers;

import com.milosz.tai.app.Controllers.Exceptions.MovieNotFoundException;
import com.milosz.tai.app.Entities.Movie;
import com.milosz.tai.app.Entities.MovieType;
import com.milosz.tai.app.Entities.User;
import com.milosz.tai.app.Exceptions.ResourceNotFoundException;
import com.milosz.tai.app.Payload.*;
import com.milosz.tai.app.Projections.MovieShort;
import com.milosz.tai.app.Projections.MovieThumbnail;
import com.milosz.tai.app.Repositories.MovieRepository;
import com.milosz.tai.app.Repositories.MovieTypeRepository;
import com.milosz.tai.app.Security.CurrentUser;
import com.milosz.tai.app.Security.UserPrincipal;
import com.milosz.tai.app.Services.MovieService;
import com.milosz.tai.app.Views.YearsView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/movies")
public class MovieController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final MovieRepository movieRepository;

    private final MovieTypeRepository movieTpeRepository;

    private final MovieService movieService;

    private final MovieTypeRepository movieTypeRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository, MovieTypeRepository movieTpeRepository, MovieService movieService, MovieTypeRepository movieTypeRepository) {
        this.movieRepository = movieRepository;
        this.movieTpeRepository = movieTpeRepository;
        this.movieService = movieService;
        this.movieTypeRepository = movieTypeRepository;
    }

    private Sort selectSortOption(int opt) {
        switch (opt) {
            case 0:
                return new Sort(Sort.Direction.ASC, "id");
            case 1:
                return new Sort(Sort.Direction.ASC, "title");
            case 2:
                return new Sort(Sort.Direction.DESC, "title");
            case 3:
                return new Sort(Sort.Direction.ASC, "rate");
            case 4:
                return new Sort(Sort.Direction.DESC, "rate");
            case 5:
                return new Sort(Sort.Direction.ASC, "polandPremiere", "worldPremiere");
            case 6:
                return new Sort(Sort.Direction.DESC, "polandPremiere", "worldPremiere");
            default:
                return null;
        }
    }

    @GetMapping("/")
    Iterable<MovieThumbnail> allMoviesWithCriteria(@RequestParam(value = "types", required = false) List<Long> typeIds,
                                                   @RequestParam(value = "years", required = false) List<Integer> years,
                                                   @RequestParam(value = "sortOpt") int sort,
                                                   @RequestParam(value = "title", required = false) String title) throws Exception {

        Sort sortOpt = selectSortOption(sort);

        if (typeIds == null && years != null && StringUtils.isBlank(title)) {
            return movieRepository.findByPremiereYearIn(years, sortOpt);
        } else if (typeIds == null && years == null && !StringUtils.isBlank(title)) {
            return movieRepository.findByTitleContainingIgnoreCase(title, sortOpt);
        } else if (typeIds != null && years == null && StringUtils.isBlank(title)) {
            return movieRepository.findDistinctByMovieTypeListIdIn(typeIds, sortOpt);
        } else if (typeIds != null && years != null && StringUtils.isBlank(title)) {
            return movieRepository.findDistinctByMovieTypeAndPremiereYear(typeIds, years, sortOpt);
        } else if (typeIds != null && years == null && !StringUtils.isBlank(title)) {
            return movieRepository.findDistinctByMovieTypeListIdInAndTitleContainingIgnoreCase(typeIds, title, sortOpt);
        } else if (typeIds == null && years != null && !StringUtils.isBlank(title)) {
            return movieRepository.findByPremiereYearInAndTitle(years, title, sortOpt);
        } else if (typeIds != null && years != null && !StringUtils.isBlank(title)) {
            return movieRepository.findDistinctByMovieTypeAndPremiereYearAndTitle(typeIds, years, title, sortOpt);
        } else {    //typeIds == null && years == null && StringUtils.isBlank(title)
            return movieRepository.findAll(sortOpt);
        }

    }

//    @PostMapping("/")
//    Movie newMovie(@RequestBody Movie newMovie) {
//        return movieRepository.save(newMovie);
//    }

    @PostMapping("/{id}/comments")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> newMovieComment(@CurrentUser UserPrincipal currentUser, @PathVariable("id") Long movieId,
                                      @Valid @RequestBody CommentRequest commentRequest) {
        return new ResponseEntity<>(movieService.addMovieComment(movieId, commentRequest, currentUser), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    MovieDetailsResponse one(@PathVariable Long id) throws Exception {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));

        MovieDetailsResponse resp = new MovieDetailsResponse();
        resp.setId(movie.getId());
        resp.setTitle(movie.getTitle());
        resp.setDescription(movie.getDescription());
        resp.setWorldPremiere(movie.getWorldPremiere());
        resp.setPolandPremiere(movie.getPolandPremiere());
        resp.setImage(movie.getImage());

        List<String> movieTypes = movie.getMovieTypeList().stream()
                .filter(Objects::nonNull)
                .map(MovieType::getName)
                .collect(Collectors.toList());
        resp.setMovieTypes(movieTypes);

        List<ActorResponse> actors = movie.getActorList().stream()
                .filter(Objects::nonNull)
                .map(item -> new ActorResponse(item.getMoviePerson().getId(),
                        item.getMoviePerson().getName() + " " + item.getMoviePerson().getSurname(),
                        item.getRoleName(),
                        item.getMoviePerson().getImage()))
                .collect(Collectors.toList());

        resp.setActorList(actors);

        List<MoviePersonResponse> directors = movie.getDirectorList().stream()
                .filter(Objects::nonNull)
                .map(item -> new MoviePersonResponse(item.getId(), item.getName() + " " + item.getSurname()))
                .collect(Collectors.toList());
        resp.setDirectorList(directors);

        List<MoviePersonResponse> scenarists = movie.getScenaristList().stream()
                .filter(Objects::nonNull)
                .map(item -> new MoviePersonResponse(item.getId(), item.getName() + " " + item.getSurname()))
                .collect(Collectors.toList());
        resp.setScenaristList(scenarists);

        resp.setRate(movie.getRate());
        resp.setBoxoffice(movie.getBoxoffice());
        resp.setTime(movie.getTime());

        List<CommentResponse> comments = movie.getMovieCommentList().stream()
                .filter(Objects::nonNull)
                .map(item -> {
                            User user = item.getUser();
                            return new CommentResponse(new UserInComment(user.getId(), user.getNickname(), user.getImage()),
                                    item.getContent(), item.getCommentedAt());
                        }
                )
                .collect(Collectors.toList());
        Collections.sort(comments);

        resp.setMovieCommentList(comments);

        return resp;
    }

//    @PutMapping("/{id}")
//    Movie updateMovie(@RequestBody Movie newMovie, @PathVariable Long id) {
//        return movieRepository.findById(id).map(movie -> {
//            movie.setTitle(newMovie.getTitle());
//            movie.setDescription(newMovie.getDescription());
//            movie.setWorldPremiere(newMovie.getWorldPremiere());
//            movie.setPolandPremiere(newMovie.getPolandPremiere());
//            movie.setBoxoffice(newMovie.getBoxoffice());
//            //TODO
//            return movieRepository.save(movie);
//        }).orElseGet(() -> movieRepository.save(newMovie));
//    }

//    @DeleteMapping("/{id}")
//    void deleteMovie(@PathVariable Long id) {
//        movieRepository.deleteById(id);
//    }

    @GetMapping("/types")
    List<MovieType> allTypes() {
        return movieTpeRepository.findAll();
    }

    @GetMapping("/years")
    Collection<YearsView> allYears() {
        logger.info("List {}", movieRepository.findDistinctYears());
        return movieRepository.findDistinctYears();
    }

//    @PutMapping("/addType")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> addMovieType(@RequestBody MovieTypeRequest request) {
//        MovieType type = new MovieType();
//        type.setName(request.getName());
//        type = movieTypeRepository.save(type);
//        return new ResponseEntity<>(type, HttpStatus.CREATED);
//    }

    @GetMapping("/short")
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<MovieShort> getAllMoviesShort(){
        return movieRepository.findAllShort();
    }


//    @PutMapping("/add")
//    @PreAuthorize("hasRole('ADMIN')")
//    public User getUserProfile(@CurrentUser UserPrincipal currentUser, @RequestBody String req) {
//        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUser.getId()));
//        return user;
//        return null;
//    }
}