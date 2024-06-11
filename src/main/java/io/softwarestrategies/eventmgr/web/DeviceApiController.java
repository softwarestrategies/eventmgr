package io.softwarestrategies.eventmgr.web;

import io.softwarestrategies.eventmgr.data.dto.DeviceDto;
import io.softwarestrategies.eventmgr.data.dto.DevicesDto;
import io.softwarestrategies.eventmgr.service.DeviceService;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/devices")
public class DeviceApiController {

    private final DeviceService deviceService;

    public DeviceApiController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> getById(@PathVariable UUID id) {
        DeviceDto result = deviceService.getById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<DevicesDto> getByLocation(@PathVariable String location) {
        DevicesDto result = deviceService.getByLocation(location);
        return ResponseEntity.ok(result);
    }

}
