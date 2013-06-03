package com.optimaize.titangraph.importbench;

import com.google.common.base.Optional;
import com.google.common.base.Stopwatch;
import com.optimaize.titangraph.importbench.datasource.GodDataProvider;
import com.optimaize.titangraph.importbench.datasource.GodSourceRecord;
import com.optimaize.titangraph.importbench.lib.tx.TxRunner;
import com.optimaize.titangraph.importbench.lib.tx.TxTask;
import com.optimaize.titangraph.importbench.lib.util.BaseGraphService;
import com.tinkerpop.blueprints.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This imports just gods, no relations.
 */
@Service
public class Importer extends BaseGraphService {

    static final Logger logger = LoggerFactory.getLogger("importer");

    @Inject
    private GodDataProvider dataProvider;
    @Inject
    private TxRunner txRunner;

    public void run() throws Exception {
        Iterator<GodSourceRecord> iterator = dataProvider.iterator();
        Stopwatch stopWatch = new Stopwatch();
        int counter = 0;
        while (iterator.hasNext()) {
            List<GodSourceRecord> part = read(iterator);
            stopWatch.reset();
            stopWatch.start();
            importInTx(part);
            stopWatch.stop();
            counter += part.size();
            logger.info("... done " + counter + " so far "+stopWatch.elapsedMillis()+"ms");
        }
    }

    private void importInTx(final List<GodSourceRecord> sourceRecords) throws Exception {
        txRunner.run(new TxTask<Object>() {
            @Override
            public Object run() throws Exception {
                for (GodSourceRecord sourceRecord : sourceRecords) {
                    if (Config.CHECK_EXISTENCE) {
                        if (findByName(sourceRecord.getName()).isPresent()) {
                            continue;
                        }
                    }
                    insert(sourceRecord);
                }
                return null;
            }

            private Optional<Vertex> findByName(String name) {
                Iterator<Vertex> iterator = g.getVertices("god_name", name).iterator();
                if (iterator.hasNext()) return Optional.of(iterator.next());
                return Optional.absent();
            }

            private void insert(GodSourceRecord sourceRecord) {
                Vertex v = g.addVertex(null);
                v.setProperty("type",      "god");
                v.setProperty("god_name",  sourceRecord.getName());
                v.setProperty("god_power", sourceRecord.getPower());
                v.setProperty("god_alive", sourceRecord.isAlive());
            }
        });
    }



    private List<GodSourceRecord> read(Iterator<GodSourceRecord> iterator) {
        List<GodSourceRecord> bite = new ArrayList<>(Config.NUM_GODS_PER_TX);
        for (int i=0; i<Config.NUM_GODS_PER_TX; i++) {
            if (!iterator.hasNext()) break;
            bite.add(iterator.next());
        }
        return bite;
    }

}
