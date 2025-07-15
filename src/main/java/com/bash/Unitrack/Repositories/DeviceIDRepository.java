package com.bash.Unitrack.Repositories;

import com.bash.Unitrack.Data.Models.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public interface DeviceIDRepository extends JpaRepository<Device, Long> {

    public Optional<Device> findDeviceByDeviceID(String deviceID);

}
