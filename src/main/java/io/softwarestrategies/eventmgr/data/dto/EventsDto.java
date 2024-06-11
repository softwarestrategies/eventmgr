package io.softwarestrategies.eventmgr.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class EventsDto {

    private List<EventDto> events = new ArrayList<>();

    public EventsDto(List<EventDto> events) {
        this.events = events;
    }
}
