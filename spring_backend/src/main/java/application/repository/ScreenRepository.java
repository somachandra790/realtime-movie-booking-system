package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import application.model.Screen;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {

}
