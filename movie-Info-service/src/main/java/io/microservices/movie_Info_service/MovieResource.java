package io.microservices.movie_Info_service;

import io.microservices.movie_Info_service.model.Movie;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieResource {
	@RequestMapping("/{movieId}")
	public Movie getMovie(@PathVariable("movieId") String movieId){
        return new Movie(movieId, "Sample Movie");
    }

}
