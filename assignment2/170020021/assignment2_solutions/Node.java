package assignment2_solutions;

public class Node {
    int value;
    Node parent, left, right;

    public Node(int value) {
        this.value = value;
        parent = left = right = null;
    }
}