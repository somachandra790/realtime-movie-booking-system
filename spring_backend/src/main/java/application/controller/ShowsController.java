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
import application.configuration.exceptions.ShowsOverlapException;
import application.model.Shows;
import application.service.ShowsService;

@Controller
@RequestMapping(value = "/shows")
public class ShowsController implements CRUDController<Shows> {

	private ShowsService showsService;

	@Autowired
	public ShowsController(ShowsService showsService) {
		this.showsService = showsService;
	}
	
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll() {
		
		List<Shows> shows = this.showsService.getAll();
		return new ResponseEntity<List<Shows>>(shows, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getById(@PathVariable("id") Long id) {

		Shows shows = this.showsService.getById(id);
		if(shows == null) {
			return new ResponseEntity<String>("Shows with id: " + id + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Shows>(shows, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody Shows shows) {
		
		try {
			
			Shows savedShows = this.showsService.save(shows);
			
			return new ResponseEntity<Shows>(savedShows, HttpStatus.OK);
			
		} catch (ShowsOverlapException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<String>("" + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
		
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Shows shows) {
		
		@SuppressWarnings("deprecation")
		Shows updatedShows = this.showsService.update(shows);
		if(updatedShows == null) {
			return new ResponseEntity<String>("Shows with id: " + shows.getId() + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Shows>(updatedShows, HttpStatus.OK);
	}

	@RequestMapping(value = "/{showsId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("showsId") Long showsId) {
		
		boolean deleted = this.showsService.delete(showsId);
		if(deleted == false) {
			return new ResponseEntity<String>("Shows with id: " + showsId + " is not found.", HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	

	@RequestMapping(value = "/movie/{movieId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMovieShows(@PathVariable("movieId") Long movieId) {
		
		List<Shows> movieShows = this.showsService.getMovieShows(movieId);
		if(movieShows == null) {
			return new ResponseEntity<String>("Movie with id:" + movieId + " is not found.", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Shows>>(movieShows, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/screen/{screenId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getScreensShows(@PathVariable("screenId") Long screenId) {
		
		List<Shows> screensShows = this.showsService.getScreensShows(screenId);
		
		return new ResponseEntity<List<Shows>>(screensShows, HttpStatus.OK);
	}
	
}
