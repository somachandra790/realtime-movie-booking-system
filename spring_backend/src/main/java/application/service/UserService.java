package application.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import application.model.Movie;
import application.model.User;
import application.model.WatchedMovie;
import application.repository.MovieRepository;
import application.repository.UserRepository;

@Service
public class UserService implements CRUDService<User> {

	private UserRepository userRepository;
	private MovieRepository movieRepository;
	
	@Autowired
	public UserService(UserRepository userRepository, MovieRepository movieRepository) {
		this.userRepository = userRepository;
		this.movieRepository = movieRepository;
	}

	public List<User> getAll() {
		
		List<User> users = this.userRepository.findAll();
		
		return users;
	}

	public User getById(Long id) {

		Optional<User> userOption = this.userRepository.findById(id);
		if(userOption.isPresent()) {
			return userOption.get();
		}
		
		return null;
	}

	public User save(User object) {
		
		User savedUser = this.userRepository.save(object);
		
		return savedUser;
	}

	public User update(User object) {
		

		if(object.getUserInfo().getPassword() == null) {
			object.getUserInfo().setPassword(this.userRepository.findById(object.getId()).get().getUserInfo().getPassword());
		}
		
		if(object.getImage() == null) {
			object.setImage(this.userRepository.getUserImagePathById(object.getId()));
		}
		
		Optional<User> findUser = this.userRepository.findById(object.getId());
		if(findUser.isPresent()) {
			object = this.userRepository.save(object);
			return object;
		}
		
		return null;
	}

	public boolean delete(Long id) {
		
		Optional<User> findUser = this.userRepository.findById(id);
		if(findUser.isPresent()) {
			this.userRepository.deleteById(id);
			return true;
		}
		
		
		return false;
		
	}
	

	public byte[] getUserImage(Long id) throws Exception {
		
		if(this.userRepository.existsById(id) == false) {
			throw new Exception("User with id: " + id + " does not exists.");
		}
		
		String userImagePath = this.userRepository.getUserImagePathById(id);
		if(userImagePath == null || userImagePath.equals(null) || userImagePath.equals("") || userImagePath.length() == 0) {
			return null;
		}
		
		File userImageFile = new File(userImagePath);
		
		if(userImageFile.exists() == false) {
			throw new Exception("Image file: " + userImageFile.getPath() + " for User: " + id + " does not exist." );
		}
		
		return Files.readAllBytes(Paths.get(userImageFile.getAbsolutePath()));
		
	}
	
	public User saveUserImage(Long userId, MultipartFile file) throws Exception {
		

		List<String> allowedExtensions = Arrays.asList(".png", ".jpg");
		String extension = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."), file.getOriginalFilename().length());
		
		// If not allowed extension
		if(allowedExtensions.contains(extension) == false) {
			throw new Exception("Unsupporeted File extension: " + extension);
		}

		File imageFile = new File("./src/main/resources/images/users/" + file.getOriginalFilename());
		try {
			
			if(imageFile.exists()) {
				
				imageFile.delete();
				
			}

			imageFile.createNewFile();

			FileOutputStream fos = new FileOutputStream(imageFile);
			fos.write(file.getBytes());
			fos.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		Optional<User> user = this.userRepository.findById(userId);
		if(user.isPresent() == false) {
			throw new Exception("User with id: " + userId + " is not found.");
		}
		
		user.get().setImage(imageFile.getPath());
		

		
		this.userRepository.save(user.get());
		
		return user.get();
	}
	
	
	
	public List<Movie> getUserBookmarks(Long userId) {
		
		Optional<User> user = this.userRepository.findById(userId);
		if(user.isPresent() == false) {
			return null;
		}
		
		return user.get().getBookmarkedMovies();
		
	}
	
	public User addMovieToUserBookmarks(Long userId, Long movieId) throws Exception {
		
		Optional<User> user = this.userRepository.findById(userId);
		Optional<Movie> movie = this.movieRepository.findById(movieId);
		
		// User - NOT_FOUUND
		if(user.isPresent() == false) {
			throw new Exception("User with id: " + userId + " is not found.");
		}
		
		// Movie - NOT_FOUND
		if(movie.isPresent() == false) {
			throw new Exception("Movie with id: " + movieId + " is not found.");
		}
			
		user.get().addToBookmarks(movie.get());		// If movie is already in Users bookmark throws Exception().
		
		User updatedUser = this.update(user.get());
		
		return updatedUser;
	}
	
	public List<Movie> removeMovieToUserBookmarks(Long userId, Long movieId) throws Exception {
		
		//----------------------------------------------------------------------------------
		// Get User by ID
		//----------------------------------------------------------------------------------
		Optional<User> user = this.userRepository.findById(userId);
		if(user.isPresent() == false) {
			throw new Exception("User with id: " + userId + " is not found.");
		}
		//----------------------------------------------------------------------------------
		
		//----------------------------------------------------------------------------------
		// Get MovieBy ID
		//----------------------------------------------------------------------------------
		Optional<Movie> movie = this.movieRepository.findById(movieId);
		if(movie.isPresent() == false) {
			throw new Exception("Movie with id: " + movieId + " is not found.");
		}
		//----------------------------------------------------------------------------------
		
		// Remove Movie from User Bookmarks
		user.get().removeFromBookmarks(movie.get());
		
		// Update User
		User updatedUser = this.userRepository.save(user.get());
		
		return updatedUser.getBookmarkedMovies();
	}
	
	public List<Movie> getWatchedMovies(Long userId) {
		
		Optional<User> user = this.userRepository.findById(userId);
		if(user.isPresent() == false) {
			return null;
		}
		
		List<WatchedMovie> watchedMovies = user.get().getWatchedMovies();
		List<Movie> movies = new ArrayList<Movie>(watchedMovies.size());
		Movie movie = null;
		for (WatchedMovie watchedMovie : watchedMovies) {
			movie = watchedMovie.getMovie();
			movies.add(movie);
		}
		
		return movies;
		
	}
	
	
	public User addWatchedMovieToUser(Long userId, Long movieId) throws Exception {
		
		Optional<User> user = this.userRepository.findById(userId);
		if(user.isPresent() == false) {
			throw new Exception("User with id: " + userId + " is not found!");
		}
		
		Optional<Movie> movie = this.movieRepository.findById(movieId);
		if(movie.isPresent() == false) {
			throw new Exception("Movie with id: " + movieId + " is not found!");
		}
		
		user.get().addWatchedMovie(movie.get());
		User savedUser = this.userRepository.save(user.get());
		
		return savedUser;
	}
	
	public boolean checkEmail(String email) { 
		
		boolean exists = this.userRepository.existsUserByUserInfo_Email(email);
		return exists;
		
	}
	
	public User getUserByEmail(String email) {
		
		Optional<User> user = this.userRepository.findByUserInfo_Email(email);
		if(user.isPresent() == false) {
			return null;
		}
		
		return user.get();
		
	}
	
	
	public Boolean exists(User userDTO) {
		
		if(this.userRepository.existsById(userDTO.getId()) == true) {
			return true;
		}
		
		return false;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
