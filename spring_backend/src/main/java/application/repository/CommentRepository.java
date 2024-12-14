package application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import application.model.Comment;
import application.model.Movie;
import application.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	/**
	 * Find All Comment for specified Movie.
	 * 
	 * @param movie
	 * @return list of comments - for specified Movie.
	 */
	public List<Comment> findByMovie(Movie movie);
	
	
	/**
	 * Counts how many comments passed Movie have.
	 * 
	 * 
	 * @param movie
	 * @return count - number of Comments of Movie
	 */
	public Long countByMovie(Movie movie);
	
	
	/**
	 * Find Users Comments
	 * 
	 * @param user
	 * @return users comments
	 */
	public List<Comment> findByUser(User user);
	
	
}
