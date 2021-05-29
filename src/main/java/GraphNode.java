import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

public class GraphNode<T extends Comparable<T>> extends Node<T> implements Comparable<GraphNode<T>> {

    protected Collection<Node<T>> children = new LinkedList<>();

    public GraphNode(T data) {
        this.setData(data);
    }

    public void addChild(T child) {
        this.children.add(new GraphNode<>(child));
    }

    public void addChild(Node<T> child) {
        if (!(child instanceof GraphNode)) {
            throw new IllegalArgumentException("Child is not a GraphNode");
        }
        this.children.add(child);
    }

    @Override
    public Node[] getChildren() {
        return children.toArray(new Node[0]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphNode<?> graphNode = (GraphNode<?>) o;
        return this.getData().equals(graphNode.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getData());
    }

    public int compareTo(GraphNode<T> o) {
        return super.getData().compareTo(o.getData());
    }
}
