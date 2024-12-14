package application.model;

import java.io.Serializable;
import java.sql.Time;
import application.configuration.Util;
import application.configuration.converter.ShowsDateConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity(name = "Shows")
@NoArgsConstructor
@AllArgsConstructor
public class Shows implements Serializable {

	private static final long serialVersionUID = 9190947332245614029L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "movie_id")
	private Movie movie;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "screen_id")
	private Screen screen;

	@Column(name = "Shows_date", columnDefinition = "text")
	@Embedded
	@Convert(converter = ShowsDateConverter.class, attributeName = "date")
	private ShowsDate date;

	@Column(name = "price")
	private Float price;

	public Shows(Movie movie, Screen screen, ShowsDate date, Float price) {
		super();
		this.movie = movie;
		this.screen = screen;
		this.date = date;
		this.price = price;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public ShowsDate getShowsDate() {
		return date;
	}

	public void setShowsDate(ShowsDate date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}
	
	public Float getPrice() {
		return price;
	}
	
	public void setPrice(Float price) {
		this.price = price;
	}
	
	@SuppressWarnings("deprecation")
	public boolean showsDateOverlap(Shows projecton) {
		
		try {
			
			Screen thisScreen = this.getScreen();
			Screen thatScreen = projecton.getScreen();
			
			// If Screens are same there is no possible Overlap
			if(thisScreen.equals(thatScreen) == false) {
				return false;
			}
			
			// If Screens are same there is possibility for Overlap
			if(thisScreen.equals(thatScreen)) {
				
				int thisDay = Util.getDayNumber(this.getShowsDate().getDay());
				int thatDay = Util.getDayNumber(projecton.getShowsDate().getDay());
				
				// If Days are not same then there is no Overlapping
				if(thisDay != thatDay) {
					return false;
				}
				
				// If Day are same then there are possible Overlapping
				if(thisDay == thatDay) {
					
					Time thisStartTime = new Time(this.getShowsDate().getHours(), this.getShowsDate().getMinutes(), 0);
					Time thisEndTime = new Time(this.getShowsDate().getHours(), this.getShowsDate().getMinutes() + this.getMovie().getDuration(), 0);
					
					Time thatStartTime = new Time(projecton.getShowsDate().getHours(), projecton.getShowsDate().getMinutes(), 0);
					Time thatEndTime = new Time(projecton.getShowsDate().getHours(), projecton.getShowsDate().getMinutes() + projecton.getMovie().getDuration(), 0);
					
					boolean overlap = (Math.min(thisEndTime.getTime(), thatEndTime.getTime()) - Math.max(thisStartTime.getTime(), thatStartTime.getTime())) > 0; 
					return overlap;
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
	}

	@Override
	public String toString() {
		return "Shows [id=" + id + ", movie=" + movie + ", screen=" + screen + ", date=" + date + ", price=" + price + "]";
	}

}
