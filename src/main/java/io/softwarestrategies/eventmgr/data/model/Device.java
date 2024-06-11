package io.softwarestrategies.eventmgr.data.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("device")
@AllArgsConstructor
@Getter
public class Device {

    @PrimaryKey
    private UUID id;

    @Column("type")
    private String type;

    @Column("location")
    private String location;

    @Column("name")
    private String name;
}
