package io.softwarestrategies.eventmgr.data;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyClass
public class EventPrimaryKey {

    @PrimaryKeyColumn(name = "device_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID deviceId;

    @PrimaryKeyColumn(name = "location", type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    private String env;

    @PrimaryKeyColumn(name = "event_time", type = PrimaryKeyType.CLUSTERED, ordinal = 0)
    private Instant eventTime;

    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.CLUSTERED, ordinal = 1)
    private UUID id;
}