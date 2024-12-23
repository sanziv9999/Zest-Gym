package Zest.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Zest.gym.model.video;

@Repository
public interface videoRepository extends JpaRepository<video, Integer> {

}
