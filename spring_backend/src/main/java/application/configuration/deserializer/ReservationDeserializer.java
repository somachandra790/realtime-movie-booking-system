package application.configuration.deserializer;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import application.model.Shows;
import application.model.Reservation;
import application.model.User;
import application.repository.ShowsRepository;
import application.repository.UserRepository;

public class ReservationDeserializer extends JsonDeserializer<Reservation> {
	
	private UserRepository userRepository;
	private ShowsRepository showsRepository;
	
	@Autowired
	public ReservationDeserializer(UserRepository userRepository, ShowsRepository showsRepository) {
		this.userRepository = userRepository;
		this.showsRepository = showsRepository;
	}
	
	@SuppressWarnings({ "removal", "unchecked" })
	@Override
	public Reservation deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = p.getCodec().readTree(p);
			
//			System.out.println(node.toPrettyString());
			
			Optional<User> user = this.userRepository.findById(new Long(mapper.treeToValue(node.get("_user").get("id"), Long.class)));
			if(user.isPresent() == false) {
				throw new Exception("User with id: " + mapper.treeToValue(node.get("_user").get("id"), Long.class) + " is not found.");
			}
			
			Optional<Shows> shows = this.showsRepository.findById(new Long(mapper.treeToValue(node.get("_shows").get("id"), Long.class)));
			if(shows.isPresent() == false) {
				throw new Exception("Shows with id: " + mapper.treeToValue(node.get("_shows").get("id"), Long.class) + " is not found.");
			}
			
			System.out.println(user.get());
			System.out.println(shows.get());
			
			Integer ticketCount = mapper.treeToValue(node.get("_ticketCount"), Integer.class);
			Set<Integer> reservedSeats = mapper.treeToValue(node.get("_reservedSeats"), Set.class);
			Date reservationDate = mapper.treeToValue(node.get("_reservationDate"), Date.class);
			Float totalPrice = mapper.treeToValue(node.get("_totalPrice"), Float.class);	
			
			Reservation reservation = new Reservation(user.get(), shows.get(), ticketCount, totalPrice, reservedSeats, reservationDate);	

			return reservation;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
