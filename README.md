# MyCassandra

## About

This is just a sample project to (re)learn integrating Spring Boot with a Cassandra database.  It is setup only for local dev work, but it wouldn't be hard to cloud-ize it.

## Helpful Resources


https://www.baeldung.com/cassandra-keys

## Setup

The Spring Boot app will expect Cassandra to be running & the schema setup.  So, from the command-line start it up:

    ../mycassandra % docker-compose up -d

Then you can see if it is running fine and even see the logs:

    ../mycassandra % docker ps

    ../mycassandra % docker logs event-cassandra

Now log onto the Cassandra docker container:

    ../mycassandra % docker exec -it event-cassandra bash

The prompt should now reflect being root on the container.  And then go into Cassandra

    root@xxxxx:/#

    root@xxxxx:/# cqlsh

Create a keyspace, create the table we will use and then see a list of all tables in the keyspace & also info on the table just created:

    cqlsh> CREATE KEYSPACE event_keyspace WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

    cqlsh> USE event_keyspace;

    cqlsh:event_keyspace> 

        CREATE TABLE event (
            id UUID,
            device_id UUID,
            location TEXT,
            event_time TIMESTAMP,
            event_type TEXT,
            metadata TEXT,
            PRIMARY KEY ((location, device_id), event_time, id)
        );

    cqlsh:event_keyspace> DESC TABLES;

        event

    cqlsh:event_keyspace> DESC TABLE event;
    
        < list of the fields, as some characteristics regarding the table >

    cqlsh:event_keyspace> 

        INSERT INTO event (id, device_id, location, event_time, event_type, metadata)
        VALUES (UUID(), b1178de4-ef93-41c4-92d9-f2778d2f4f1c, 'HOUSE', TOTIMESTAMP(now()), 'DOOR OPENED', 'ACTOR=HENRY; OPENED-BY=KEYCARD');

        INSERT INTO event (id, device_id, location, event_time, event_type, metadata)
        VALUES (UUID(), b1178de4-ef93-41c4-92d9-f2778d2f4f1c, 'HOUSE', TOTIMESTAMP(now()), 'DOOR CLOSED', '');

    cqlsh:event_keyspace> SELECT * FROM event;

        < the above-inserted record should be listed >

When done in the Cassandra instance, exit out back to the host's commandline.

Finally, when all the dev work is done, the instance of Cassandra running should be shutdown:

    ../mycassandra % docker-compose stop