package io.softwarestrategies.eventmgr.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.softwarestrategies.eventmgr.data.model.Device;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class DeviceDto {

    private UUID id;
    private UUID deviceId;
    private Instant dateTime;
    private String type;
    private String location;
    private String name;

    public static DeviceDto fromDevice(Device device) {
        return DeviceDto.builder()
                .id(device.getId())
                .type(device.getType())
                .location(device.getLocation())
                .name(device.getName())
                .build();
    }
}