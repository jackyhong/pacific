package com.techx.pacific.broker.store.topic.util;

/**
 * Date: 16/12/25
 * MailTo: jackyhongvip@gmail.com
 */
public class ByteSequence {
    public byte[] data;
    public int offset;
    public int length;

    public ByteSequence() {
    }

    public ByteSequence(byte data[]) {
        this.data = data;
        this.offset = 0;
        this.length = data.length;
    }

    public ByteSequence(byte data[], int offset, int length) {
        this.data = data;
        this.offset = offset;
        this.length = length;
    }

    public byte[] getData() {
        return data;
    }

    public int getLength() {
        return length;
    }

    public int getOffset() {
        return offset;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void compact() {
        if (length != data.length) {
            byte t[] = new byte[length];
            System.arraycopy(data, offset, t, 0, length);
            data = t;
            offset = 0;
        }
    }

    public int indexOf(ByteSequence needle, int pos) {
        int max = length - needle.length;
        for (int i = pos; i < max; i++) {
            if (matches(needle, i)) {
                return i;
            }
        }
        return -1;
    }

    private boolean matches(ByteSequence needle, int pos) {
        for (int i = 0; i < needle.length; i++) {
            if( data[offset + pos+ i] != needle.data[needle.offset + i] ) {
                return false;
            }
        }
        return true;
    }

    public byte getByte(int i) {
        return data[offset+i];
    }

    final public int indexOf(byte value, int pos) {
        for (int i = pos; i < length; i++) {
            if (data[offset + i] == value) {
                return i;
            }
        }
        return -1;
    }
}
