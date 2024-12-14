package application.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.Shows;
import application.model.Reservation;
import application.model.Screen;
import application.model.User;
import application.repository.ShowsRepository;
import application.repository.ReservationRepository;
import application.repository.ScreenRepository;
import application.repository.UserRepository;

@Service
public class ReservationService implements CRUDService<Reservation> {

	private ReservationRepository reservationRepository;
	private UserRepository userRepository;
	private ShowsRepository showsRepository;
	private ScreenRepository screenRepository;
	
	@Autowired
	public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, ShowsRepository showsRepository, ScreenRepository screenRepository) {
		this.reservationRepository = reservationRepository;
		this.userRepository = userRepository;
		this.showsRepository = showsRepository;
		this.screenRepository = screenRepository;
	}

	public List<Reservation> getAll() {
		
		List<Reservation> reservations = this.reservationRepository.findAll();
		return reservations;
	}

	public Reservation getById(Long id) {

		Optional<Reservation> reservation = this.reservationRepository.findById(id);
		if(reservation.isPresent() == false) {
			return null;
		}
		
		return reservation.get();
	}

	public Reservation save(Reservation object) throws Exception {
		
		// Shows Check
		Shows shows = object.getShows();
		if(shows == null) {
			throw new Exception("Reservation: " + object.getId() + " shows is null.");
		}
		
		// Get All Shows Reservations and Check Seats
		List<Reservation> showsReservations = this.reservationRepository.findByShows(shows);
		if(showsReservations.isEmpty() || showsReservations == null) {
			showsReservations = new ArrayList<Reservation>();
		}
		
		// Here are now all reserved seats
		Set<Integer> totalReservedSeats = new HashSet<Integer>();
		for (Reservation showsReservation : showsReservations) {
			totalReservedSeats.addAll(showsReservation.getReservedSeats());
		}
		

		for (Integer seat : object.getReservedSeats()) {
			if(totalReservedSeats.contains(seat)) {
				throw new Exception("Seat with number: " + seat + " is reserved.");
			}
			if(seat < 1 || seat > (object.getShows().getScreen().getColumns() * object.getShows().getScreen().getRows())) {
				throw new Exception("Wrong Seat number: " + seat + " seat number must be between 1 and " + (object.getShows().getScreen().getColumns() * object.getShows().getScreen().getRows()));
			}
		}
		
		object.setTicketCount(object.getReservedSeats().size());
		object.setTotalPrice(object.getShows().getPrice() * object.getTicketCount());
		
		// If All seats passed check - save Reservation
		Reservation savedReservation = this.reservationRepository.save(object);
		
		return savedReservation;
	}
	

	@Deprecated
	public Reservation updateDelete(Reservation reservation) {
		reservation = this.reservationRepository.save(reservation);
		return reservation;
	}
	
	public Reservation update(Reservation object) throws Exception {

		Optional<Reservation> reservation = this.reservationRepository.findById(object.getId());
		if(reservation.isPresent() == false) {
			return null;
		}
		
		Reservation updatedReservation = this.save(object);
		
		return updatedReservation;
	}

	public boolean delete(Long id) {

		if(this.reservationRepository.existsById(id) == false) {
			return false;
		}
		
		this.reservationRepository.deleteById(id);
		
		return true;
	}

	public List<Reservation> getUserReservations(Long userId) {
		
		Optional<User> user = this.userRepository.findById(userId);
		if(user.isPresent() == false) {
			return null;
		}
		
		List<Reservation> userReservations = this.reservationRepository.findByUser(user.get());
		return userReservations;
		
	}
	
	public List<Reservation> findShowsReservations(Long showsId) throws Exception {
		
		Optional<Shows> shows = this.showsRepository.findById(showsId);
		if(shows.isPresent() == false) {
			throw new Exception("Shows with id: " + showsId + " is not found");
		}
		
		List<Reservation> projectonReservationsList = this.reservationRepository.findByShows(shows.get());
		
		return projectonReservationsList;
		
		
	}
	

	public List<Integer> getShowsReservationSeats(Long showsId) {
		
		Optional<Shows> shows = this.showsRepository.findById(showsId);
		
		// If Shows is not found
		if(shows.isPresent() == false) {
			return null;
		}
		
		// Get All Reservations for Shows
		List<Reservation> showsReservations = this.reservationRepository.findByShows(shows.get());
		List<Integer> showsReservationSeats = new ArrayList<Integer>();
		
		for (Reservation reservation : showsReservations) {
			showsReservationSeats.addAll(reservation.getReservedSeats());
		}
		
		return showsReservationSeats;
		
	}
	
	
	public List<Reservation> getScreenReservations(Long screenId) {
		
		Optional<Screen> screen = this.screenRepository.findById(screenId);
		if(screen.isPresent() == false) {
			return null;
		}
		
		List<Shows> screenShows = this.showsRepository.findByScreen(screen.get());
		List<Reservation> screensReservations = new ArrayList<Reservation>();
		for (Shows shows : screenShows) {
			
			try {
				
				List<Reservation> showsReservations = this.findShowsReservations(shows.getId());				
				screensReservations.addAll(showsReservations);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return screensReservations;			
	}
	
}
