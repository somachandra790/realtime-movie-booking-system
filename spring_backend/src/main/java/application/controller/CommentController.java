package application.controller;

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
import application.model.Comment;
import application.service.CommentService;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {

	private CommentService commentService;
	
	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
		
		List<Comment> comments = this.commentService.getAll();
		
		return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
	}

	@RequestMapping(value = "/{commentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getById(@PathVariable("commentId") Long id) {

		Comment comment = this.commentService.getById(id);
		if(comment == null) {
			return new ResponseEntity<String>("Comment with id: " + id + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Comment>(comment, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody Comment object) {

		try {			
			
			Comment savedComment = this.commentService.save(object);
			
			
			return new ResponseEntity<Comment>(savedComment, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}	

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Comment object) {

		try {
			Comment updatedComment = this.commentService.update(object);
			if(updatedComment == null) {
				return new ResponseEntity<String>("Comment with id: " + object.getId() + " is not found.", HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<Comment>(updatedComment, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@RequestMapping(value = "/{commentId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("commentId") Long id) {
		
		boolean deleted = this.commentService.delete(id);
		if(deleted == false) {
			return new ResponseEntity<String>("Comment with id: " + id + " is not found.", HttpStatus.NOT_FOUND); 
		}
		
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}


	@RequestMapping(value = "/movie/{movieId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMovieComments(@PathVariable("movieId") Long movieId) {
		
		try {
		
			List<Comment> movieComments = this.commentService.getMovieComments(movieId);
			
			return new ResponseEntity<List<Comment>>(movieComments, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("" + e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(value = "/movie/count/{movieId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMovieCommetsCount(@PathVariable("movieId") Long movieId) {
		
		Long movieCommentCount = this.commentService.getMovieCommentsCount(movieId);
		if(movieCommentCount == null) {
			return new ResponseEntity<String>("Movie with id: " + movieId + " is not found", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Long>(movieCommentCount, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "/user/{userId}")
	public ResponseEntity<?> getUserComments(@PathVariable("userId") Long userId) {
		
		List<Comment> userComments = this.commentService.getUserComments(userId);
		if(userComments == null) {
			return new ResponseEntity<String>("User with id: " + userId + " is not found", HttpStatus.NOT_FOUND);
		}
			
		return new ResponseEntity<List<Comment>>(userComments, HttpStatus.OK);
		
	}
	
}
