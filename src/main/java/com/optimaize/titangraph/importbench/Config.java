package com.optimaize.titangraph.importbench;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;

import java.io.File;

/**
 */
public class Config {

    public static final int NUM_GODS = 10000000;
    public static final int NUM_GODS_PER_TX = 10000;
    public static final boolean CHECK_EXISTENCE = true;
    public static final String BERKELEY_PATH = "g:/graph-test";
    public static final String ELASTICSEARCH_PATH = null;


    public static Configuration getGraphConfiguration() {
        Configuration conf = new BaseConfiguration();
        berkeley(conf);
        return conf;
    }


    /**
     * This connects to the embedded berkeley db.
     * See https://github.com/thinkaurelius/titan/wiki/Example-Graph-Configuration
     */
    private static void berkeley(Configuration conf) {
        File path = new File(BERKELEY_PATH);
        if (!path.exists() || !path.canWrite()) throw new RuntimeException("Check your path config: "+path);
        conf.setProperty("storage.backend","berkeleyje");
        conf.setProperty("storage.directory",BERKELEY_PATH);
    }

    /**
     * This would be the code to connect to a standalone cassandra.
     * See https://github.com/thinkaurelius/titan/wiki/Using-Cassandra
     */
    private void configCassandraNetowkr(Configuration conf) {
        conf.setProperty("storage.backend","cassandra");
        conf.setProperty("storage.hostname","myhost"); //can be a comma-separated list
        conf.setProperty("storage.keyspace","MyKeySpace"); //titan 0.3.1
        conf.setProperty("storage.port","9160"); //default
        conf.setProperty("storage.thrift-timeout","10000"); //Default time out in milliseconds after which to fail a connection attempt with a Cassandra node
        conf.setProperty("storage.replication-factor","1");
    }

    private void configLocalElasticSearch(Configuration conf) {
        File path = new File(ELASTICSEARCH_PATH);
        if (!path.exists() || !path.canWrite()) throw new RuntimeException("Check your path config: "+path);
        conf.setProperty("storage.index.search.backend","elasticsearch");
        conf.setProperty("storage.index.search.directory",ELASTICSEARCH_PATH);
        conf.setProperty("storage.index.search.client-only",false);
        conf.setProperty("storage.index.search.local-mode",true);
    }

}
