import java.util.Collection;
import java.util.LinkedList;

public abstract class Node<T> {
    private T data = null;
    private Collection<Node<T>> subNodes = new LinkedList<>();

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void addSubNode(Node<T> node) {
        subNodes.add(node);
    }

    public Collection<Node<T>> getSubNodes() {
        return new LinkedList<>(this.subNodes);
    }

    abstract Node[] getChildren();
}
