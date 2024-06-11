package io.softwarestrategies.eventmgr.service;

import io.softwarestrategies.eventmgr.data.model.Event;
import io.softwarestrategies.eventmgr.data.dto.EventDto;
import io.softwarestrategies.eventmgr.data.model.EventPrimaryKey;
import io.softwarestrategies.eventmgr.repository.EventRepository;
import io.softwarestrategies.eventmgr.data.dto.EventsDto;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventsDto getByDeviceId(UUID deviceId) {
        List<EventDto> eventDtoList = eventRepository.findByDeviceId(deviceId).stream().map(EventDto::fromEvent).toList();
        return new EventsDto(eventDtoList);
    }

    @Transactional(readOnly = false)
    public void postEvent(EventDto eventDto) {
        EventPrimaryKey primaryKey = new EventPrimaryKey(eventDto.getDeviceId(),Instant.now(), UUID.randomUUID());
        Event event = new Event(primaryKey, eventDto.getType(), eventDto.getData());
        eventRepository.save(event);
    }
}