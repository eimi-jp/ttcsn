package entity;

import java.util.Objects;

/**
 * Đại diện cho một nút trong thuật toán A*
 */

public class Node implements Comparable<Node> {

    public int x;
    public int y;

    public int gCost; // Chi phí từ điểm bắt đầu đến nút hiện tại
    public int hCost; // Chi phí ước tính từ nút hiện tại đến đích
    public int fCost; // Tổng chi phí: fCost = gCost + hCost
    
    public Node parent; // Nút cha để truy vết đường đi

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.gCost = Integer.MAX_VALUE;
        this.fCost = Integer.MAX_VALUE;
        this.parent = null;
    }

    public void calculateFCost() {
        this.fCost = this.gCost + this.hCost;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.fCost, other.fCost);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
