package com.bash.Unitrack.Data.DTO;

import com.bash.Unitrack.Data.Models.Lecturer;

public record LecturerDTO(
        String username,
        String firstName,
        String lastName,
        String email,
        String role,
        String department
) {
    public LecturerDTO(Lecturer lecturer) {
        this(
                lecturer.getUsername(),
                lecturer.getFirstName(),
                lecturer.getLastName(),
                lecturer.getEmail(),
                lecturer.getRole().name(),
                lecturer.getDepartment()
        );
    }
}
