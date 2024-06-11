package io.softwarestrategies.eventmgr.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class DevicesDto {

    private List<DeviceDto> devices = new ArrayList<>();

    public DevicesDto(List<DeviceDto> devices) {
        this.devices = devices;
    }
}
