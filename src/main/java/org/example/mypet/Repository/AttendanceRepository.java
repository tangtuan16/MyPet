package org.example.mypet.Repository;

import org.example.mypet.Models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployeeIdAndWorkDateBetween(Long employeeId, LocalDate start, LocalDate end);

    Attendance findByEmployeeIdAndWorkDate(long id, LocalDate workDate);
}
