package com.techx.pacific.broker.store.topic.util;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UTFDataFormatException;

/**
 * Date: 16/12/25
 * MailTo: jackyhongvip@gmail.com
 */
public class DataByteArrayOutputStream extends OutputStream implements DataOutput {
    private static final int DEFAULT_SIZE = 1024 * 4;
    protected byte buf[];
    protected int pos;

    public DataByteArrayOutputStream(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Invalid size : " + size);
        }
        buf = new byte[size];
    }

    public DataByteArrayOutputStream() {
        this(DEFAULT_SIZE);
    }

    public void restart(int size) {
        buf = new byte[size];
        pos = 0;
    }

    public void restart() {
        restart(DEFAULT_SIZE);
    }


    public ByteSequence toByteSequece() {
        return new ByteSequence(buf, 0, pos);
    }

    private void ensureEnoughBuffer(int newCount) {
        if (newCount > buf.length) {
            byte newBuf[] = new byte[Math.max(buf.length + 1024 * 1024, newCount)];
            System.arraycopy(buf, 0, newBuf, 0, pos);
            buf = newBuf;
        }
    }

    /**
     * This method allow subclasses to task some action based on the writes
     */
    protected void onWrite() {
    }

    public void write(byte b[], int off, int len) throws IOException {
        if(len == 0){
            return;
        }

        int newCount = pos + len;
        ensureEnoughBuffer(newCount);
        System.arraycopy(b, off, buf, pos, len);
        pos = newCount;
        onWrite();
    }

    @Override
    public void writeBoolean(boolean v) throws IOException {
        ensureEnoughBuffer(pos + 1);
        buf[pos++] = (byte) (v ? 1 : 0);
        onWrite();
    }

    @Override
    public void writeByte(int v) throws IOException {
        ensureEnoughBuffer(pos + 1);
        buf[pos++] = (byte) (v >>> 0);
        onWrite();
    }

    @Override
    public void writeShort(int v) throws IOException {
        ensureEnoughBuffer(pos + 2);
        buf[pos++] = (byte) (v >>> 8);
        buf[pos++] = (byte) (v >>> 0);

    }

    @Override
    public void writeChar(int v) throws IOException {
        ensureEnoughBuffer(pos + 2);
        buf[pos++] = (byte) (v >>> 8);
        buf[pos++] = (byte) (v >>> 0);
        onWrite();
    }

    @Override
    public void writeInt(int v) throws IOException {
        ensureEnoughBuffer(pos + 4);
        buf[pos++] = (byte) (v >>> 24);
        buf[pos++] = (byte) (v >>> 16);
        buf[pos++] = (byte) (v >>> 8);
        buf[pos++] = (byte) (v >>> 0);
        onWrite();
    }

    @Override
    public void writeLong(long v) throws IOException {
        ensureEnoughBuffer(pos + 8);
        buf[pos++] = (byte) (v >>> 56);
        buf[pos++] = (byte) (v >>> 48);
        buf[pos++] = (byte) (v >>> 40);
        buf[pos++] = (byte) (v >>> 32);
        buf[pos++] = (byte) (v >>> 24);
        buf[pos++] = (byte) (v >>> 16);
        buf[pos++] = (byte) (v >>> 8);
        buf[pos++] = (byte) (v >>> 0);
        onWrite();
    }

    @Override
    public void writeFloat(float v) throws IOException {
        writeInt(Float.floatToIntBits(v));
    }

    @Override
    public void writeDouble(double v) throws IOException {
        writeLong(Double.doubleToLongBits(v));
    }

    @Override
    public void writeBytes(String s) throws IOException {
        int length = s.length();
        for (int i = 0; i < length; i++) {
            write((byte) s.charAt(i));
        }
    }

    @Override
    public void writeChars(String s) throws IOException {
        int length = s.length();
        for (int i = 0; i < length; i++) {
            int c = s.charAt(i);
            write((c >>> 8) & 0xFF);
            write((c >>> 0) & 0xFF);
        }
    }

    @Override
    public void writeUTF(String str) throws IOException {
        int strlen = str.length();
        int encodedsize = 0;
        int c;
        for (int i = 0; i < strlen; i++) {
            c = str.charAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                encodedsize++;
            } else if (c > 0x07FF) {
                encodedsize += 3;
            } else {
                encodedsize += 2;
            }
        }
        if (encodedsize > 65535) {
            throw new UTFDataFormatException("encoded string too long: " + encodedsize + " bytes");
        }
        ensureEnoughBuffer(pos + encodedsize + 2);
        writeShort(encodedsize);
        for (int i = 0; i < strlen; i++) {
            int charValue = str.charAt(i);
            if (charValue > 0 && charValue <= 127) {
                buf[pos++] = (byte) charValue;
            } else if (charValue <= 2047) {
                buf[pos++] = (byte) (0xc0 | (0x1f & (charValue >> 6)));
                buf[pos++] = (byte) (0x80 | (0x3f & charValue));
            } else {
                buf[pos++] = (byte) (0xe0 | (0x0f & (charValue >> 12)));
                buf[pos++] = (byte) (0x80 | (0x3f & (charValue >> 6)));
                buf[pos++] = (byte) (0x80 | (0x3f & charValue));
            }
        }
        onWrite();
    }

    @Override
    public void write(int b) throws IOException {
        ensureEnoughBuffer(pos + 1);
        buf[pos++] = (byte)b;
        onWrite();
    }
}
