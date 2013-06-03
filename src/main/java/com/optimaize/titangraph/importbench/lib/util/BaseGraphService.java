package com.optimaize.titangraph.importbench.lib.util;

import com.google.common.base.Optional;
import com.optimaize.titangraph.importbench.lib.GraphProvider;
import com.optimaize.titangraph.importbench.lib.tx.TxRunner;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import javax.inject.Inject;

/**
 * Base for services that operate on the graph.
 */
public abstract class BaseGraphService {

    protected TitanGraph g;
    @Inject
    private void setGraphProvider(GraphProvider graphProvider) {
        Graph graph = graphProvider.get();
        if (!(graph instanceof TitanGraph)) {
            throw new IllegalArgumentException("Graph must be an instance of TitanGraph!");
        }
        g = (TitanGraph)graph;
    }

    @Inject
    protected TxRunner txRunner;


    protected Optional<Vertex> getById(String id) {
        Vertex vertex = g.getVertex(Long.parseLong(id));
        return Optional.fromNullable(vertex);
    }

    /**
     * @throws IllegalArgumentException In case the vertex for that id exists, but is not of the required <code>type</code>.
     */
    protected Optional<Vertex> getById(String id, String type) throws IllegalArgumentException {
        Optional<Vertex> vertex = getById(id);
        if (vertex.isPresent()) {
            verifyType(id, type, vertex.get());
        }
        return vertex;
    }

    private void verifyType(String id, String type, Vertex vertex) {
        String havingType = vertex.getProperty("type");
        if (havingType==null) throw new IllegalStateException("Vertex must have a type property!");
        if (!type.equals(havingType)) {
            throw new IllegalArgumentException("Vertex "+id+" not of expected type "+type+" but of type "+havingType+"!");
        }
    }

}
