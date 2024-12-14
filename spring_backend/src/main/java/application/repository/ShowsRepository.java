package application.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Movie;
import application.model.Shows;
import application.model.Screen;
import application.other.Day;

public interface ShowsRepository extends JpaRepository<Shows, Long> {

	public List<Shows> findByScreenAndDate_Day(Screen screen, Day day);
	
	public List<Shows> findByMovie(Movie movie);
	
	public List<Shows> findByScreen(Screen screen);
	
}
