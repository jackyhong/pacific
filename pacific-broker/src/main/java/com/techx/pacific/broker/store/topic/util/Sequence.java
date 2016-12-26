package com.techx.pacific.broker.store.topic.util;

/**
 * Date: 16/12/25
 * MailTo: jackyhongvip@gmail.com
 */
public class Sequence extends LinkedNode<Sequence> {
    long first;
    long last;

    public Sequence(long value) {
        first = last = value;
    }

    public Sequence(long first, long last) {
        this.first = first;
        this.last = last;
    }

    public boolean isAdjacentToLast(long value) {
        return last + 1 == value;
    }

    public boolean isAdjacentToFirst(long value) {
        return first - 1 == value;
    }

    public boolean contains(long value) {
        return first <= value && value <= last;
    }

    public long range() {
        return first == last ? 1 : (last - first) + 1;
    }

    @Override
    public String toString() {
        return first == last ? "" + first : first + "-" + last;
    }

    public long getFirst() {
        return first;
    }

    public void setFirst(long first) {
        this.first = first;
    }

    public long getLast() {
        return last;
    }

    public void setLast(long last) {
        this.last = last;
    }

    public interface Closure<T extends Throwable> {
        public void execute(long value) throws T;
    }

    public <T extends Throwable> void each(Closure<T> closure) throws T {
        for (long i = first; i <= last; i++) {
            closure.execute(i);
        }
    }
}
