package com.bash.Unitrack.Data.DTO;

import com.bash.Unitrack.Data.Models.Attendance;
import com.bash.Unitrack.Data.Models.Lecturer;
import com.bash.Unitrack.Data.Models.Student;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceDT0 {

    private String courseName;
    private List<StudentDTO> studentList;
    private String lecturer;
    private final String date;
    private final String  time;

    public AttendanceDT0(Attendance attendance) {

        courseName = attendance.getCourse().getCourseName();
        lecturer = attendance.getLecturer().getFirstName() + " " + attendance.getLecturer().getLastName();
        studentList = attendance.getStudents().stream().map(StudentDTO::new).collect(Collectors.toList());
        LocalDateTime dateTime = Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
        time = dateTime.getHour() + ":" + dateTime.getMinute();
        date = dateTime.getDayOfWeek() + ", " +
                dateTime.getDayOfMonth() + " " + dateTime.getMonth() + ", " + dateTime.getYear();

    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<StudentDTO> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentDTO> studentList) {
        this.studentList = studentList;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
