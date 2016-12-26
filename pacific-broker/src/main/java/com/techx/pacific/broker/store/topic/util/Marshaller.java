package com.techx.pacific.broker.store.topic.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Date: 16/12/25
 * MailTo: jackyhongvip@gmai.com
 */
public interface Marshaller<T> {
    /**
     * Write the payload of the object to the DataOutput stream.
     *
     * @param object
     * @param dataOut
     * @throws IOException
     */
    void writePayload(T object, DataOutput dataOut) throws IOException;


    /**
     * Read the payload of the object from the DataInput stream.
     *
     * @param dataIn
     * @return unmarshalled object
     * @throws IOException
     */
    T readPayload(DataInput dataIn) throws IOException;

    /**
     * @return -1 if the object do not always marshall to a fixed size, otherwise return that fixed size.
     */
    int getFixedSize();

    /**
     *
     * @return true if the {@link #deepCopy(Object)} operations is supported.
     */
    boolean isDeepCopySupported();

    /**
     * @return a deep copy of the source object.
     */
    T deepCopy(T source);
}
