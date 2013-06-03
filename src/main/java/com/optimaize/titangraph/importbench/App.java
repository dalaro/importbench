package com.optimaize.titangraph.importbench;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * Runs the whole thing.
 * Before executing this check the {@link Config} class to adjust things for your machine.
 * I recommend running this with the vm options: -ea -server -Xmx400M
 */
public class App {

    static final Logger logger = LoggerFactory.getLogger("importer");


    public static void main( String[] args ) throws Exception {
        ClassPathXmlApplicationContext context = makeApplicationContext();

        Importer importer = context.getBean(Importer.class);

        logger.info("STARTING...");
        Stopwatch stopWatch = new Stopwatch().start();

        importer.run();

        stopWatch.stop();
        logger.info("DONE in "+stopWatch.elapsedTime(TimeUnit.MINUTES) + " minutes.");

        context.close();
    }


    protected static ClassPathXmlApplicationContext makeApplicationContext() {
        return new ClassPathXmlApplicationContext(
                "spring.xml"
        );
    }

}
