package io.softwarestrategies.eventmgr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.lang.NonNull;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    @NonNull
    protected String getKeyspaceName() {
        return "eventmgr_keyspace";
    }

    @Override
    @NonNull
    protected String getContactPoints() {
        return "127.0.0.1";
    }

    @Override
    protected int getPort() {
        return 9042;
    }

    @Override
    @NonNull
    public String[] getEntityBasePackages() {
        return new String[] { "io.softwarestrategies.eventmgr" };
    }
}
