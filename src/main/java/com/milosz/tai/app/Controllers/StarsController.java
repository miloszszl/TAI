package com.milosz.tai.app.Controllers;

import com.milosz.tai.app.Entities.MoviePerson;
import com.milosz.tai.app.Exceptions.ResourceNotFoundException;
import com.milosz.tai.app.Payload.*;
import com.milosz.tai.app.Projections.MoviePersonThumbnail;
import com.milosz.tai.app.Repositories.MoviePersonCommentRepository;
import com.milosz.tai.app.Repositories.MoviePersonRepository;
import com.milosz.tai.app.Security.CurrentUser;
import com.milosz.tai.app.Security.UserPrincipal;
import com.milosz.tai.app.Services.MoviePersonService;
import com.milosz.tai.app.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/stars")
public class StarsController {

    private final MoviePersonRepository moviePersonRepository;

    private final MoviePersonService moviePersonService;

    @Autowired
    public StarsController(MoviePersonRepository moviePersonRepository, MoviePersonService moviePersonService) {
        this.moviePersonRepository = moviePersonRepository;
        this.moviePersonService = moviePersonService;
    }

    private Sort selectSortOption(int opt) {
        switch (opt) {
            case 0:
                return new Sort(Sort.Direction.ASC, "id");
            case 1:
                return new Sort(Sort.Direction.ASC, "surname");
            case 2:
                return new Sort(Sort.Direction.DESC, "surname");
            case 3:
                return new Sort(Sort.Direction.ASC, "rate");
            case 4:
                return new Sort(Sort.Direction.DESC, "rate");
            default:
                return null;
        }
    }

    @GetMapping("/")
    Iterable<MoviePersonThumbnail> allStars(@RequestParam(value = "fullName", required = false) String fullName,
                                            @RequestParam(value = "sortOpt") int sort) throws Exception {

        Sort sortOpt = selectSortOption(sort);
        if (!StringUtils.isBlank(fullName)) {
            fullName = fullName.replaceAll("\\s+", " ").trim();
            return moviePersonRepository.findByNameAndSurnameContainingCustom(fullName, sortOpt);
        } else {
            return moviePersonRepository.findAll(sortOpt);
        }
    }

    @GetMapping("/{id}")
    MoviePersonDetailsResponse one(@PathVariable Long id) throws Exception {

        MoviePerson moviePerson = moviePersonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MoviePerson", "id", id));

        MoviePersonDetailsResponse res = new MoviePersonDetailsResponse();
        res.setId(moviePerson.getId());
        res.setName(moviePerson.getName());
        res.setSurname(moviePerson.getSurname());
        res.setBirthdate(moviePerson.getBirthdate());
        res.setBirthPlace(moviePerson.getBirthPlace());
        res.setHeight(moviePerson.getHeight());
        res.setImage(moviePerson.getImage());
        res.setRate(moviePerson.getRate());

        List<MovieResponse> actorMovies = moviePerson.getActorMovieList().stream()
                .filter(Objects::nonNull)
                .map(item -> new MovieActorResponse(item.getMovie().getId(), item.getMovie().getTitle(),
                        item.getMovie().getImage(), item.getRoleName()))
                .collect(Collectors.toList());
        res.setActorMovieList(actorMovies);

        List<MovieResponse> directedMovies = moviePerson.getDirectorMovieList().stream()
                .filter(Objects::nonNull)
                .map(item -> new MovieResponse(item.getId(), item.getTitle(),
                        item.getImage()))
                .collect(Collectors.toList());
        res.setDirectorMovieList(directedMovies);

        List<MovieResponse> scenaristMovies = moviePerson.getScenaristMovieList().stream()
                .filter(Objects::nonNull)
                .map(item -> new MovieResponse(item.getId(), item.getTitle(),
                        item.getImage()))
                .collect(Collectors.toList());
        res.setScenaristMovieList(scenaristMovies);

        List<CommentResponse> comments = moviePerson.getMoviePersonCommentList().stream()
                .filter(Objects::nonNull)
                .map(Utils::makeCommentResponse).sorted().collect(Collectors.toList());

        res.setMoviePersonCommentList(comments);

        return res;
    }

    @PostMapping("/{id}/comments")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> newMoviePersonComment(@CurrentUser UserPrincipal currentUser, @PathVariable("id") Long moviePersonId,
                                            @Valid @RequestBody CommentRequest commentRequest) {
        return new ResponseEntity<>(moviePersonService.addMoviePersonComment(moviePersonId, commentRequest, currentUser),
                HttpStatus.CREATED);
    }

}
