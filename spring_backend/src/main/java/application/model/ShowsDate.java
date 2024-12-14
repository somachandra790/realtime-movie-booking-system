package application.model;

import java.io.Serializable;
import application.configuration.Util;
import application.other.Day;
import jakarta.persistence.Embeddable;

@Embeddable
public class ShowsDate implements Serializable, Comparable<ShowsDate> {

	private static final long serialVersionUID = 6533859460399769737L;
	
	private Day day;
	private Integer hours;
	private Integer minutes;

	public ShowsDate() {

	}

	public ShowsDate(Day day, Integer hours, Integer minutes) {
		super();
		
		try {
			
			if(hours > 23 && hours < 0) {
				throw new Exception("Wrong hours range, hourse range from 0 - 23: " + hours);
			}
			
			if(minutes > 59 && minutes < 0) {
				throw new Exception("Wrong minute range, minute range from 0 - 59: " + minutes);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		this.day = day;
		this.hours = hours;
		this.minutes = minutes;
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}
	
	public boolean equals(ShowsDate showsDate) {
		
		if(showsDate == null) {
			return false;
		}
		
		if(this.day == showsDate.getDay() && this.hours == showsDate.getHours() && this.minutes == showsDate.getMinutes()) {
			return true;
		}
		
		return false;
		
	}
	
	public int compareTo(ShowsDate showsDate) {
		
		int thisDayNumber = -1;
		int thatDayNumber = -1;
		try {
			thisDayNumber = Util.getDayNumber(this.getDay());
			thatDayNumber = Util.getDayNumber(showsDate.getDay());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(thisDayNumber < thatDayNumber) {
			return -1;
		}
		
		if(thisDayNumber > thatDayNumber) {
			return 1;
		}
		
		// If ShowsDate Days are equals then we watch hours and minutes
		if(thisDayNumber == thatDayNumber) {
			
			if(this.getHours() < showsDate.getHours()) {
				return -1;
			}
			
			if(this.getHours() > showsDate.getHours()) {
				return 1;
			}
			
			if(this.getHours() == showsDate.getHours()) {
				if(this.getMinutes() < showsDate.getMinutes()) {
					return -1;
				}
				else if(this.getMinutes() > showsDate.getMinutes()) {
					return 1;
				}
				else if(this.getMinutes() == showsDate.getMinutes()) {
					return 0;
				}
			}
		}
		
		return 0;
	}
	
	
	
	@Override
	public String toString() {
		return "ShowsDate [day=" + day + ", hours=" + hours + ", minutes=" + minutes + "]";
	}

}
