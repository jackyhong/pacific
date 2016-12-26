package com.techx.pacific.broker.store.topic;

import com.techx.pacific.broker.store.topic.util.LinkedNode;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Date: 16/12/25
 * MailTo: jackyhongvip@gmail.com
 */
public class DataFile extends LinkedNode<DataFile> implements Comparable<DataFile>{
    protected final File file;
    protected final int dataFileId;
    protected int length;

    DataFile(File file, int number){
        this.file = file;
        this.dataFileId = number;
        this.length = (int)(file.exists() ? file.length() : 0);
    }

    public File getFile(){
        return file;
    }

    public int getDataFileId(){
        return dataFileId;
    }

    public synchronized RandomAccessFile openRandomAccessFile() throws IOException {
        return new RandomAccessFile(file, "rw");
    }

    public synchronized void closeRandomAccessFile(RandomAccessFile file) throws IOException {
        file.close();
    }

    public int compareTo(DataFile df) {
        return dataFileId - df.dataFileId;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof DataFile) {
            result = compareTo((DataFile)o) == 0;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return dataFileId;
    }
}
