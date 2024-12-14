package application.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import application.model.Movie;
import application.service.MovieService;

@CrossOrigin()
@Controller
@RequestMapping(value = "/movie")
public class MovieController implements CRUDController<Movie> {

	private MovieService movieService;

	@Autowired
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
		
		List<Movie> movies = this.movieService.getAll();
		return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getById(@PathVariable("id") Long id) {

		Movie movie = this.movieService.getById(id);
		if(movie == null) {
			return new ResponseEntity<String>("Movie with id " + id + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Movie>(movie, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody Movie object) {
		
		Movie movie = this.movieService.save(object);
		return new ResponseEntity<Movie>(movie, HttpStatus.OK);
	}

	
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Movie object) {
		
		Movie movie = this.movieService.update(object);
		if(movie == null) {
			return new ResponseEntity<String>("Movie with id " + object.getId() + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Movie>(movie, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		
		return null;
	}

	@RequestMapping(value = "/image/{movieId}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<?> getMovieImage(@PathVariable("movieId") Long id) {
		
		try {
			byte[] imageData = this.movieService.getMovieImage(id);
			return new ResponseEntity<byte[]>(imageData, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	@RequestMapping(value = "/recommend", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRecommendedMovie() {
		
		Movie randomMovie = this.movieService.getRecommendedMovie();
		
		return new ResponseEntity<Movie>(randomMovie, HttpStatus.OK);
	}
	
	
}
