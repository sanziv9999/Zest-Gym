package Zest.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Zest.gym.model.AttendanceSheet;

public interface AttendanceRepository extends JpaRepository<AttendanceSheet, Integer> {

}
