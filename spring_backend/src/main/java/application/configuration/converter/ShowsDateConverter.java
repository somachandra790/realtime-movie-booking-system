package application.configuration.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.model.ShowsDate;
import jakarta.persistence.AttributeConverter;

public class ShowsDateConverter implements AttributeConverter<ShowsDate, String>{

	public String convertToDatabaseColumn(ShowsDate attribute) {
		
		if(attribute == null) {
			return null;
		}
		
		try {
			return new ObjectMapper().writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public ShowsDate convertToEntityAttribute(String dbData) {

		if(dbData == null) {
			return null;
		}
		
		try {
			
			return new ObjectMapper().readValue(dbData, ShowsDateConverter.typeReference());
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		
		

	}
	
	private static final TypeReference<ShowsDate> typeReference() {
		return new TypeReference<ShowsDate>() { };
	}
	
}
