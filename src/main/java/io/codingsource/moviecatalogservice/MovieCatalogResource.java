package io.codingsource.moviecatalogservice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import model.CatalogItem;
import model.Movie;
import model.Rating;
import model.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem>getCatalog(@PathVariable("userId") String userId){
	
		UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, 
		UserRating.class);
		//For each movie id, call movie info service and get details
		return ratings.getUserRatings().stream().<CatalogItem>map(rating -> {
				Movie movie = restTemplate.getForObject("http://movie-info-service/movies/foo" + rating.getMovieId(), Movie.class);
				//put them all together
				return new CatalogItem(movie.getName(),"Desc", rating.getRating());
		})
		.collect(Collectors.toList());	
}
}

//
