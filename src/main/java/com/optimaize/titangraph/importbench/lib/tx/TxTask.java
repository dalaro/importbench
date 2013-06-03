package com.optimaize.titangraph.importbench.lib.tx;

/**
 * Transaction task.
 */
public interface TxTask<T> {

    T run() throws Exception;

}
