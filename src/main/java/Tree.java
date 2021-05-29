import java.util.LinkedList;
import java.util.Queue;

public class Tree<T> {
    private Node<T> head;

    public Tree() {
        this.head = new TreeNode<>();
    }

    public Tree(TreeNode<T> head) {
        this.head = head;
    }

    public Node<T> getHead() {
        return head;
    }

    public void setHead(TreeNode<T> head) {
        this.head = head;
    }

    public Node findNode(T data) {
        Queue<Node> process = new LinkedList<>();
        process.add(head);

        while (!process.isEmpty()) {
            for (Node child : process.poll().getChildren()) {
                process.add(child);
                if (child.getData().equals(data)) {
                    return child;
                }
            }
        }
        return null;
    }
}
