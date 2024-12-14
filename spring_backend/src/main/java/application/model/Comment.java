package application.model;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "comment")
//@JsonDeserialize(using = CommentDeserializer.class)
public class Comment implements Serializable {

	private static final long serialVersionUID = 6855198274217429999L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "movie_id", referencedColumnName = "id")
	private Movie movie;

	@Column(name = "date")
	private Date date;

	@Column(name = "comment_rating")
	private Integer rating;

	@Column(name = "text", columnDefinition = "text")
	private String commentText;
	
	public Comment() {

	}
	
	public Comment(User user, Movie movie, Date date, Integer rating, String commentText) {
		super();
		this.user = user;
		this.movie = movie;
		this.date = date;
		this.rating = rating;
		this.commentText = commentText;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", user=" + user.getId() + ", movie=" + movie.getId() + ", date=" + date + ", rating=" + rating + ", commentText=" + commentText + "]";
	}

}
