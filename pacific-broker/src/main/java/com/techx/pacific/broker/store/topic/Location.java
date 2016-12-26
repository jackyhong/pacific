package com.techx.pacific.broker.store.topic;

/**
 * Date: 16/12/25
 * MailTo: jackyhongvip@gmail.com
 */
public final class Location implements Comparable<Location> {
    private static final int NOT_SET = -1;
    private static final int NOT_SET_TYPE = 0;
    private int dataFileId = NOT_SET;
    private int offset = NOT_SET;
    private int size = NOT_SET;
    private int type = NOT_SET_TYPE;

    public Location() {
    }

    public Location(Location item) {
        this.dataFileId = item.dataFileId;
        this.offset = item.offset;
        this.size = item.size;
        this.type = item.type;
    }

    public Location(int dataFileId, int offset) {
        this.dataFileId = dataFileId;
        this.offset = offset;
    }

    public int getSize(){
        return size;
    }

    public int getDataFileId() {
        return dataFileId;
    }

    public void setDataFileId(int dataFileId) {
        this.dataFileId = dataFileId;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return dataFileId + ":" + offset;
    }

    @Override
    public int compareTo(Location o) {
        Location l = (Location)o;
        if (dataFileId == l.dataFileId) {
            int rc = offset - l.offset;
            return rc;
        }
        return dataFileId - l.dataFileId;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof Location) {
            result = compareTo((Location)o) == 0;
        }
        return result;
    }
    @Override
    public int hashCode() {
        return dataFileId ^ offset;
    }

}
