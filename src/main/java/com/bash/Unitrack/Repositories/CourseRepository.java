package com.bash.Unitrack.Repositories;

import com.bash.Unitrack.Data.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseName(String courseName);

    boolean existsByCourseCode(String courseCode);
}
