package com.optimaize.titangraph.importbench.lib.util;

import com.thinkaurelius.titan.core.TitanKey;
import com.thinkaurelius.titan.core.TitanLabel;
import com.thinkaurelius.titan.core.TitanType;
import com.thinkaurelius.titan.core.TypeMaker;

/**
 * Base for services that set up schema (create TitanKey and TitanLabel).
 */
public abstract class BaseSchemaSetup extends BaseGraphService {

    public abstract void run();

    protected static interface TypeDef {
        TypeMaker makeType(TypeMaker typeMaker);
    }

    protected TitanKey getOrMakeKey(String name, TypeDef typeDef) {
        TitanType titanKey = g.getType(name);
        if (titanKey!=null) {
            if (!(titanKey instanceof TitanKey)) {
                throw new IllegalStateException("A thing (probably a TitanLabel) with the name >>>"+name+"<<< exists already!");
            }
            return (TitanKey)titanKey;
        }
        return typeDef.makeType(g.makeType().name(name)).makePropertyKey();
    }

    protected TitanLabel getOrMakeLabel(String name, TypeDef typeDef) {
        TitanType titanKey = g.getType(name);
        if (titanKey!=null) {
            if (!(titanKey instanceof TitanLabel)) {
                throw new IllegalStateException("A thing (probably a TitanKey) with the name >>>"+name+"<<< exists already!");
            }
            return (TitanLabel)titanKey;
        }
        return typeDef.makeType(g.makeType().name(name)).makeEdgeLabel();
    }

}
