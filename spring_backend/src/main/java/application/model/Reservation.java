package application.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity(name = "reservation")
//@JsonDeserialize(using = ReservationDeserializer.class)
public class Reservation implements Serializable {

	private static final long serialVersionUID = 2384209627011782144L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	@JoinColumn(name = "Shows_id", referencedColumnName = "id")
	private Shows shows;

	@Column(name = "ticket_count")
	private Integer ticketCount;

	@Column(name = "total_price")
	private Float totalPrice;
	
	@Column(name = "reserved_seats")
	private Set<Integer> reservedSeats;
	
	@Column(name = "reservation_date")
	private Date reservationDate;
	
	public Reservation() {

	}

	public Reservation(User user, Shows Shows, Set<Integer> reservedSeats) {
		super();
		this.user = user;
		this.shows = Shows;
		this.reservedSeats = reservedSeats;
		this.ticketCount = reservedSeats.size();
		this.totalPrice = this.shows.getPrice() * ticketCount.floatValue();
		
	}
	
	public Reservation(User user, Shows Shows, Integer ticketCount, Float totalPrice, Set<Integer> reservedSeats, Date reservationDate) {
		super();
		this.user = user;
		this.shows = Shows;
		this.ticketCount = ticketCount;
		this.totalPrice = totalPrice;
		this.reservedSeats = reservedSeats;
		this.reservationDate = reservationDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Shows getShows() {
		return shows;
	}

	public void setShows(Shows shows) {
		this.shows = shows;
	}

	public Long getId() {
		return id;
	}

	public void setTicketCount(Integer ticketCount) {
		this.ticketCount = ticketCount;
	}

	public Integer getTicketCount() {
		return ticketCount;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setReservedSeats(Set<Integer> reservedSeats) {
		this.reservedSeats = reservedSeats;
	}
	
	public Set<Integer> getReservedSeats() {
		return reservedSeats;
	}
	
	public Date getReservationDate() {
		return reservationDate;
	}
	
	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}
	
	public void reserveSeat(Integer seatNumber) throws Exception {
		
		if(seatNumber < 1 || seatNumber > (this.shows.getScreen().getColumns() * this.shows.getScreen().getRows())) {
			throw new Exception("Wrong seat number: " + seatNumber + " Seat number must be between 1 and " + (this.shows.getScreen().getColumns() * this.shows.getScreen().getRows()));
		}
		
		if(this.reservedSeats.contains(seatNumber)) {
			throw new Exception("Seat number: " + seatNumber + " is selected reserved.");
		}

		this.reservedSeats.add(seatNumber);
		
		this.ticketCount = this.reservedSeats.size();
		this.totalPrice = this.getShows().getPrice() * this.ticketCount;
		
	}
	
	@Override
	public String toString() {
		return "Reservation [id=" + id + ", ticketCount=" + ticketCount + ", totalPrice=" + totalPrice + ", reservedSeats=" + reservedSeats + ", reservationDate: " + reservationDate + "]";
	}

}
