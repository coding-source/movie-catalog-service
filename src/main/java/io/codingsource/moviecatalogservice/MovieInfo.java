package io.codingsource.moviecatalogservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import model.CatalogItem;
import model.Movie;
import model.Rating;

@Service
public class MovieInfo {

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
	public CatalogItem getMovieInfo(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/foo" + rating.getMovieId(),
				Movie.class);
		// put them all together
		return new CatalogItem(movie.getName(), "Desc", rating.getRating());
	}

	public CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem("movie name not found", "Desc", rating.getRating());
	}

}
