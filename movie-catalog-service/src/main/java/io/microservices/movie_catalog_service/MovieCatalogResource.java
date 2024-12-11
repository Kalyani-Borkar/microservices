package io.microservices.movie_catalog_service;

import io.microservices.movie_catalog_service.model.CatalogItem;
import io.microservices.movie_catalog_service.model.Movie;
import io.microservices.movie_catalog_service.model.Rating;
import io.microservices.movie_catalog_service.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder webClientBuilder;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

		UserRating ratings=restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);

		return ratings.getUserRating()
				.stream().map(rating -> {
					Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
					return new CatalogItem(movie.getName(), "test", rating.getRating());
				})
				.collect(Collectors.toList());
	}
	 /* webClientBuilder.build()
						 .get()
						 .uri("http://localhost:8082/movies/" + rating.getMovieId()).
						 retrieve().
						 bodyToMono(Movie.class).
						 block();*/
}
