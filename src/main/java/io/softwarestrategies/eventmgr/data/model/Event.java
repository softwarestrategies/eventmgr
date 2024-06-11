package io.softwarestrategies.eventmgr.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("event")
@AllArgsConstructor
@Getter
public class Event {

    @PrimaryKey
    private EventPrimaryKey primaryKey;

    @Column("type")
    private String type;

    @Column("data")
    private String data;
}