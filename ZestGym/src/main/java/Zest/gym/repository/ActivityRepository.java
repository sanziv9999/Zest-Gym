package Zest.gym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Zest.gym.model.Activity;
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer>{
	
	List<Activity> findByEmail(String email);
	
	@Query("SELECT a.scheduleId FROM Activity a WHERE a.email = :email")
    List<Integer> findScheduleIdsByEmail(@Param("email") String email);

}
