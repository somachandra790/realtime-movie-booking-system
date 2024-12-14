package application.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import application.model.Shows;
import application.model.Reservation;
import application.model.User;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
	/**
	 * Get All User Reservations.
	 * 
	 * @param user
	 * @return users reservation
	 */
	public List<Reservation> findByUser(User user);
	
	
	/**
	 * Get All Reservation for some Shows.
	 * 
	 * @param shows
	 * @return reservations - all shows reservation
	 */
	public List<Reservation> findByShows(Shows shows);
	
	
	
}
