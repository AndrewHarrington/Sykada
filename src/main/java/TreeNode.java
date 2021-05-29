public class TreeNode<T> extends Node<T> {

    private static final int DEFAULT_ARRAY_SIZE = 94;
    private final Node<T>[] children;
    private boolean flag = false;

    public TreeNode() {
        this(null);
    }

    public TreeNode(T data) {
        this.setData(data);
        this.children = new Node[DEFAULT_ARRAY_SIZE];
    }

    public TreeNode(T data, int size) {
        this.setData(data);
        this.children = new Node[size];
    }

    public Node[] getChildren() {
        return children;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    void addChild(T child, int pos) {
        this.addChild(new TreeNode<>(child), pos);
    }

    void addChild(Node<T> child, int pos) {
        this.children[pos] = child;
    }
}
