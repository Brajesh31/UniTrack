package com.bash.Unitrack.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("")
    public ResponseEntity<String > test(@RequestHeader("X-Device-ID") String deviceId){
        System.out.println(deviceId);
        return ResponseEntity.ok(deviceId);
    }
}
