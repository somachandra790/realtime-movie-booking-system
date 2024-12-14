package application.configuration.deserializer;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.model.Comment;
import application.model.Movie;
import application.model.User;
import application.service.MovieService;
import application.service.UserService;

public class CommentDeserializer extends JsonDeserializer<Comment> {
	
	private UserService userService;
	private MovieService movieService;

	@Autowired
	public CommentDeserializer(UserService userService, MovieService movieService) {
		this.userService = userService;
		this.movieService = movieService;
	}
	
	@Override
	public Comment deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = p.getCodec().readTree(p);
		
		System.out.println("test");
		System.out.println(node.toPrettyString());
		
		User user = mapper.treeToValue(node.get("_user"), User.class);
		Movie movie = mapper.treeToValue(node.get("_movie"), Movie.class);
		
		System.out.println(user);
		
		user.setImage(this.userService.getById(user.getId()).getImage());
		movie.setImage(this.movieService.getById(movie.getId()).getImage());
		
		Date commentDate = mapper.treeToValue(node.get("_date"), Date.class);
		Integer rating = node.get("_rating").asInt();
		String commentText = node.get("_commentText").asText();
		
		Comment comment = new Comment(user, movie, commentDate, rating, commentText);
		
		return comment;
	}
	
}
