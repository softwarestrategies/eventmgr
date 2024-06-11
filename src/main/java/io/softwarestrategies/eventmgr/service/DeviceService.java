package io.softwarestrategies.eventmgr.service;

import io.softwarestrategies.eventmgr.data.dto.DeviceDto;
import io.softwarestrategies.eventmgr.data.dto.DevicesDto;
import io.softwarestrategies.eventmgr.exception.EntityNotFoundException;
import io.softwarestrategies.eventmgr.repository.DeviceRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceDto getById(UUID deviceId) {
        return DeviceDto.fromDevice(
                deviceRepository.findById(deviceId)
                        .orElseThrow(() -> new EntityNotFoundException("Device Not Found: " + deviceId))
        );
    }

    public DevicesDto getByLocation(String location) {
        List<DeviceDto> deviceDtoList = deviceRepository.findByLocation(location).stream()
                .map(DeviceDto::fromDevice).toList();
        return new DevicesDto(deviceDtoList);
    }
}
