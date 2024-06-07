package io.softwarestrategies.eventmgr.data;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@UserDefinedType("event")
@Table("event")
public class Event {

    @PrimaryKey
    private EventPrimaryKey key;

    @Column("event_type")
    private String eventType;

    @Column("metadata")
    private String metadata;
}