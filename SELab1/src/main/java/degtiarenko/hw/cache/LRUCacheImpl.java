package degtiarenko.hw.cache;

import org.contract4j5.contract.Contract;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Contract
public class LRUCacheImpl<K, V> implements Cache<K, V> {
    private final int maxCashSize;
    private final ConcurrentMap<K, Node> nodes = new ConcurrentHashMap<>();

    private volatile Node first;
    private volatile Node last;

    public LRUCacheImpl(int size) {
        maxCashSize = size;
    }

    public synchronized V get(K key) {
        assert nodes.size() <= maxCashSize;

        V result = doGet(key);

        assert ((result != null && first.getValue() == result) || (result == null && nodes.get(key) == null));
        return result;
    }

    private V doGet(K key) {
        Node resultNode = nodes.get(key);
        if (resultNode != null) {
            setFirst(resultNode);
            return resultNode.getValue();
        }
        return null;
    }

    public synchronized V put(K key, V value) {
        assert nodes.size() <= maxCashSize;

        V result = doPut(key, value);

        assert get(key) == value;
        assert nodes.size() <= maxCashSize;
        assert (value == null && nodes.size() == 0) || last != null;
        assert value == null || first.getValue() == value;
        return result;
    }

    private V doPut(K key, V value) {
        Node newNode = new Node(key, value);
        Node oldNode = nodes.get(key);
        if (oldNode != null) {
            deleteNode(oldNode);
        } else if (last == null) {
            last = newNode;
        }
        setFirst(newNode);
        nodes.put(key, newNode);
        adjustCacheSize();
        return oldNode == null ? null : oldNode.getValue();
    }

    private void setFirst(Node resultNode) {
        if (first != null) {
            orderNodes(resultNode, first);
            if (first.getPrevious() == resultNode) {
                first.setPrevious(resultNode.getPrevious());
            }
        }
        if (last == resultNode && resultNode.getNext() != null) {
            last = resultNode.getNext();
        }
        resultNode.setNext(null);
        first = resultNode;
    }

    private void adjustCacheSize() {
        if (nodes.size() == maxCashSize + 1) {
            nodes.remove(last.getKey());
            last = last.getNext();
        }
    }

    private void deleteNode(Node oldNode) {
        if (last == oldNode && oldNode.getNext() != null) {
            last = oldNode.getNext();
        }
        orderNodes(oldNode.getNext(), oldNode.getPrevious());
    }

    private void orderNodes(Node first, Node second) {
        second.setNext(first);
        first.setPrevious(second);
    }

    private class Node {
        private final K key;
        private final V value;
        private Node next;
        private Node previous;

        private Node(K key, V value) {
            this.value = value;
            this.key = key;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrevious() {
            return previous;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }
    }
}
