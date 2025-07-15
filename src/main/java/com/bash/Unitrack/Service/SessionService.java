package com.bash.Unitrack.Service;

import com.bash.Unitrack.Data.DTO.SessionDTO;
import com.bash.Unitrack.Data.DTO.SessionRequest;
import com.bash.Unitrack.Data.Models.*;
import com.bash.Unitrack.Exceptions.NotFoundException;
import com.bash.Unitrack.Repositories.AttendanceRepository;
import com.bash.Unitrack.Repositories.CourseRepository;
import com.bash.Unitrack.Repositories.SessionRepository;
import com.bash.Unitrack.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final CourseRepository courseRepository;

    public SessionService(
            SessionRepository sessionRepository,
            UserRepository userRepository,
            AttendanceRepository attendanceRepository,
            CourseRepository courseRepository){
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
        this.courseRepository = courseRepository;
    }


    public ResponseEntity<List<SessionDTO>> fetchSession() {
        return ResponseEntity.ok(sessionRepository.findAll()
                .stream().map(SessionDTO::new).collect(Collectors.toList()));
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void closeExpiredSession(){

        List<Session> activeSession = sessionRepository.findByStatus(Stat.ACTIVE);
        if (activeSession.isEmpty()){
            return;
        }
        for (Session session : activeSession){
            if (session.getEndTime().isBefore(Instant.now())) {
                session.setStatus(Stat.CLOSED);
                sessionRepository.save(session);
            }
        }
    }

    public Boolean isInRange(Location studentLocation, Location lecturerLocation){
        return null;
    }
    public ResponseEntity<String> createSession(SessionRequest sessionRequest) throws NotFoundException {

        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = (Jwt) object;
        String  username = jwt.getSubject();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User does not exist"));
        if (user.getRole().equals(Role.STUDENT)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You should be a lecturer to create a session");
        }
        Optional<Course> courseOptional = courseRepository.findByCourseName(sessionRequest.courseName());
        if (courseOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course does not exist");
        }
        Course course = courseOptional.get();
        Lecturer lecturer = (Lecturer) user;
        Session session = new Session();
        Attendance attendance = new Attendance();
        userRepository.save(lecturer);
        courseRepository.save(course);
        sessionRepository.save(session);

        //Set up attendance
        attendance.setLecturer(lecturer);
        attendance.setCourse(course);
        attendance.setTime(Instant.now());
        attendance.setSession(session);
        attendanceRepository.save(attendance);

        //Setup Session
        session.setAttendance(attendance);
        session.setLocation(sessionRequest.location());
        session.setCourse(course);
        session.setLecturer(lecturer);
        session.setStartTime(Instant.now());
        session.setStatus(Stat.ACTIVE);
        session.setEndTime(Instant.now().plusSeconds(60));

        sessionRepository.save(session);


        return ResponseEntity.ok("Session Started");
    }
}





