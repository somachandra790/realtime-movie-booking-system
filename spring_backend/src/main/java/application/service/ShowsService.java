package application.service;

import java.util.List; 
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import application.configuration.exceptions.ShowsOverlapException;
import application.model.Movie;
import application.model.Shows;
import application.model.Reservation;
import application.model.Screen;
import application.repository.MovieRepository;
import application.repository.ShowsRepository;
import application.repository.ScreenRepository;

@Service
public class ShowsService {

	private ShowsRepository showsRepository;
	private MovieRepository movieRepository;
	private ScreenRepository screenRepository;
	
	private ReservationService reservationService;
	
	@Autowired
	public ShowsService(ShowsRepository showsRepository, ReservationService reservationService, MovieRepository movieRepository, ScreenRepository screenRepository) {
		this.showsRepository = showsRepository;
		this.movieRepository = movieRepository;
		this.reservationService = reservationService;
		this.screenRepository = screenRepository;
	}

	public List<Shows> getAll() {
		
		List<Shows> Shows = this.showsRepository.findAll();
		return Shows;
	}

	public Shows getById(Long id) {
		
		Optional<Shows> shows = this.showsRepository.findById(id);
		if(shows.isPresent() == false) {
			return null;
		}
		
		return shows.get();
	}

	public Shows save(Shows object) throws ShowsOverlapException {
		
		// Get All Shows that are same Day and Same Screen as Object
		List<Shows> shows = this.showsRepository.findByScreenAndDate_Day(object.getScreen(), object.getShowsDate().getDay());
		for (Shows show : shows) {
			if(object.showsDateOverlap(show)) {
				throw new ShowsOverlapException("Current Shows is overlapping Screen and Time with other Shows.\n\nCurrentShows:\nScreen: " + object.getScreen().getName() + "\nTime: " + object.getShowsDate() + "\n\nShows:\nScreen: " +  show.getScreen().getName()+ "\nTime: " + shows.getLast() + "\n\n\n");
			}
		}
		
		Shows show = this.showsRepository.save(object);
		return show;
	}

	@Deprecated
	public Shows update(Shows object) {
		
		if(this.movieRepository.existsById(object.getId()) == true) {
			try {
				object = this.save(object);
			} catch (ShowsOverlapException e) {
				e.printStackTrace();
			}
		}
		
		return object;
	}
	

	
	@SuppressWarnings("deprecation")
	public boolean delete(Long showsId) {
		
		Optional<Shows> shows = this.showsRepository.findById(showsId);
		
		if(shows.isPresent() == false) {
			return false;
		}
		

		try {
			List<Reservation> showsReservations = this.reservationService.findShowsReservations(showsId);
			for (Reservation reservation : showsReservations) {
				reservation.setShows(null);
				this.reservationService.updateDelete(reservation);
				this.reservationService.delete(reservation.getId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		shows.get().setMovie(null);
		shows.get().setScreen(null);
		
		try {
			this.save(shows.get());
		} catch (ShowsOverlapException e) {
			e.printStackTrace();
			return false;
		}
		//-------------------------------------------------
		
		this.showsRepository.deleteById(showsId);
		
		return true;
		
		
	}
	
	

	
	public List<Shows> getMovieShows(Long movieId) {
		
		
		Optional<Movie> movie = this.movieRepository.findById(movieId);
		if(movie.isPresent() == false) {
			return null;
		}
		
		List<Shows> movieShows = this.showsRepository.findByMovie(movie.get());
		
		return movieShows;
	}
	
	
	public List<Shows> getScreensShows(Long screenId) {
		
		// Get Screen
		Optional<Screen> screen = this.screenRepository.findById(screenId);
		if(screen.isPresent() == false) {
			return null;			// Screen Not Found
		}
		
		
		List<Shows> screensShows = this.showsRepository.findByScreen(screen.get());
		
		return screensShows;
	}
	
	
}
