package com.optimaize.titangraph.importbench.lib.tx;

import com.optimaize.titangraph.importbench.lib.GraphProvider;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Graph;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 */
@Service
public class TitanTxRunner implements TxRunner {

    @Inject
    private void setProvider(GraphProvider provider) {
        Graph graph = provider.get();
        if (!(graph instanceof TitanGraph)) {
            throw new IllegalArgumentException("Provided Graph must be an instance of TitanGraph!");
        }
        g = (TitanGraph) graph;
    }
    private TitanGraph g;


    @Override
    public <T extends Object> T run(TxTask<T> task) throws Exception {
        T result = null;
        Exception ex = null;

        //DON'T start a new tx using g.startTransaction(); !!! see
        //

        boolean txCloseAttempted = false;
        try {
            try {
                result = task.run();
            } catch (Exception e) {
                ex = e;
            }
            if (ex != null) {
                try {
                    txCloseAttempted = true;
                    g.rollback();
                } catch (Exception e) {
                    //TODO log this one
                    e.printStackTrace();
                }
                //but throw the original ex.
                throw ex;
            } else {
                txCloseAttempted = true;
                g.commit();
                return result;
            }
        } finally {
            if (!txCloseAttempted) {
                g.rollback();
            }
        }
    }

}
