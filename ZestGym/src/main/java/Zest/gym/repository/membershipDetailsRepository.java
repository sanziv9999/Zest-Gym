package Zest.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Zest.gym.model.MembershipDetails;

public interface MembershipDetailsRepository extends JpaRepository<MembershipDetails, Integer> {

}
