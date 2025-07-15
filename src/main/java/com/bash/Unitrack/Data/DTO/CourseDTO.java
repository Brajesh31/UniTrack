package com.bash.Unitrack.Data.DTO;
import com.bash.Unitrack.Data.Models.Course;

import java.util.List;
import java.util.stream.Collectors;

public record CourseDTO(String courseName, String courseCode, List<LecturerDTO> lecturerId) {
    public CourseDTO(Course course) {
        this(
                course.getCourseName(),
                course.getCourseCode(),
                course.getLecturer().stream().map(LecturerDTO::new)
                        .collect(Collectors.toList())
        );
    }
}
