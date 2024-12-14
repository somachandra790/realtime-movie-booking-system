package application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.Comment;
import application.model.Movie;
import application.model.User;
import application.repository.CommentRepository;
import application.repository.MovieRepository;
import application.repository.UserRepository;

@Service
public class CommentService implements CRUDService<Comment> {

	private CommentRepository commentRepository;
	private MovieRepository movieRepository;
	private UserRepository userRepository;
	
	@Autowired
	public CommentService(CommentRepository commentRepository, MovieRepository movieRepository, UserRepository userRepository) {
		this.commentRepository = commentRepository;
		this.movieRepository = movieRepository;
		this.userRepository = userRepository;
	}

	public List<Comment> getAll() {
		
		List<Comment> comments = this.commentRepository.findAll();
		return comments;
	}

	public Comment getById(Long id) {
		
		Optional<Comment> comment = this.commentRepository.findById(id);
		if(comment.isPresent() == false) {
			return null;
		}
		
		return comment.get();
	}

	public Comment save(Comment object) throws Exception {
		
		Integer commentRating = object.getRating();
		
		// Movie with id not fond - NOT_FOUND
		if(this.movieRepository.existsById(object.getMovie().getId()) == false) {
			return null;
		}

		Long commentCount = this.commentRepository.countByMovie(object.getMovie());
		
		if(commentCount == 0) {
			object.getMovie().setRating(commentRating.floatValue());
		}
		else if(commentCount > 0) {
			System.out.println(object.getMovie().getRating().floatValue() + " + " + commentRating.floatValue() + " / " + commentCount.floatValue() + " + " + 1f );
			object.getMovie().setRating((object.getMovie().getRating().floatValue() + commentRating.floatValue()) / 2f);
		}
		
		String movieImagePath = this.movieRepository.getMovieImagePathById(object.getMovie().getId());
		
		object.getMovie().setImage(movieImagePath);
		
		
		this.movieRepository.save(object.getMovie());
		
		Comment comment = this.commentRepository.save(object);
		return comment;
	}

	public Comment update(Comment object) throws Exception {
		
		Optional<Comment> comment = this.commentRepository.findById(object.getId());
		if(comment.isPresent() == false) {
			return null;
		}
		
		Comment savedComment = this.commentRepository.save(object);
		
		return savedComment;
	}

	public boolean delete(Long id) {

		if(this.commentRepository.existsById(id) == false) {
			return false;
		}
		
		this.commentRepository.deleteById(id);
		
		return true;
	}
	

	public List<Comment> getMovieComments(Long movieId) throws Exception {
		
		Optional<Movie> movie = this.movieRepository.findById(movieId); 
		if(movie.isPresent() == false) {
			throw new Exception("Movie with id: " + movieId + " is not found.");
		}
		
		List<Comment> movieComments = this.commentRepository.findByMovie(movie.get());
		return movieComments;
	}
	
	public Long getMovieCommentsCount(Long movieId) {
		
		// Movie not Exists - NOT_FOUND
		Optional<Movie> movie = this.movieRepository.findById(movieId);
		if(movie.isPresent() == false) {
			return null;		// Movie with id not exists.
		}
		
		// Movie Exists - FOUND
		Long movieCommentsCount = this.commentRepository.countByMovie(movie.get());

		return movieCommentsCount;
	}
	
	public List<Comment> getUserComments(Long userId) {
		
		Optional<User> user = this.userRepository.findById(userId);
		if(user.isPresent() == false) {
			return null;
		}
		List<Comment> userComments = this.commentRepository.findByUser(user.get());
		
		return userComments;
	}
	
}

