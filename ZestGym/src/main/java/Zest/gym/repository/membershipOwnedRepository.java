package Zest.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Zest.gym.model.membershipOwned;
@Repository
public interface membershipOwnedRepository extends JpaRepository<membershipOwned, Integer> {

}
