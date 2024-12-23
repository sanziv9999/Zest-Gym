package Zest.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Zest.gym.model.Trainer;

@Repository
public interface trainerRepository extends JpaRepository<Trainer, Integer> {

}
