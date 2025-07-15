    package com.bash.Unitrack.Data.Models;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;

    import java.time.Instant;
    import java.util.ArrayList;
    import java.util.List;

    @Data
    @Entity
    @AllArgsConstructor
    public class Attendance {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @OneToOne
        @JsonBackReference
        private Session session;
        private Instant time;
        @ManyToOne
        private Course course;
        @ManyToMany(mappedBy = "attendance")
        @JsonManagedReference
        private List<Student> students = new ArrayList<>();
        @ManyToOne
        @JsonManagedReference
        private Lecturer lecturer;
        private Location location;
        public Attendance(){

        }

        public Attendance(Session session, Instant time, Lecturer lecturer, Status status){
            this.session = session;
            this.time = time;
            this.lecturer = lecturer;
        }

        public List<Student> getStudents() {
            return students;
        }

        public void setStudents(List<Student> students) {
            this.students = students;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Session getSession() {
            return session;
        }

        public void setSession(Session session) {
            this.session = session;
        }

        public Instant getTime() {
            return time;
        }

        public void setTime(Instant time) {
            this.time = time;
        }

        public Course getCourse() {
            return course;
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public void setStudent(List<Student> student) {
            this.students = student;
        }

        public Lecturer getLecturer() {
            return lecturer;
        }

        public void setLecturer(Lecturer lecturer) {
            this.lecturer = lecturer;
        }



    }
