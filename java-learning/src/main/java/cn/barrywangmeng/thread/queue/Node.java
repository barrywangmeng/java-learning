package cn.barrywangmeng.thread.queue;

/**
 * @Description:
 * @Author: wangmeng
 * @Date: 2018/12/12-23:11
 */
public class Node implements Comparable<Node> {
    private int id;
    private String name;

    public Node() {
    }

    public Node(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Node node) {
        return Integer.compare(this.id, node.id);
    }

    public String toString() {
        return this.id + " : " + this.name;
    }
}
