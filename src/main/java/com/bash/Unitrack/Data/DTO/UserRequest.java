package com.bash.Unitrack.Data.DTO;

public record UserRequest(
        String username,
        String firstName,
        String lastName,
        String program,
        String indexNumber,
        String password,
        String email,
        String role
) {}
