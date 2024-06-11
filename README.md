# EventMgr

## About

A very simplistic E


This is just a sample project to (re)learn integrating Spring Boot with a Cassandra database.  It is setup only for local dev work, but it wouldn't be hard to cloud-ize it.

It isn't an endall solution

## Helpful Resources

https://towardsdatascience.com/when-to-use-cassandra-and-when-to-steer-clear-72b7f2cede76
https://www.baeldung.com/cassandra-keys
https://www.baeldung.com/cassandra-object-mapping-datastax-java-driver
https://www.baeldung.com/spring-data-cassandra-dates
https://medium.com/javarevisited/spring-data-with-apache-cassandra-database-composite-key-3c43a2dc7685

## Setup

The Spring Boot app will expect Cassandra to be running & the schema setup.  So, from the command-line start it up:

    ../eventmgr % docker-compose up -d

Then you can see if it is running fine and even see the logs:

    ../eventmgr % docker ps

    ../eventmgr % docker logs eventmgr-cassandra

Now log onto the Cassandra docker container:

    ../eventmgr % docker exec -it eventmgr-cassandra bash

The prompt should now reflect being root on the container.  And then go into Cassandra

    root@xxxxx:/#

    root@xxxxx:/# cqlsh

Create a keyspace, create the table we will use and then see a list of all tables in the keyspace & also info on the table just created:

    cqlsh> CREATE KEYSPACE eventmgr_keyspace WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

    cqlsh> USE eventmgr_keyspace;

    cqlsh:eventmgr_keyspace> 

        CREATE TABLE device (
            id UUID PRIMARY KEY,
            type TEXT,
            location TEXT,
            name TEXT
        );

        CREATE INDEX ON device (type);
        CREATE INDEX ON device (location);

        INSERT INTO device (id, name, type, location) VALUES (4d6ed554-7074-481d-ba4b-993b599e2cc0, 'FRONT DOOR', 'DOOR', 'BLDG 5');
        INSERT INTO device (id, name, type, location) VALUES (e42c73fa-db1e-4658-9a32-195b059a66b3, 'BACK DOOR',  'DOOR', 'BLDG 7');
        INSERT INTO device (id, name, type, location) VALUES (a303d7ac-3d22-4964-aaee-26d7dbefad48, 'CAFETERIA',  'THERMOMETER', 'BLDG A');

        CREATE TABLE event (
            id UUID,
            device_id UUID,
            datetime TIMESTAMP,
            type TEXT,
            data TEXT,
            PRIMARY KEY ((device_id), datetime, id)
        );

        CREATE INDEX ON event (datetime);

        INSERT INTO event (id, device_id, datetime, type, data)
        VALUES (8bb579cf-4e63-4586-81fd-8d6d54d6a60e, 4d6ed554-7074-481d-ba4b-993b599e2cc0, TOTIMESTAMP(now()), 'DOOR OPENED',  '{ "ACTOR": "HENRY", "METHOD": "KEYCARD" }');

        INSERT INTO event (id, device_id, datetime, type, data)
        VALUES (1e5afaba-b0f9-406b-9635-1cce727f0e8e, a303d7ac-3d22-4964-aaee-26d7dbefad48, TOTIMESTAMP(now()), 'TEMP CHECKED', '{ "TEMP": 98.5 }');

        INSERT INTO event (id, device_id, datetime, type, data)
        VALUES (0a6808cd-edc7-4fbc-b0f8-508360f7254e, 4d6ed554-7074-481d-ba4b-993b599e2cc0, TOTIMESTAMP(now()), 'DOOR CLOSED',  '{}');

        INSERT INTO event (id, device_id, datetime, type, data)
        VALUES (2560476e-4264-4b4e-9243-2ec125b669d0, 4d6ed554-7074-481d-ba4b-993b599e2cc0, TOTIMESTAMP(now()), 'DOOR OPENED',  '{ "ACTOR": "SALLY", "METHOD": "KEY" }');

    cqlsh:eventmgr_keyspace> DESC TABLES;

        event

    cqlsh:eventmgr_keyspace> DESC TABLE event;
    
        < list of the fields, as some characteristics regarding the table >

    cqlsh:eventmgr_keyspace> SELECT * FROM event;

        < the above-inserted record should be listed >

When done in the Cassandra instance, exit out back to the host's commandline.

Finally, when all the dev work is done, the instance of Cassandra running should be shutdown:

    ../mycassandra % docker-compose stop




In this structure, device_id is the partition key and event_time along with id are the clustering columns.
This allows you to handle cases where multiple events from the same device might occur at the same event_time, and the id (assuming it is unique for each event) provides Cassandra a way to differentiate these entries.
Remember, queries in Apache Cassandra must include a condition on the partition key and can optionally include conditions on the clustering columns. Utilizing clustering columns can provide order to your data and allow a range of values to be queried.