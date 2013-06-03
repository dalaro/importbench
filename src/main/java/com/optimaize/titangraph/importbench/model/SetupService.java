package com.optimaize.titangraph.importbench.model;

import com.optimaize.titangraph.importbench.lib.tx.TxTask;
import com.optimaize.titangraph.importbench.lib.util.BaseGraphService;
import com.optimaize.titangraph.importbench.lib.util.BaseSchemaSetup;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Set;

/**
 * Creates the db structure, automatically. Runs this in a transaction so that it's committed.
 * Feel free to refactor in case you don't want to run this code in all scenarios.
 */
@Service
public class SetupService extends BaseGraphService {

    @Inject
    private Set<BaseSchemaSetup> setups;

    @PostConstruct
    private void init() throws Exception {
        txRunner.run(new TxTask<Void>() {
            @Override
            public Void run() throws Exception {
                for (BaseSchemaSetup setup : setups) {
                    setup.run();
                }
                return null;
            }
        });
    }

}
