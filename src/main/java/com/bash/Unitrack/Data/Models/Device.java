package com.bash.Unitrack.Data.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@AllArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Instant time;
    String deviceID;

    public Device(){

    }

    public Device(Instant time, String deviceID) {
        this.time = time;
        this.deviceID = deviceID;
    }
}
