package com.bash.Unitrack.Data.DTO;

import com.bash.Unitrack.Data.Models.Location;
import lombok.Data;


public record SessionRequest(String courseName, Location location) {
}
