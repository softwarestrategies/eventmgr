package io.softwarestrategies.eventmgr.data.model;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
@Getter
@AllArgsConstructor
public class EventPrimaryKey {

    @PrimaryKeyColumn(name = "device_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID deviceId;

    @PrimaryKeyColumn(name = "datetime", type = PrimaryKeyType.CLUSTERED, ordinal = 0)
    private Instant dateTime;

    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.CLUSTERED, ordinal = 1)
    private UUID id;
}
