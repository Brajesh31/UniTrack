package com.bash.Unitrack.Data.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Instant startTime;
    private Instant endTime;
    private Stat status;
    @ManyToOne
    private Course course;
    @ManyToOne
    @JsonManagedReference
    private Lecturer lecturer;
    @OneToOne
    @JsonManagedReference
    private Attendance attendance;
    @Embedded
    private Location location;

    public Session(){

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Stat getStatus() {
        return status;
    }

    public void setStatus(Stat status) {
        this.status = status;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Session(Instant startTime, Lecturer lecturer, Attendance attendance){
        this.startTime = startTime;
        this.lecturer = lecturer;
        this.attendance = attendance;
    }



}
