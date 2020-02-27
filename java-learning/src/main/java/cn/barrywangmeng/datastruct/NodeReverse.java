package cn.barrywangmeng.datastruct;

/**
 * @Description: 单链表反转
 * @Author: wangmeng
 * @Date: 2019/1/23-16:45
 */
public class NodeReverse {

    public static void main(String[] args) {
        Node head = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        head.setNext(node1);
        node1.setNext(node2);
        node2.setNext(node3);

        Node node = reverseNode(head);
        while (node != null) {
            System.out.println(node.getData());

            node = node.getNext();
        }
    }

    private static Node reverseNode(Node node) {
        if (node == null || node.getNext() == null) {
            return node;
        }

        Node reData = reverseNode(node.getNext());
        node.getNext().setNext(node);
        node.setNext(null);
        return reData;
    }

    static class Node {
        private int data;
        private Node next;

        public Node(int data) {
            this.data = data;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
