package com.optimaize.titangraph.importbench;

import com.optimaize.titangraph.importbench.lib.GraphProvider;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Graph;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 */
@Service
public class TitanGraphProvider implements GraphProvider {

    private TitanGraph graph;

    @PostConstruct
    private void init() {
        graph = TitanFactory.open(Config.getGraphConfiguration());
    }

    @Override
    public Graph get() {
        return graph;
    }
}
