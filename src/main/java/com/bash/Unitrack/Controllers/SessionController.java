package com.bash.Unitrack.Controllers;

import com.bash.Unitrack.Data.DTO.SessionDTO;
import com.bash.Unitrack.Data.DTO.SessionRequest;
import com.bash.Unitrack.Data.Models.Session;
import com.bash.Unitrack.Exceptions.NotFoundException;
import com.bash.Unitrack.Service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }

    @GetMapping("")
    public ResponseEntity<List<SessionDTO>> fetchService(){
        return sessionService.fetchSession();
    }

    @PostMapping("/create")
    public ResponseEntity<String > create(@RequestBody SessionRequest sessionRequest) throws NotFoundException {
        return sessionService.createSession(sessionRequest);
    }
}
