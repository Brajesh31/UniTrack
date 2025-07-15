    package com.bash.Unitrack.Controllers;
    
    import com.bash.Unitrack.Data.DTO.CourseDTO;
    import com.bash.Unitrack.Data.Models.Course;
    import com.bash.Unitrack.Data.DTO.CourseRequest;
    import com.bash.Unitrack.Exceptions.NotFoundException;
    import com.bash.Unitrack.Service.AuthenticationService;
    import com.bash.Unitrack.Service.CourseService;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    
    import java.util.List;
    
    @RestController
    @RequestMapping("/api/v1/courses")
    public class CourseController {
    
        private final CourseService courseService;
        private final AuthenticationService authenticationService;
    
        public CourseController(CourseService courseService, AuthenticationService authenticationService){
            this.courseService = courseService;
            this.authenticationService = authenticationService;
        }
    
        @GetMapping("")
        public ResponseEntity<List<CourseDTO>> getAll(){
            return courseService.fetchAll();
        }
    
        @GetMapping("/lecturer")
        public ResponseEntity<List<Course>> getLecturerCourses() throws NotFoundException {
            return courseService.fetchCourse(authenticationService.getId());
        }
    
        @PostMapping("/add")
        public ResponseEntity<String > bash(@RequestBody CourseRequest courseRequest) throws NotFoundException {
            return courseService.addCourse(courseRequest);
        }
    
    }
    
