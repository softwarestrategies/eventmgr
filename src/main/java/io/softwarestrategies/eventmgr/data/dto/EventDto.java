package io.softwarestrategies.eventmgr.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.softwarestrategies.eventmgr.data.model.Event;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class EventDto {

    private UUID id;
    private UUID deviceId;
    private Instant dateTime;
    private String type;
    private String data;

    public static EventDto fromEvent(Event event) {
        return EventDto.builder()
                .id(event.getPrimaryKey().getId())
                .deviceId(event.getPrimaryKey().getDeviceId())
                .dateTime(event.getPrimaryKey().getDateTime())
                .type(event.getType())
                .data(event.getData())
                .build();
    }
}