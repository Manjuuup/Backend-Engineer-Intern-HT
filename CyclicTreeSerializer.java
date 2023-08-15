package defaultpackage;

//II.Implement a TreeSerializer that takes into account a “cyclic tree”. Your implementation should throw a RuntimeException when a cyclic connection is found in the Tree. Bonus: create an implementation that works even with a “cyclic tree”. See “cyclic tree” example:
import java.util.*;

class Node5 {
    int num;
    Node5 left;
    Node5 right;

    Node5(int num) {
        this.num = num;
    }
}

interface TreeSerializer {
    String serialize(Node5 root);
    Node5 deserialize(String str);
}

public class CyclicTreeSerializer implements TreeSerializer {
    private Set<Node5> visitedNodes = new HashSet<>();
    private Set<Node5> currentPath = new HashSet<>(); // To track current path during traversal

    public String serialize(Node5 root) {
        visitedNodes.clear();
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }

    private void serializeHelper(Node5 node, StringBuilder sb) {
        if (node == null) {
            sb.append("null").append(",");
            return;
        }

        if (currentPath.contains(node)) {
            throw new RuntimeException("Cyclic connection found in the tree");
        }

        currentPath.add(node);
        sb.append(node.num).append(",");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
        currentPath.remove(node);
        visitedNodes.add(node);
    }

    public Node5 deserialize(String str) {
        Deque<String> nodes = new LinkedList<>(Arrays.asList(str.split(",")));
        visitedNodes.clear();
        return deserializeHelper(nodes);
    }

    private Node5 deserializeHelper(Deque<String> nodes) {
        String val = nodes.poll();
        if (val.equals("null")) {
            return null;
        }
        Node5 node = new Node5(Integer.parseInt(val));

        if (visitedNodes.contains(node)) {
            throw new RuntimeException("Cyclic connection found in the tree");
        }

        visitedNodes.add(node);
        currentPath.add(node);
        node.left = deserializeHelper(nodes);
        node.right = deserializeHelper(nodes);
        currentPath.remove(node);
        return node;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter space-separated tree values: ");
        String[] values = scanner.nextLine().split(" ");
        Node5 root = buildTree(values, 0);

        CyclicTreeSerializer serializer = new CyclicTreeSerializer();

        try {
            String serialized = serializer.serialize(root);
            System.out.println("Serialized: " + serialized);
        } catch (RuntimeException e) {
            System.out.println("Error during serialization: " + e.getMessage());
        }

        try {
            Node5 deserialized = serializer.deserialize(serializer.serialize(root));
            System.out.println("Deserialized root value: " + deserialized.num);
        } catch (RuntimeException e) {
            System.out.println("Error during deserialization: " + e.getMessage());
        }

        scanner.close();
    }

    private static Node5 buildTree(String[] values, int index) {
        if (index >= values.length || values[index].equals("null")) {
            return null;
        }

        Node5 node = new Node5(Integer.parseInt(values[index]));
        node.left = buildTree(values, 2 * index + 1);
        node.right = buildTree(values, 2 * index + 2);

        return node;
    }
}

