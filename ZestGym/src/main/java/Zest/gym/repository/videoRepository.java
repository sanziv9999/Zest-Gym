package Zest.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Zest.gym.model.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

}
