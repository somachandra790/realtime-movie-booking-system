package application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.model.Screen;
import application.service.ScreenService;

@RestController
@RequestMapping("/screen")
public class ScreenController implements CRUDController<Screen>{

	private ScreenService screeneService;
	
	@Autowired
	public ScreenController(ScreenService screeneService) {
		this.screeneService = screeneService;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<?> getAll() {
		
		List<Screen> screens = this.screeneService.getAll();
		return new ResponseEntity<List<Screen>>(screens, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{screenId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<?> getById(@PathVariable("screenId") Long id) {
		
		Screen screen = this.screeneService.getById(id);
		if(screen == null) {
			return new ResponseEntity<String>("Screen with id: " + id + " is not Found!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Screen>(screen, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<?> save(@RequestBody Screen object) {
		
		Screen savedScreen = null;
		try {
			savedScreen = this.screeneService.save(object);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Screen>(savedScreen, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<?> update(@RequestBody Screen object) {
		
		Screen updatedScreen = null;
		try {
			updatedScreen = this.screeneService.update(object);
			
			if(updatedScreen == null) {
				return new ResponseEntity<String>("Screen with id: " + object.getId() + " is not found!", HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Screen>(updatedScreen, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{screenId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<?> delete(@PathVariable("screenId") Long id) {
		
		this.screeneService.delete(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	
}
