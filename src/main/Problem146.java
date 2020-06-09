package main;

import java.util.HashMap;
import java.util.Map;

/**
 * 运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put 。
 *
 * 获取数据 get(key) - 如果关键字 (key) 存在于缓存中，则获取关键字的值（总是正数），否则返回 -1。
 * 写入数据 put(key, value) - 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字/值」。当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
 *
 * 后感：
 * 刚开始是打算使用Map和List实现，List保存Key的顺序，后面在网上看到使用LinkNode实现的方式，才改用LinkNode双向链表+Map的方式实现。
 * 核心点在于对LRU的理解，看了Wikipedia(https://en.wikipedia.org/wiki/Cache_replacement_policies#Least_recently_used_(LRU)) 后才大致明白需要注意的地方，
 * 之前会犹豫，比如get时是否需要将key的Node移动到最前，从字面意思看是需要的，因为只要这个Key被使用过，那就不算是LRU。
 * 后面就主要在移动Node节点时出了点bug，经过在纸上反复模拟，最终也算做对了。
 * 看了别人的解题思路，最感慨的是，利用Java的LinkedHashMap，只需要21行代码就可以实现，而自己则是简单实现了LinkedHashMap。
 */
public class Problem146 {

    public static void main(String[] args) {
        LRUCache cache = new LRUCache( 2 /* 缓存容量 */ );

        /**
         * ["LRUCache","put","put","get","put","get","get"]
         * [[2],[2,1],[1,1],[2],[4,1],[1],[2]]
         */

        cache.put(2, 1);
        cache.put(1, 1);
        int v1 = cache.get(2);       // 返回  1
        cache.put(4, 1);
        int v2 = cache.get(1);       // 返回 -1 (未找到)
        int v3 = cache.get(2);       // 返回 1

    }

    public static class LRUCache {

        private Map<Integer, Integer> cache;
        private Map<Integer, LinkNode> nodes;
        private LinkNode head;
        private LinkNode tail;
        private int capacity;
        private int length = 0;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.cache = new HashMap<Integer, Integer>(capacity);
            this.nodes = new HashMap<Integer, LinkNode>(capacity);
            this.head = new LinkNode();
            this.tail = new LinkNode();

            // head -> tail
            this.head.setNext(tail);
            this.tail.setPrev(head);
        }

        public int get(int key) {
            if (cache.containsKey(key)) {
                LinkNode node = nodes.get(key);
                moveTop(node);
                return cache.get(key);
            }
            return -1;
        }

        public void put(int key, int value) {
            if (cache.containsKey(key)) {
                cache.put(key, value);
                LinkNode node = nodes.get(key);
                moveTop(node);
                return;
            }
            LinkNode node = new LinkNode();
            node.setKey(key);
            node.setValue(value);
            if (length >= this.capacity) {
                int popedKey = pop();
                cache.remove(popedKey);
                nodes.remove(popedKey);
            }
            // head -> tmp1 -> tmp2 -> tail
            LinkNode tmp = head.getNext();
            head.setNext(node);

            node.setPrev(head);
            node.setNext(tmp);

            tmp.setPrev(node);

            cache.put(key, value);
            nodes.put(key, node);

            length += 1;
        }

        private int pop() {
            LinkNode pop = tail.getPrev();
            LinkNode prev = pop.getPrev();
            tail.setPrev(prev);
            prev.setNext(tail);
            this.length -= 1;
            return pop.getKey();
        }

        private void moveTop(LinkNode node){
            LinkNode moveTo = head.getNext();
            if(node.getKey() != moveTo.getKey()) {
                node.getPrev().setNext(node.getNext());
                node.getNext().setPrev(node.getPrev());

                head.getNext().setPrev(node);
                node.setNext(head.getNext());

                head.setNext(node);
                node.setPrev(head);
            }
        }
    }

    public static class LinkNode {
        int key;
        int value;
        LinkNode prev = null;
        LinkNode next = null;

        public void setKey(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public void setPrev(LinkNode prev) {
            this.prev = prev;
        }

        public LinkNode getPrev() {
            return this.prev;
        }

        public void setNext(LinkNode next) {
            this.next = next;
        }

        public LinkNode getNext() {
            return this.next;
        }
    }

}