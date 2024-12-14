package application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.Shows;
import application.model.Screen;
import application.repository.ScreenRepository;

@Service
public class ScreenService implements CRUDService<Screen> {

	private ScreenRepository screenRepository;
	private ShowsService ShowsService;
	
	@Autowired
	public ScreenService(ScreenRepository screenRepository, ShowsService ShowsService) {
		this.screenRepository = screenRepository;
		this.ShowsService = ShowsService;
	}

	@Override
	public List<Screen> getAll() {
		
		List<Screen> screens = this.screenRepository.findAll();
		return screens;
	}

	@Override
	public Screen getById(Long id) {

		Optional<Screen> screen = this.screenRepository.findById(id);
		if(screen.isPresent() == false) {
			return null;
		}
		
		return screen.get();
	}

	@Override
	public Screen save(Screen object) throws Exception {
		
		Screen savedScreen = this.screenRepository.save(object);
		return savedScreen;
	}

	@Override
	public Screen update(Screen object) throws Exception {
		
		if(this.screenRepository.existsById(object.getId()) == false) {
			return null;
		}
		
		Screen updatedScreen = this.screenRepository.save(object);

		return updatedScreen;
	}

	@Override
	public boolean delete(Long screenId) {
		
		// Get All Screens Shows
		List<Shows> screensShows = this.ShowsService.getScreensShows(screenId);
		
		for (Shows Shows : screensShows) {
			this.ShowsService.delete(Shows.getId());
		}
		
		this.screenRepository.deleteById(screenId);
		return true;
	}
	
	
	
}
