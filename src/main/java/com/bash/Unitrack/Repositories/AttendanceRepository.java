package com.bash.Unitrack.Repositories;

import com.bash.Unitrack.Data.Models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
