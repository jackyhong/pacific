package com.techx.pacific.broker.store.topic;

import com.techx.pacific.broker.store.topic.util.ByteSequence;
import com.techx.pacific.broker.store.topic.util.LinkedNode;
import com.techx.pacific.broker.store.topic.util.LinkedNodeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Date: 16/12/25
 * Time: 下午1:09
 */
public class DataFileAppender {
    private static final Logger logger = LoggerFactory.getLogger(DataFileAppender.class);

    public static class WriteKey {
        private final int file;
        private final long offset;
        private int hash;

        public WriteKey(Location location) {
            file = location.getDataFileId();
            offset = location.getOffset();
            hash = (int) (file ^ offset);
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof WriteKey) {
                WriteKey di = (WriteKey) obj;
                return di.file == file && di.offset == offset;
            }
            return false;
        }
    }

    public static class WriteCommand extends LinkedNode<WriteCommand> {
        public final Location location;
        public final ByteSequence byteSequence;
        public final boolean sync;
        public final Runnable onComplete;

        public WriteCommand(Location location, ByteSequence data, boolean sync) {
            this.location = location;
            this.byteSequence = data;
            this.sync = sync;
            this.onComplete = null;
        }

        public WriteCommand(Location location, ByteSequence data, Runnable onComplete){
            this.location = location;
            this.byteSequence = data;
            this.onComplete = onComplete;
            this.sync = false;
        }
    }

    public class WriteBatch {
        public final DataFile dataFile;
        public final LinkedNodeList<WriteCommand> writes = new LinkedNodeList<WriteCommand>();
        public final CountDownLatch latch = new CountDownLatch(1);
        private final int offset;
        public int size =

    }
}
