package com.bash.Unitrack.Data.DTO;

import com.bash.Unitrack.Data.Models.Location;

public record AttendanceRequestDTO(Long sessionId, Location location) {}
