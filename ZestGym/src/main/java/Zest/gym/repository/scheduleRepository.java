package Zest.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Zest.gym.model.schedule;

@Repository
public interface scheduleRepository extends JpaRepository<schedule, Integer>{

}
