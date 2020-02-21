package io.codingsource.moviecatalogservice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.CatalogItem;
import model.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private MovieInfo movieInfo;

	@Autowired
	private UserRatingInfo userRatingInfo;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

		UserRating ratings = userRatingInfo.getUserRating(userId);
		// For each movie id, call movie info service and get details
		return ratings.getUserRatings().stream().
				map(rating -> movieInfo.getMovieInfo(rating))
				.collect(Collectors.toList());
	}

}

//
