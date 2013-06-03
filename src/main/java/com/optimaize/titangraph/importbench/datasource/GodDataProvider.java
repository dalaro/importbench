package com.optimaize.titangraph.importbench.datasource;

import com.google.common.collect.UnmodifiableIterator;
import com.optimaize.titangraph.importbench.Config;
import org.apache.commons.lang.mutable.MutableInt;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 */
@Service
public class GodDataProvider implements Iterable<GodSourceRecord> {

    @Override
    public Iterator<GodSourceRecord> iterator() {
        return iterator(0);
    }

    public Iterator<GodSourceRecord> iterator(int offset) {
        final MutableInt currentPos = new MutableInt(offset);

        return new UnmodifiableIterator<GodSourceRecord>() {
            @Override
            public boolean hasNext() {
                return currentPos.intValue() < Config.NUM_GODS;
            }

            @Override
            public GodSourceRecord next() {
                if (!hasNext()) throw new NoSuchElementException();
                GodSourceRecord record = new GodSourceRecord(
                        "xGod_" + currentPos.intValue(),
                        computePower(currentPos.intValue()),
                        computeAlive(currentPos.intValue())
                );
                currentPos.increment();
                return record;
            }
        };
    }

    private int computePower(int i) {
        return i;
    }

    private boolean computeAlive(int i) {
        return i%2 == 0;
    }

}
