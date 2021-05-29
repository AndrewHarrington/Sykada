import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Graph<T extends Comparable<T>> {
    private final Set<GraphNode<T>> nodes = new TreeSet<>();

    public Graph() {
    }

    public Graph(Set<T> input) {
        for (T data : input) {
            addChild(data);
        }
    }

    public Set<T> getNodes() {
        Set<T> output = new HashSet<T>();
        for (GraphNode<T> node : nodes) {
            output.add(node.getData());
        }
        return output;
    }

    public boolean addChild(T data) {
        if (this.containsChild(data)) {
            //update the value within the network with the new info
            GraphNode<T> node = this.findNode(data);
            node.setData(data);
        }
        return nodes.add(new GraphNode<>(data));
    }

    public boolean containsChild(T data) {
        return nodes.contains(new GraphNode<>(data));
    }

    public void removeChild(T data) {
        for (GraphNode<T> node : nodes) {
            if (node.getData().equals(data)) {
                nodes.remove(node);
                return;
            }
        }
        throw new IllegalArgumentException("Graph does not contain " + data.toString());
    }

    public void addConnection(T parent, T child) {
        //must contain the x element
        if (parent.equals(child) || !this.containsChild(parent)) {
//            System.out.println("Could not add " + ((Word)child).getWord() + " to " + ((Word)parent).getWord());
            return;
        }

        if (!this.containsChild(child)) {
            Word childWord = (Word) child;
            childWord.setWord(childWord.getWord().trim());
            String word = childWord.getWord();
            if (word.contains(" ") || word.length() <= 1) {
//                System.out.println(word + " could not be added");
                return;
            }
//            System.out.println(word + " was added to the graph");
            this.addChild((T) childWord);
        }

        GraphNode<T> pnode = findNode(parent);
        GraphNode<T> cnode = findNode(child);
        assert pnode != null;
        assert cnode != null;
        pnode.addChild(cnode);
    }

    public GraphNode<T> graphInject(T search) {
        for (GraphNode<T> g : nodes) {
            if (g.getData().equals(search)) {
                return g;
            }
        }
        return null;
    }

    protected T find(T data) {
        return Objects.requireNonNull(findNode(data)).getData();
    }

    private GraphNode<T> findNode(T data) {
        if (!this.containsChild(data)) {
            throw new IllegalArgumentException("Graph does not contain " + data.toString());
        }
        for (GraphNode<T> node : nodes) {
            if (node.getData().equals(data)) {
                return node;
            }
        }
        return null;
    }
}
