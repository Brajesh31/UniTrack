package com.bash.Unitrack.Service;

import com.bash.Unitrack.Data.DTO.AttendanceDT0;
import com.bash.Unitrack.Data.DTO.AttendanceRequestDTO;
import com.bash.Unitrack.Data.Models.*;
import com.bash.Unitrack.Exceptions.NotFoundException;
import com.bash.Unitrack.Repositories.AttendanceRepository;
import com.bash.Unitrack.Repositories.DeviceIDRepository;
import com.bash.Unitrack.Repositories.SessionRepository;
import com.bash.Unitrack.Repositories.UserRepository;
import com.bash.Unitrack.Utilities.Haversine;
import com.bash.Unitrack.Utilities.RequestClass;
import com.bash.Unitrack.Utilities.RouteRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    public final AttendanceRepository attendanceRepository;
    public final UserRepository userRepository;
    public final SessionRepository sessionRepository;
    public final AuthenticationService authenticationService;
    private final DeviceIDRepository deviceIDRepository;
    private final WebClient webclient;



    Session sessions = new Session();

    public AttendanceService(
            AttendanceRepository attendanceRepository,
            UserRepository userRepository,
            SessionRepository sessionRepository,
            WebClient webclient,
            AuthenticationService authenticationService, DeviceIDRepository deviceIDRepository){
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.authenticationService = authenticationService;
        this.webclient = webclient;
        this.deviceIDRepository = deviceIDRepository;
    }

    public ResponseEntity<List<AttendanceDT0>> fetchAttendance() {
        return ResponseEntity.ok(attendanceRepository.findAll()
                .stream().map(AttendanceDT0::new).collect(Collectors.toList()));
    }

    public ResponseEntity<String> range(
            Location lecturerLocation,
            Location studentLocation) throws JsonProcessingException {

        String apiKey = "AIzaSyBdMRVS5_VcpweMHk27ZP_GkXFWMHmEgco";

        RequestClass request = new RequestClass(
                lecturerLocation.getLatitude(), lecturerLocation.getLongitude(),
                studentLocation.getLatitude(), studentLocation.getLongitude());

        request.setTravelMode("WALK");

        ObjectMapper mapper = new ObjectMapper();
        System.out.println("\n The request x" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request) + "\n");

        RouteRequest routeRequest = new RouteRequest(webclient);

        String value = routeRequest.computeRoute(request, apiKey)
                .block();

        if (value == null) {
            return ResponseEntity.ok("Value is null");
        }
        return ResponseEntity.ok(value);
    }

    public double range2(RequestClass request){

        return Haversine.calculateDistance(request);
    }

    public ResponseEntity<String> create(
            AttendanceRequestDTO attendanceRequestDTO,
            @RequestHeader("X-Device-ID") String deviceID ,
            @RequestParam(required = false) Long id) throws NotFoundException,
            JsonProcessingException {

        Session session = sessionRepository.findById(attendanceRequestDTO.sessionId())
                .orElseThrow(() -> new NotFoundException("Session does not exist"));

        if (session.getStatus() == Stat.CLOSED){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Session is closed");
        }

        RequestClass requestClass = new RequestClass(
                session.getLocation().getLatitude(),
                session.getLocation().getLongitude(),
                attendanceRequestDTO.location().getLatitude(),
                attendanceRequestDTO.location().getLongitude()
        );

        Student student = new Student();

        String username = authenticationService.getUsername();
        User currentUser = userRepository.findByUsername(username).orElseThrow();
        if (currentUser instanceof Lecturer){
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Student does not exist"));

            if (!(user instanceof Student)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not a student");
            }
            student = (Student) user;
        }

        if (currentUser instanceof Student){
            student = (Student) currentUser;
        }

        if (deviceIDRepository.findDeviceByDeviceID(deviceID).isEmpty()){
            System.out.println(deviceID);
            deviceIDRepository.save(new Device(Instant.now(), deviceID));
        }else {
            // Check If device has already taken attendance 1 minute ago
            Device device = deviceIDRepository.findDeviceByDeviceID(deviceID)
                    .orElseThrow(() -> new NotFoundException("Device Not found"));
            if (currentUser instanceof Student && device.getDeviceID().equals(deviceID)
                    && device.getTime().isAfter(Instant.now()
                    .minusSeconds(60))){
                System.out.println("User has already marked attendance");
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Device has already been used \n" + "Wait 1 minute to try again");
            }
        }

        double distance = range2(requestClass);

        System.out.println("Haversine " + distance);
        if (range2(requestClass) >= 0.025){
            double excess = Math.round((distance - 0.025) * 1000);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).
                    body("You're out of range \n" + excess + " metres from location");
        }

        Attendance attendance = session.getAttendance();

        sessions = session;
        userRepository.save(student);
        List<Student> students = new ArrayList<>();
        students.add(student);
        attendance.setStudent(students);
        attendance.setLocation(attendanceRequestDTO.location());
        student.getAttendance().add(attendance);
        attendanceRepository.save(attendance);
        userRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body("Attendance Marked");
    }

    public ResponseEntity<List<Attendance>> studentAttendance(Long id) throws NotFoundException {

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        Student student = (Student) user;
        List<Attendance> attendanceList =  student.getAttendance();
        return ResponseEntity.status(HttpStatus.OK).body(attendanceList);
    }

    public ResponseEntity<List<Attendance>> attendanceLecturer(Long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        Lecturer lecturer = (Lecturer) user;
        List<Attendance> attendanceList = lecturer.getAttendance();
        return ResponseEntity.ok(attendanceList);
    }
}