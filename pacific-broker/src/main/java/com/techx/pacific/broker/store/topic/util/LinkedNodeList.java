package com.techx.pacific.broker.store.topic.util;

import java.util.ArrayList;

/**
 *  IMPORTANT TIPS:  Copy from ActiveMQ
 *
 * Provides a list of LinkedNode objects.
 *
 * @author chirino
 */
public class LinkedNodeList <T extends LinkedNode<T>> {

    T head;
    int size;

    public LinkedNodeList() {
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void addLast(T node) {
        node.linkToTail(this);
    }

    public void addFirst(T node) {
        node.linkToHead(this);
    }

    public T getHead() {
        return head;
    }

    public T getTail() {
        return head != null ? head.prev : null;
    }

    public void clear() {
        while (head != null) {
            head.unlink();
        }
    }

    public void addLast(LinkedNodeList<T> list) {
        if (list.isEmpty()) {
            return;
        }
        if (head == null) {
            head = list.head;
            reparent(list);
        } else {
            getTail().linkAfter(list);
        }
    }

    public void addFirst(LinkedNodeList<T> list) {
        if (list.isEmpty()) {
            return;
        }
        if (head == null) {
            reparent(list);
            head = list.head;
            list.head = null;
        } else {
            getHead().linkBefore(list);
        }
    }

    public T reparent(LinkedNodeList<T> list) {
        size += list.size;
        T n = list.head;
        do {
            n.list = this;
            n = n.next;
        } while (n != list.head);
        list.head = null;
        list.size = 0;
        return n;
    }

    /**
     * Move the head to the tail and returns the new head node.
     *
     * @return
     */
    public T rotate() {
        if (head == null) return null;
        return head = head.getNextCircular();
    }

    /**
     * Move the head to the tail and returns the new head node.
     *
     * @return
     */
    public void rotateTo(T head) {
        assert head != null : "Cannot rotate to a null head";
        assert head.list == this : "Cannot rotate to a node not linked to this list";
        this.head = head;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean first = true;
        T cur = getHead();
        while (cur != null) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(cur);
            first = false;
            cur = cur.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copies the nodes of the LinkedNodeList to an ArrayList.
     *
     * @return
     */
    public ArrayList<T> toArrayList() {
        ArrayList<T> rc = new ArrayList<T>(size);
        T cur = head;
        while (cur != null) {
            rc.add(cur);
            cur = cur.getNext();
        }
        return rc;
    }
}
