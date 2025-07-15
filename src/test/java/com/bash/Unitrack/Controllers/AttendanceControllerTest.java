package com.bash.Unitrack.Controllers;

import com.bash.Unitrack.Data.Models.Attendance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceControllerTest {

    @BeforeEach
    void setUp() {
        List<Attendance> attendanceList = new ArrayList<>();
        Attendance newAttendance = new Attendance();
        attendanceList.add(newAttendance);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void fetchAll() {

    }

    @Test
    void createAttendance() {
    }
}