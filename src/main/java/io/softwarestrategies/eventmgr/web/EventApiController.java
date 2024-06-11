package io.softwarestrategies.eventmgr.web;

import io.softwarestrategies.eventmgr.data.dto.EventDto;
import io.softwarestrategies.eventmgr.data.dto.EventsDto;
import io.softwarestrategies.eventmgr.service.EventService;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
public class EventApiController {

    private final EventService eventService;

    public EventApiController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<EventsDto> getByDeviceId(@PathVariable UUID deviceId) {
        EventsDto result = eventService.getByDeviceId(deviceId);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postEvent(@RequestBody EventDto eventDto) {
        eventService.postEvent(eventDto);
    }
}
