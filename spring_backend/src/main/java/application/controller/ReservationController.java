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
import application.model.Reservation;
import application.service.ReservationService;

@Controller
@RequestMapping(value = "/reservation")
public class ReservationController implements CRUDController<Reservation> {

	private ReservationService reservationService;

	@Autowired
	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
		
		List<Reservation> reservations = this.reservationService.getAll();
		
		return new ResponseEntity<List<Reservation>>(reservations,HttpStatus.OK);
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getById(@PathVariable("userId") Long id) {
		
		Reservation reservation = this.reservationService.getById(id);
		if(reservation == null) {
			return new ResponseEntity<String>("No Reservation with id: " + id, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
		
	}

	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody Reservation object) {
		
		System.out.println(object);
		
		Reservation savedReservation;
		try {
			savedReservation = this.reservationService.save(object);
			
			return new ResponseEntity<Reservation>(savedReservation, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("" + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
		
	}

	
	@RequestMapping(method = RequestMethod.PUT, consumes =  MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Reservation object) {

		
		try {
		
			Reservation updatedReservation = this.reservationService.update(object);
			if(updatedReservation == null) {
				return new ResponseEntity<String>("No Reservation with id: " + object.getId(), HttpStatus.NOT_FOUND); 
			}
			
			return new ResponseEntity<Reservation>(updatedReservation, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("" + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
		
		
		
	}

	@RequestMapping(value = "/{reservationId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("reservationId") Long id) {
		
		boolean deleted = this.reservationService.delete(id);
		
		if(deleted == false) {
			return new ResponseEntity<String>("No Reservation with id: " + id, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserReservations(@PathVariable("userId") Long userId) {
		
		List<Reservation> userReservations = this.reservationService.getUserReservations(userId);
		if(userReservations == null) {
			return new ResponseEntity<String>("User with id: " + userId + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Reservation>>(userReservations, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/shows/{showsId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findShowsReservations(@PathVariable("showsId") Long showsId) {
		
		try {
			
			List<Reservation> showsReservations = this.reservationService.findShowsReservations(showsId);
			
			return new ResponseEntity<List<Reservation>>(showsReservations, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("" + e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	@RequestMapping(value = "/shows/reserved-seats/{showsId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getShowsReservedSeats(@PathVariable("showsId") Long showsId) {
		
		List<Integer> showsReservedSeats = this.reservationService.getShowsReservationSeats(showsId);
		if(showsReservedSeats == null) {
			return new ResponseEntity<String>("Shows with id: " + showsId + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Integer>>(showsReservedSeats, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/screen/{screenId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getScreensReservatons(@PathVariable("screenId") Long screenId) {
		
		List<Reservation> screenReservations = this.reservationService.getScreenReservations(screenId);
		
		return new ResponseEntity<List<Reservation>>(screenReservations, HttpStatus.OK);
		
	}
	
	
}
