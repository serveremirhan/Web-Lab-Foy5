package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;

@Controller
public class MovieController {
    @Autowired
    private MovieRepository MovieRepository;

    @PostMapping("/newMovie")
    Movie newMovie(@RequestBody Movie newMovie) {
        return MovieRepository.save(newMovie);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<Movie> getMovie(@PathVariable Integer movieId) {
        return MovieRepository.findById(movieId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/uptMovie/{movieId}")
    Optional<Object> updateMovie(@RequestBody Movie newMovie, @PathVariable String movieId) {
        try {
            Integer parsedMovieId = Integer.parseInt(movieId);
            return MovieRepository.findById(parsedMovieId).map(movie -> {
                movie.setMovie(newMovie.getMovie());
                movie.setDirector(newMovie.getDirector());
                movie.setCategories(newMovie.getCategories());
                movie.setRating(newMovie.getRating());
                return MovieRepository.save(movie);
            });
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid movieId format");
        }
    }

    @GetMapping("/delMovie/{movieId}")
    String deleteMovie(@PathVariable Integer movieId) {

        MovieRepository.deleteById(movieId);
        return "redirect:/";
    }
}