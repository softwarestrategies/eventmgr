# EventMgr

## About

This is just a simple sample project to (re)learn working directly with Cassandra and also integrating a Spring Boot app with it.

The system has some of what one might want in a system that tracks Devices and the Events occuring on those devices.  

It is by no means comprehensive final pass for a real world system, but it does have enough to get there pretty quickly and will be something I use if there's a call for it.  Be gentle in your judgement of it all.

## Dependencies

There are a few dependencies to consider, all of which can easily be changed to match your environment and/or preferences.

First, I am using Java 21 and Spring Boot 3.3.  If you don't (or can't) use these, you can change the versions in the build.gradle file.  

That being said - I also am using the new Spring Boot directive for using virtual threads.  It can be found in the application.properties file and can be removed:

    spring.threads.virtual.enabled=true

Also, you will want to have Gradle and Docker installed, though you can of course tweak it to use other means of testing it.

## Setup Cassandra and the Data Model

You will notice that there is a docker-compose.yml file in the root directory.  Some of the values found there can also be found correspondingly in the application's application.properties file.

So, from the command-line start it up:

    ../eventmgr % docker-compose up -d

Then you can see if the "eventmgr-cassandra" container is up-and-running and if you want see the logs from when it started up:

    ../eventmgr % docker ps

    ../eventmgr % docker logs eventmgr-cassandra

Now, log onto the Cassandra docker container:

    ../eventmgr % docker exec -it eventmgr-cassandra bash

The prompt should now reflect being root on the container.  And then go into Cassandra

    root@xxxxx:/#

    root@xxxxx:/# cqlsh

Create a keyspace, switch focus to it and then create & populate the tables that can be used here:

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

If you want to see what tables are in the keyspace, want to see info on a specifice table, or want to query a table -- you can do the following:

    cqlsh:eventmgr_keyspace> DESC TABLES;

        event

    cqlsh:eventmgr_keyspace> DESC TABLE event;
    
        < list of the fields, as some characteristics regarding the table >

    cqlsh:eventmgr_keyspace> SELECT * FROM event;

        < the above-inserted record should be listed >

You can exit out of csqlsh and then ultimately the container by exiting a few times.  Don't stop the container itself yet, though.

Now start up the Java application -- either from within your IDE or from the commandline.  From the OS commandline , you can do the following:

    ../eventmgr % ./gradlew build

    ../eventmgr % java -jar ./build/libs/eventmgr-0.0.1-SNAPSHOT.jar

If it works, you should see a lot of log output and ultimately this line:

    Tomcat started on port 8080 (http) with context path '/eventmgr'

Using either curl or a tool like Postman, try hitting these endpoints with GET calls and you should see the data inserted above in Cassandra:

    localhost:8080/eventmgr/api/v1/devices/4d6ed554-7074-481d-ba4b-993b599e2cc0

    localhost:8080/eventmgr/api/v1/devices/location/BLDG A

    localhost:8080/eventmgr/api/v1/events/device/a303d7ac-3d22-4964-aaee-26d7dbefad48

AND for testing POST, use this (with the JSON in the request's body) and you should get a 201 response.  Then do a GET again with using the same deviceId as above & you should now see this new record:

    localhost:8080/eventmgr/api/v1/events

        {
            "deviceId": "a303d7ac-3d22-4964-aaee-26d7dbefad48",
            "type": "TEMP CHECKED",
            "data": "{ \"TEMP\": 107.5 }"
        }

        localhost:8080/eventmgr/api/v1/events/device/a303d7ac-3d22-4964-aaee-26d7dbefad48

You can now stop the Java application.  And if fully done, you should also stop the Cassandra container:

    ../mycassandra % docker-compose stop

## Helpful Resources

https://towardsdatascience.com/when-to-use-cassandra-and-when-to-steer-clear-72b7f2cede76
https://www.baeldung.com/cassandra-keys
https://www.baeldung.com/cassandra-object-mapping-datastax-java-driver
https://www.baeldung.com/spring-data-cassandra-dates
https://medium.com/javarevisited/spring-data-with-apache-cassandra-database-composite-key-3c43a2dc7685



In this structure, device_id is the partition key and event_time along with id are the clustering columns.
This allows you to handle cases where multiple events from the same device might occur at the same event_time, and the id (assuming it is unique for each event) provides Cassandra a way to differentiate these entries.
Remember, queries in Apache Cassandra must include a condition on the partition key and can optionally include conditions on the clustering columns. Utilizing clustering columns can provide order to your data and allow a range of values to be queried.