package application.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import application.configuration.jwt_security.UserInfo;
import application.configuration.jwt_security.UserInfoService;
import application.model.Movie;
import application.model.User;
import application.service.UserService;


@Controller
@RequestMapping(value = "/user")
public class UserController {

	private UserService userService;
	private UserInfoService userInfoService; 
	
	@Autowired
	public UserController(UserService userService, UserInfoService userInfoService) {
		this.userService = userService;
		this.userInfoService = userInfoService;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
		
		List<User> usersDTO = this.userService.getAll();
		return new ResponseEntity<List<User>>(usersDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getById(@PathVariable("id") Long id) {

		User userDTO = this.userService.getById(id);
		if(userDTO == null) {
			return new ResponseEntity<String>("User with id " + id + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<User>(userDTO, HttpStatus.OK);
	}

	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody User user) {
		
		try {
			
			// Add UserInfo to Database
			UserInfo userInfo = user.getUserInfo();
			userInfo = userInfoService.addUser(userInfo);
			
			// Add User to Database
			user.setUserInfo(userInfo);
			user = this.userService.save(user);
		
			return new ResponseEntity<User>(user, HttpStatus.OK);
			
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("E-mail already exist.", HttpStatus.NOT_ACCEPTABLE);
		} 

		
		
	}
	
	// If I change E-mail i will need to get new JWT Token
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody User object) {
		
		
		
		UserInfo userInfo = object.getUserInfo();
		User userDTO = object;
		
		// If UserInfo is not Found
		if(this.userInfoService.exists(userInfo) == false) {
			return new ResponseEntity<String>("UserInfo id: " + object.getUserInfo().getId() + " not found.", HttpStatus.NOT_FOUND);
		}
		
		// If User is not Found
		if(this.userService.exists(userDTO) == null) {
			return new ResponseEntity<String>("User id: " + object.getId() + " not found.", HttpStatus.NOT_FOUND);
		}
		
		//-----------------------------------------------------------
		// UserInfoSave
		//-----------------------------------------------------------
		// If UserInfo object does not have User Password, load User Password from Database
		if(userInfo.getPassword() == null) {
			// Save without Hashing Password
			String userHashedPassword = this.userInfoService.getUserInfoPassword(userInfo);
			if(userHashedPassword == null) {
				throw new InternalError("userHashedPassword: " + userHashedPassword);
			}
			userInfo.setPassword(userHashedPassword);
			userInfo = this.userInfoService.addUserNoHashPassword(userInfo);
		}
		// If UserInfo Object has password, save normally UserInfo object into Database
		else {
			// Password is not hashed here, password is in Raw Format
			try {
				userInfo = this.userInfoService.addUser(userInfo);
			} catch (SQLIntegrityConstraintViolationException e) {
				e.printStackTrace();
			}
		}
		//-----------------------------------------------------------
		
		
		//-----------------------------------------------------------
		// User - save
		//-----------------------------------------------------------
		userDTO.setUserInfo(userInfo);
		userDTO = this.userService.update(userDTO);
		//-----------------------------------------------------------
		
		return new ResponseEntity<User>(userDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {

		boolean delete = this.userService.delete(id);
		if(delete == false) { 
			return new ResponseEntity<String>("User with id " + id + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	

	@RequestMapping(value = "/image/{userId}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<?> getUserImage(@PathVariable("userId") Long id) {
		
		try {
			
			byte[] userImageData = this.userService.getUserImage(id);
			return new ResponseEntity<byte[]>(userImageData, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		

	}
	
	
	@RequestMapping(value = "/bookmark/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserBookmarks(@PathVariable("userId") Long userId) {
		
		List<Movie> bookmarkedMovies = this.userService.getUserBookmarks(userId);
		if(bookmarkedMovies == null) {
			return new ResponseEntity<String>("User with id: " + userId + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		
		return new ResponseEntity<List<Movie>>(bookmarkedMovies, HttpStatus.OK);
	}
	
	// First need to get JTW Token from URL: POST http://localhost:8080/auth/generateToken
	
	@RequestMapping(value = "/bookmark/movie/{userId}/{movieId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addMovieToUserBookmarks(@PathVariable("userId") Long userId, @PathVariable("movieId") Long movieId) {
		
		try {
			
			User user = this.userService.addMovieToUserBookmarks(userId, movieId);		// Returned Updated User Object with added Movie with its Bookmark.
			return new ResponseEntity<User>(user, HttpStatus.OK);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("" + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
	
	@RequestMapping(value = "/bookmark/movie/{userId}/{movieId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> removeMovieToUserBookmarks(@PathVariable("userId") Long userId, @PathVariable("movieId") Long movieId) {
		
		try {
			
			List<Movie> bookmarkedMovies = this.userService.removeMovieToUserBookmarks(userId, movieId);
			return new ResponseEntity<List<Movie>>(bookmarkedMovies, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("" + e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}
	
	@RequestMapping(value = "/watched/{userId}")
	public ResponseEntity<?> getUserWatchedMovies(@PathVariable("userId") Long userId) {
		
		List<Movie> watchedMovies = this.userService.getWatchedMovies(userId);
		if(watchedMovies == null) {
			return new ResponseEntity<String>("User with id: " + userId + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Movie>>(watchedMovies, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/watched/{userId}/{movieId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addWatchedMovieToUser(@PathVariable("userId") Long userId, @PathVariable("movieId") Long movieId) {
		
		User user;
		try {
			user = this.userService.addWatchedMovieToUser(userId, movieId);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("" + e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/check-email", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> checkEmail(@RequestBody(required = true) String emailJson) {
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(emailJson);
			
			String email = jsonNode.get("email").asText();
			
			Boolean emailExists = this.userService.checkEmail(email);
			
			return new ResponseEntity<Boolean>(emailExists, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	@RequestMapping(value = "/email/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email) {
		
		User user = this.userService.getUserByEmail(email);
		if(user == null) {
			return new ResponseEntity<String>("User with E-mail: " + email + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/image/{userId}", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadUserImage(@PathVariable("userId") Long userId, @RequestParam("image") MultipartFile file) {
		
		
		try {
			
			User user = this.userService.saveUserImage(userId, file);	
			return new ResponseEntity<User>(user, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("" + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
	
}
