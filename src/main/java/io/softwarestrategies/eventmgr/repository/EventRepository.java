package io.softwarestrategies.eventmgr.repository;

import io.softwarestrategies.eventmgr.data.model.Event;
import io.softwarestrategies.eventmgr.data.model.EventPrimaryKey;
import java.util.List;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CassandraRepository<Event, EventPrimaryKey> {

    @Query("""
                SELECT * FROM event 
                WHERE device_id = :deviceId
            """)
    List<Event> findByDeviceId(UUID deviceId);
}
