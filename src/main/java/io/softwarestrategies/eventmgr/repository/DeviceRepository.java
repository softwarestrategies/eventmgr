package io.softwarestrategies.eventmgr.repository;

import io.softwarestrategies.eventmgr.data.model.Device;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CassandraRepository<Device, UUID> {

    @Query("""
                SELECT * FROM device 
                WHERE id = :id
            """)
    Optional<Device> findById(UUID id);

    @Query("""
                SELECT * FROM device 
                WHERE location = :location
            """)
    List<Device> findByLocation(String location);
}
