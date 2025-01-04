package Zest.gym.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Zest.gym.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	boolean existsByEmailAndPassword(String email, String password);
	
	@Query("SELECT u.username FROM User u WHERE u.email = :email")
	String findUsernameByEmail(String email);
	
	@Query("SELECT u.email FROM User u WHERE u.username = :username")
	String findEmailByUsername(String username);
	
	@Query("SELECT u.role FROM User u WHERE u.email = :email")
	String findRoleByEmail(String email);
	
	
	@Query("SELECT u FROM User u WHERE u.role = :role")
	List<org.apache.catalina.User> findAllByRole(String role);
	
	Optional<User> findByEmail(String email);
	
	default void updatePasswordByEmail(String email, String newPassword) {
        findByEmail(email).ifPresent(user -> {
            // Update the user's password
            user.setPassword(newPassword);
            save(user);
        });
    }
	
	 List<User> findAll(); 
	
	
	long count();
	
	
	

}
