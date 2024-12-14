package application.configuration;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import application.configuration.jwt_security.UserInfo;
import application.configuration.jwt_security.UserInfoService;
import application.model.Movie;
import application.model.Shows;
import application.model.ShowsDate;
import application.model.Reservation;
import application.model.Screen;
import application.model.User;
import application.other.Day;
import application.other.MovieGenre;
import application.repository.MovieRepository;
import application.repository.ShowsRepository;
import application.repository.ReservationRepository;
import application.repository.ScreenRepository;
import application.repository.UserRepository;


@Component
public class ApplicationRunner implements CommandLineRunner {
	
	private UserRepository userRepository;
	private UserInfoService userInfoService;
	private MovieRepository movieRepository;
	private ScreenRepository screenRepository;
	private ShowsRepository showsRepository;
	private ReservationRepository reservationRepository;
//	private CommentRepository commentRepository;

	
	
	@Autowired
	public ApplicationRunner(UserRepository userRepository, UserInfoService userInfoService, MovieRepository movieRepository, ScreenRepository screenRepository, ShowsRepository showsRepository, ReservationRepository reservationRepository) {
		this.userRepository = userRepository;
		this.userInfoService = userInfoService;
		this.movieRepository = movieRepository;
		this.screenRepository = screenRepository;
		this.showsRepository = showsRepository;
		this.reservationRepository = reservationRepository;
	}
	
	public void run(String... args) throws Exception {

//		System.err.println(this.enableSpringSecurity);
		
//		this.initUsers();
//		this.initMovies();
//		this.initScreen();
//		this.initComments();
		
	}
	
	private void initUsers() {
		
		File userImageFolder = new File("./src/main/resources/images/users");
		
		// Create new Users
		UserInfo userInfo1 = new UserInfo("Pera", "Peric", "peraperic@gmail.com", "Test!123!", "ROLE_USER");
		UserInfo userInfo2 = new UserInfo("Pera", "Anic", "peraanic@gmail.com", "Test!123!", "ROLE_USER");
		UserInfo userInfo3 = new UserInfo("Ana", "Peric", "anaperic@gmail.com", "Test!123!", "ROLE_USER");
		
		try {
			userInfo1 = this.userInfoService.addUser(userInfo1);
			userInfo2 = this.userInfoService.addUser(userInfo2);
			userInfo3 = this.userInfoService.addUser(userInfo3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		User user1 = new User(userInfo1, userImageFolder.getPath() + "\\user-profil-image-empty.png");
		User user2 = new User(userInfo2, userImageFolder.getPath() + "\\user-profil-image-empty.png");
		User user3 = new User(userInfo3, null);
		
		user1 = this.userRepository.save(user1);
		user2 = this.userRepository.save(user2);
		user3 = this.userRepository.save(user3);

		
		
//		this.userRepository.save(user1);
//		this.userRepository.save(user2);
//		this.userRepository.save(user3);
		
		
		
	}
	
	private void initMovies() {
		
		File movieImageFolder = new File("./src/main/resources/images/movie");
		
//		String movieImageName = "\\background.png";
		String movieImageName = "\\image-placeholder.png";
//		String movieImageName = "\\image-placeholder-1.jpg";
		
		Movie movie1 = new Movie("Title1", MovieGenre.Action, movieImageFolder.getPath() + movieImageName, null, "Ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit amet nulla facilisi morbi tempus iaculis urna id volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim convallis aenean et tortor at risus viverra adipiscing at in tellus integer feugiat scelerisque varius morbi enim nunc faucibus a pellentesque sit amet porttitor eget dolor morbi non arcu risus quis varius quam quisque id diam vel quam elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere lorem ipsum dolor sit amet consectetur adipiscing elit duis tristique sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum a arcu cursus vitae congue mauris rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas maecenas pharetra convallis posuere morbi leo urna molestie at elementum eu facilisis sed odio morbi quis commodo.", Arrays.asList("Pera Peric"), Arrays.asList("Pera Peric", "Ana Anic", "Ana Peric"), 2024, "USA", 120, null);
		Movie movie2 = new Movie("Title2", MovieGenre.Action, movieImageFolder.getPath() + movieImageName, null, "Ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit amet nulla facilisi morbi tempus iaculis urna id volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim convallis aenean et tortor at risus viverra adipiscing at in tellus integer feugiat scelerisque varius morbi enim nunc faucibus a pellentesque sit amet porttitor eget dolor morbi non arcu risus quis varius quam quisque id diam vel quam elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere lorem ipsum dolor sit amet consectetur adipiscing elit duis tristique sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum a arcu cursus vitae congue mauris rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas maecenas pharetra convallis posuere morbi leo urna molestie at elementum eu facilisis sed odio morbi quis commodo.", Arrays.asList("Pera Peric"), Arrays.asList("Pera Peric", "Ana Anic", "Ana Peric"), 2024, "USA", 120, null);
		Movie movie3 = new Movie("Title3", MovieGenre.Action, movieImageFolder.getPath() + movieImageName, null, "Ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit amet nulla facilisi morbi tempus iaculis urna id volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim convallis aenean et tortor at risus viverra adipiscing at in tellus integer feugiat scelerisque varius morbi enim nunc faucibus a pellentesque sit amet porttitor eget dolor morbi non arcu risus quis varius quam quisque id diam vel quam elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere lorem ipsum dolor sit amet consectetur adipiscing elit duis tristique sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum a arcu cursus vitae congue mauris rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas maecenas pharetra convallis posuere morbi leo urna molestie at elementum eu facilisis sed odio morbi quis commodo.", Arrays.asList("Pera Peric"), Arrays.asList("Pera Peric", "Ana Anic", "Ana Peric"), 2024, "USA", 120, null);
		Movie movie4 = new Movie("Title4", MovieGenre.Action, movieImageFolder.getPath() + movieImageName, null, "Ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit amet nulla facilisi morbi tempus iaculis urna id volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim convallis aenean et tortor at risus viverra adipiscing at in tellus integer feugiat scelerisque varius morbi enim nunc faucibus a pellentesque sit amet porttitor eget dolor morbi non arcu risus quis varius quam quisque id diam vel quam elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere lorem ipsum dolor sit amet consectetur adipiscing elit duis tristique sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum a arcu cursus vitae congue mauris rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas maecenas pharetra convallis posuere morbi leo urna molestie at elementum eu facilisis sed odio morbi quis commodo.", Arrays.asList("Pera Peric"), Arrays.asList("Pera Peric", "Ana Anic", "Ana Peric"), 2024, "USA", 120, null);
		Movie movie5 = new Movie("Title5", MovieGenre.Action, movieImageFolder.getPath() + movieImageName, null, "Ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit amet nulla facilisi morbi tempus iaculis urna id volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim convallis aenean et tortor at risus viverra adipiscing at in tellus integer feugiat scelerisque varius morbi enim nunc faucibus a pellentesque sit amet porttitor eget dolor morbi non arcu risus quis varius quam quisque id diam vel quam elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere lorem ipsum dolor sit amet consectetur adipiscing elit duis tristique sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum a arcu cursus vitae congue mauris rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas maecenas pharetra convallis posuere morbi leo urna molestie at elementum eu facilisis sed odio morbi quis commodo.", Arrays.asList("Pera Peric"), Arrays.asList("Pera Peric", "Ana Anic", "Ana Peric"), 2024, "USA", 120, null);
		Movie movie6 = new Movie("Title6", MovieGenre.Action, movieImageFolder.getPath() + movieImageName, null, "Ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit amet nulla facilisi morbi tempus iaculis urna id volutpat lacus laoreet non curabitur gravida arcu ac tortor dignissim convallis aenean et tortor at risus viverra adipiscing at in tellus integer feugiat scelerisque varius morbi enim nunc faucibus a pellentesque sit amet porttitor eget dolor morbi non arcu risus quis varius quam quisque id diam vel quam elementum pulvinar etiam non quam lacus suspendisse faucibus interdum posuere lorem ipsum dolor sit amet consectetur adipiscing elit duis tristique sollicitudin nibh sit amet commodo nulla facilisi nullam vehicula ipsum a arcu cursus vitae congue mauris rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas maecenas pharetra convallis posuere morbi leo urna molestie at elementum eu facilisis sed odio morbi quis commodo.", Arrays.asList("Pera Peric"), Arrays.asList("Pera Peric", "Ana Anic", "Ana Peric"), 2024, "USA", 120, null);
		
		
		this.movieRepository.save(movie1);
		this.movieRepository.save(movie2);
		this.movieRepository.save(movie3);
		this.movieRepository.save(movie4);
		this.movieRepository.save(movie5);
		this.movieRepository.save(movie6);
		
		
		Optional<User> user1 = this.userRepository.findById(1l);
		Optional<User> user2 = this.userRepository.findById(2l);
		
		// User Bookmarked Movies
		try {
			user1.get().addToBookmarks(movie1);
			user1.get().addToBookmarks(movie2);
			user2.get().addToBookmarks(movie2);
			user2.get().addToBookmarks(movie3);
			
			this.userRepository.save(user1.get());
			this.userRepository.save(user2.get());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		user1.get().addWatchedMovie(movie1);
		user1.get().addWatchedMovie(movie2);
		user2.get().addWatchedMovie(movie2);
		user2.get().addWatchedMovie(movie1);
		
		this.userRepository.save(user1.get());
		this.userRepository.save(user2.get());
		
		
		
	}
	
	private void initScreen() {
		
		Screen screen1 = new Screen("Screen 1", 10, 8);
		Screen screen2 = new Screen("Screen 2", 12, 5);
		Screen screen3 = new Screen("Screen 3", 14, 6);
		Screen screen4 = new Screen("Screen 4", 12, 5);
		Screen screen5 = new Screen("Screen 5", 14, 16);
		
		this.screenRepository.save(screen1);
		this.screenRepository.save(screen2);
		this.screenRepository.save(screen3);
		this.screenRepository.save(screen4);
		this.screenRepository.save(screen5);
		
		Optional<Movie> movie1 = this.movieRepository.findById(1l);
//		Optional<Movie> movie2 = this.movieRepository.findById(1l);
//		Optional<Movie> movie3 = this.movieRepository.findById(1l);
		
		
		Shows shows1 = new Shows(movie1.get(), screen5, new ShowsDate(Day.Monday, 11, 30), 10.25f);
		Shows shows2 = new Shows(movie1.get(), screen5, new ShowsDate(Day.Monday, 11, 00), 15.0f);
		Shows shows3 = new Shows(movie1.get(), screen3, new ShowsDate(Day.Monday, 18, 30), 20.5f);
		
		Shows shows4 = new Shows(movie1.get(), screen3, new ShowsDate(Day.Wednesday, 11, 00), 20.5f);
		Shows shows5 = new Shows(movie1.get(), screen1, new ShowsDate(Day.Wednesday, 12, 00), 20.5f);
		Shows shows6 = new Shows(movie1.get(), screen3, new ShowsDate(Day.Wednesday, 20, 00), 20.5f);
		
		Shows shows7 = new Shows(movie1.get(), screen1, new ShowsDate(Day.Thursday, 9, 00), 20.5f);
		Shows shows8 = new Shows(movie1.get(), screen4, new ShowsDate(Day.Thursday, 11, 00), 20.5f);
		Shows shows9 = new Shows(movie1.get(), screen5, new ShowsDate(Day.Thursday, 15, 00), 20.5f);
		
		Shows shows10 = new Shows(movie1.get(), screen1, new ShowsDate(Day.Friday, 12, 00), 20.5f);
		Shows shows11 = new Shows(movie1.get(), screen2, new ShowsDate(Day.Friday, 13, 30), 20.5f);
		Shows shows12 = new Shows(movie1.get(), screen4, new ShowsDate(Day.Friday, 16, 30), 20.5f);
		
		Shows shows13 = new Shows(movie1.get(), screen1, new ShowsDate(Day.Saturday, 14, 00), 20.5f);
		Shows shows14 = new Shows(movie1.get(), screen2, new ShowsDate(Day.Saturday, 14, 30), 20.5f);
		Shows shows15 = new Shows(movie1.get(), screen1, new ShowsDate(Day.Saturday, 22, 00), 20.5f);
		
		
		
		this.showsRepository.save(shows1);
		this.showsRepository.save(shows2);
		this.showsRepository.save(shows3);
		
		this.showsRepository.save(shows4);
		this.showsRepository.save(shows5);
		this.showsRepository.save(shows6);
		
		this.showsRepository.save(shows7);
		this.showsRepository.save(shows8);
		this.showsRepository.save(shows9);
		
		this.showsRepository.save(shows10);
		this.showsRepository.save(shows11);
		this.showsRepository.save(shows12);
		
		this.showsRepository.save(shows13);
		this.showsRepository.save(shows14);
		this.showsRepository.save(shows15);
		
		
		Optional<User> user = this.userRepository.findById(1l);
		Optional<User> user2 = this.userRepository.findById(2l);
		
		Reservation reservation = new Reservation(user.get(), shows1, new HashSet<Integer>());
		Reservation reservation2 = new Reservation(user2.get(), shows1, new HashSet<Integer>());
		Reservation reservation3 = new Reservation(user2.get(), shows1, new HashSet<Integer>());
		
		reservation = this.reservationRepository.save(reservation);
		reservation2 =  this.reservationRepository.save(reservation2);
		
		try {
			reservation.reserveSeat(1);
			reservation.reserveSeat(2);
			reservation.reserveSeat(3);
			reservation.reserveSeat(4);
			
			reservation2.reserveSeat(10);
			reservation2.reserveSeat(11);
			reservation2.reserveSeat(12);
			
			reservation3.reserveSeat(15);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		reservation = this.reservationRepository.save(reservation);
		reservation2 = this.reservationRepository.save(reservation2);
		reservation3 = this.reservationRepository.save(reservation3);
		

		try {
			reservation.reserveSeat(100);
			reservation = this.reservationRepository.save(reservation);
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		
	}
	
	private void initComments() {
		
//		Optional<User> user1 = this.userRepository.findById(1l);
//		Optional<User> user2 = this.userRepository.findById(2l);
//		Optional<Movie> movie1 = this.movieRepository.findById(1l);
		
//		Comment comment1 = new Comment(user1.get(), movie1.get(), new Date(), 3, "Varius duis at consectetur lorem donec massa sapien faucibus et molestie ac feugiat sed lectus vestibulum mattis ullamcorper velit sed ullamcorper morbi tincidunt ornare massa eget egestas purus viverra accumsan in nisl nisi scelerisque eu ultrices vitae auctor eu augue ut lectus arcu bibendum at varius vel pharetra vel turpis.");
//		Comment comment2 = new Comment(user2.get(), movie1.get(), new Date(), 4, "Interdum posuere lorem ipsum dolor sit amet consectetur adipiscing elit duis tristique sollicitudin nibh sit amet commodo nulla facilisi nullam.");
		
//		this.commentRepository.save(comment1);
//		this.commentRepository.save(comment2);
		
		
		
		
		
	}
	
	
	
}

