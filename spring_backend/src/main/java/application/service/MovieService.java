package application.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import application.model.Movie;
import application.repository.MovieRepository;

@Service
public class MovieService implements CRUDService<Movie> {

	private MovieRepository movieRepository;

	@Autowired
	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	public List<Movie> getAll() {
		
		List<Movie> movies = this.movieRepository.findAll();
		return movies;
	}

	public Movie getById(Long id) {

		Optional<Movie> movieOptional = this.movieRepository.findById(id);
		if(movieOptional.isPresent()) {
			return movieOptional.get();
		}
		
		return null;
	}

	public Movie save(Movie object) {

		Movie savedMovie = this.movieRepository.save(object);
		return savedMovie;
	}

	public Movie update(Movie object) {
		
		Optional<Movie> movieOptional = this.movieRepository.findById(object.getId());
		if(movieOptional.isPresent()) {
			Movie savedMovie = this.movieRepository.save(object);
			return savedMovie;
		}
		return null;
	}
	
	@Deprecated
	public boolean delete(Long id) {
		
		return false;
	}
	

	public byte[] getMovieImage(Long id) throws Exception {
		
		// If Movie with id Exists in Database load its image path
		if(this.movieRepository.existsById(id) == false) {
			throw new Exception("Movie with id: " + id + " does not exists.");
		}
		
		String imagePath = this.movieRepository.getMovieImagePathById(id);
		
		if(imagePath == null || imagePath.equals("") || imagePath.length() == 0) {
			return null;
		}
		
		File imageFile = new File(imagePath);
		// If Image File does not Exist
		if(imageFile.exists() == false) {
			throw new Exception("Image file: " + imageFile.getPath() + " for movie with id: " + id + " is not found." );
		}
		
		// If Image File Exist
		return Files.readAllBytes(Paths.get(imageFile.getAbsolutePath()));
		
	}
	

	public Movie getRecommendedMovie() {
		
		List<Movie> movieList = this.movieRepository.findTop3ByOrderByRatingDesc();
		Random random = new Random();
		Movie randomMovie = movieList.get(random.nextInt(3));
		
		return randomMovie;
	}
	
}
