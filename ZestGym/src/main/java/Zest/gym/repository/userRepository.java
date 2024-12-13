package Zest.gym.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Zest.gym.model.User;

public interface userRepository extends JpaRepository<User, Integer>{
	
	boolean existsByEmailAndPassword(String email, String password);
	
	@Query("SELECT u.username FROM User u WHERE u.email = :email")
	String findUsernameByEmail(String email);
	
	
	Optional<User> findByEmail(String email);
	
	default void updatePasswordByEmail(String email, String newPassword) {
        findByEmail(email).ifPresent(user -> {
            // Update the user's password
            user.setPassword(newPassword);
            save(user);
        });
    }
	
	
	long count();
	
	
	

}
