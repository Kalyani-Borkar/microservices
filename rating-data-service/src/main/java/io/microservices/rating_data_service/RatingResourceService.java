package io.microservices.rating_data_service;

import io.microservices.rating_data_service.model.Rating;
import io.microservices.rating_data_service.model.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingResourceService {

	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId){
        return new Rating(movieId, 4);
    }

	@RequestMapping("users/{userId}")
	public UserRating getUserRating(@PathVariable("userId") String userId){
		List<Rating> ratings= Arrays.asList(
				new Rating("1234",4),
				new Rating("7899",3)
		);
		UserRating userRating=new UserRating();
		userRating.setUserRating(ratings);
		return userRating;
	}
}
