package Zest.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Zest.gym.model.Diet;
@Repository
public interface dietRepository extends JpaRepository<Diet, Integer> {

}