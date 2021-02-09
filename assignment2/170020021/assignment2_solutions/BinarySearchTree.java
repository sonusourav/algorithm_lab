package assignment2_solutions;

import java.util.ArrayList;

public class BinarySearchTree {

    Node root;
    ArrayList<Integer> traversalList = new ArrayList<>();

    BinarySearchTree() {
        this.root = null;
    }

    // Insert new value to BST
    public Node insert(Node curNode, int newValue) {
        if (curNode == null) {
            return new Node(newValue);
        } else {
            Node temp = null;
            if (newValue < curNode.value) {
                temp = insert(curNode.left, newValue);
                curNode.left = temp;
                temp.parent = curNode;
            } else {
                temp = insert(curNode.right, newValue);
                curNode.right = temp;
                temp.parent = curNode;
            }

            return curNode;
        }
    }

    public String traversal(int type) {
        traversalList = new ArrayList<>();
        switch (type) {
            case 0:
                inorderTraversal(root);
                break;
            case 1:
                preorderTraversal(root);
                break;
            case 2:
                postorderTraversal(root);
                break;
        }
        StringBuilder builder = new StringBuilder();
        if (traversalList.size() > 0)
            builder.append(traversalList.get(0));
        for (int i = 1; i < traversalList.size(); i++) {
            builder.append(" " + traversalList.get(i));
        }
        return builder.toString().trim();
    }

    // Inorder Traversal
    public void inorderTraversal(Node curNode) {
        if (curNode == null) {
            return;
        }

        inorderTraversal(curNode.left);
        traversalList.add(curNode.value);
        inorderTraversal(curNode.right);
    }

    // Preorder Traversal
    public void preorderTraversal(Node curNode) {
        if (curNode == null) {
            return;
        }

        traversalList.add(curNode.value);
        preorderTraversal(curNode.left);
        preorderTraversal(curNode.right);
    }

    // Postorder Traversal
    public void postorderTraversal(Node curNode) {
        if (curNode == null) {
            return;
        }

        postorderTraversal(curNode.left);
        postorderTraversal(curNode.right);
        traversalList.add(curNode.value);
    }

    // Search if the given value exists in the BST
    public boolean search(Node curNode, int valToSearch) {
        if (curNode == null) {
            return false;
        }

        if (curNode.value == valToSearch) {
            return true;
        }

        boolean leftSearch = search(curNode.left, valToSearch);
        return (leftSearch == true) ? true : search(curNode.right, valToSearch);
    }

    // Find the minimum value in the BST
    public int minimumNode(Node curNode) {
        if (curNode == null) {
            return -1;
        }

        Node currentNode = curNode;
        while (currentNode.left != null) {
            currentNode = currentNode.left;
        }

        return currentNode.value;
    }

    // Find the maximum value in the BST
    public int maximumNode(Node curNode) {
        if (curNode == null) {
            return -1;
        }

        Node currentNode = curNode;
        while (currentNode.right != null) {
            currentNode = currentNode.right;
        }

        return currentNode.value;
    }

    // Get the node with given value
    private Node getNodeWithGivenValue(Node curNode, int valueToSearch) {
        if (curNode == null || curNode.value == valueToSearch)
            return curNode;

        if (curNode.value > valueToSearch)
            return getNodeWithGivenValue(curNode.left, valueToSearch);

        return getNodeWithGivenValue(curNode.right, valueToSearch);
    }

    // Find the successor of given val.
    // Return -2 if the given val is not found
    // Return -1 if the successor does not exist
    public int successor(int val) {
        Node searchedNode = getNodeWithGivenValue(root, val);
        // If node with given value does not exist
        if (searchedNode == null) {
            return -2;
        }

        // If node with given value has right node, then minimum value in the right
        // subtree is its successor
        if (searchedNode.right != null) {
            return minimumNode(searchedNode.right);
        }

        // If node with given value has parent node, then parent is its successor
        Node tempNode = searchedNode;
        Node parentNode = tempNode.parent;
        while (parentNode != null && tempNode == parentNode.right) {
            tempNode = parentNode;
            parentNode = parentNode.parent;
        }

        if (parentNode != null) {
            return parentNode.value;
        } else {
            // Successor does not exist
            return -1;
        }
    }

    // Find the successor of given val.
    // Return -2 if the given val is not found
    // Return -1 if the successor does not exist
    public int predecessor(int val) {
        Node searchedNode = getNodeWithGivenValue(root, val);
        // If node with given value does not exist
        if (searchedNode == null) {
            return -2;
        }

        // If node with given value has left node, then maximum value in the left
        // subtree is its predecessor
        if (searchedNode.left != null) {
            return maximumNode(searchedNode.left);
        }

        // If node with given value has parent node, then parent is its predecessor,
        // if the current node is in the right of parent
        Node tempNode = searchedNode;
        Node parentNode = tempNode.parent;
        while (parentNode != null && tempNode == parentNode.left) {
            tempNode = parentNode;
            parentNode = parentNode.parent;
        }

        if (parentNode != null) {
            return parentNode.value;
        } else {
            // Predecessor does not exist
            return -1;
        }
    }

}