package com.techx.pacific.broker.store.topic;

import com.techx.pacific.broker.store.topic.util.ByteSequence;
import com.techx.pacific.broker.store.topic.util.DataByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Manages DataFiles
 * Date: 16/12/25
 * MailTo: jackyhongvip@gmail.com
 */
public class Journal {
    private static Logger logger = LoggerFactory.getLogger(Journal.class);

    public static final int RECORD_HEAD_SPACE = 4 + 1;
    public static final int BATCH_CONTROL_RECORD_TYPE = 2;
    public static final byte[] BATCH_HEAD_RECORD_MAGIC = bytes("WRITE BATCH");
    public static final byte[] BATCH_TAIL_RECORD_MAGIC = bytes("BATCH WRITTEN");
    public static final int BATCH_CONTROL_RECODE_SIZE = RECORD_HEAD_SPACE +
            BATCH_HEAD_RECORD_MAGIC.length + 4 + 8;
    public static final int BATCH_TAIL_RECORD_MAGIC_SIZE = BATCH_TAIL_RECORD_MAGIC.length;
    public static final byte[] BATCH_CONTROL_RECORD_HEADER = createBatchControlRecordHeader();


    private static byte[] createBatchControlRecordHeader(){
        DataByteArrayOutputStream os = new DataByteArrayOutputStream();
        try {
            //record size
            os.writeInt(BATCH_CONTROL_RECODE_SIZE);
            //record type
            os.writeByte(BATCH_CONTROL_RECORD_TYPE);
            //head magic
            os.write(BATCH_HEAD_RECORD_MAGIC);
            ByteSequence sequence = os.toByteSequece();
            sequence.compact();
            return sequence.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static byte[] bytes(String string) {
        try {
            return string.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
