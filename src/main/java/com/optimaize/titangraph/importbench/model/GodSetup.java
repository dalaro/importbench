package com.optimaize.titangraph.importbench.model;

import com.optimaize.titangraph.importbench.lib.util.BaseSchemaSetup;
import com.thinkaurelius.titan.core.TitanKey;
import com.thinkaurelius.titan.core.TypeMaker;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;
import org.springframework.stereotype.Service;

/**
 * Creates the graph structure for the God vertex.
 */
@Service
public class GodSetup extends BaseSchemaSetup {


    private TitanKey type;
    private TitanKey name;
    private TitanKey power;
    private TitanKey alive;


    public void run() {
        type = getOrMakeKey("type", new TypeDef() {
            public TypeMaker makeType(TypeMaker typeMaker) {
                return typeMaker.dataType(String.class).unique(Direction.OUT);
            }
        });
        name = getOrMakeKey("god_name", new TypeDef() {
            public TypeMaker makeType(TypeMaker typeMaker) {
                return typeMaker.dataType(String.class).indexed(Vertex.class).unique(Direction.OUT);
            }
        });
        power = getOrMakeKey("god_power", new TypeDef() {
            public TypeMaker makeType(TypeMaker typeMaker) {
                return typeMaker.dataType(Integer.class).unique(Direction.OUT);
            }
        });
        alive = getOrMakeKey("god_alive", new TypeDef() {
            public TypeMaker makeType(TypeMaker typeMaker) {
                return typeMaker.dataType(Boolean.class).unique(Direction.OUT);
            }
        });
    }

}
