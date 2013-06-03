package com.optimaize.titangraph.importbench.lib.tx;

/**
 * Transaction runner.
 */
public interface TxRunner {

    /**
     * Runs the <code>task</code>.
     *
     * <p>Always closes the transaction.
     * If the given task throws, then the tx is aborted and the ex is thrown.
     * If the task succeeds but the commit fails then the tx is aborted, and the commit ex is thrown.
     * If both task and commit succeed then the task's return value is returned.
     * </p>
     */
    <T extends Object> T run(TxTask<T> task) throws Exception;

}
