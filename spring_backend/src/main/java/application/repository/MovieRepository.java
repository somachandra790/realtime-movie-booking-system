package application.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import application.model.Movie;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	
	@Query("SELECT m.image FROM movie m WHERE m.id = :movieId")
	public String getMovieImagePathById(@Param("movieId") Long movieId);
	
	public List<Movie> findTop3ByOrderByRatingDesc();

}
