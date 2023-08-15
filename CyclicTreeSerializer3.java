package defaultpackage;
//III. Suggest changes that should be done in order to support any data type (as opposed to only an int data type)
//To support any data type in the binary tree, you can use Java's generics. You would modify the Node class to be a generic class, and update the serialization and deserialization methods to work with the generic type parameter. Here's how the modified Node class and serializer might look:
import java.util.*;

//Update the Node class to use a generic type parameter T
class Node6<T> {
 T data;
 Node6<T> left;
 Node6<T> right;

 Node6(T data) {
     this.data = data;
 }
}

//Update the TreeSerializer interface to use a generic type parameter T
interface TreeSerializer7<T> {
 String serialize(Node6<T> root);
 Node6<T> deserialize(String str);
}

//Update the CyclicTreeSerializer class to use a generic type parameter T
class CyclicTreeSerializer3<T> implements TreeSerializer7<T> {
 private Set<Node6<T>> visitedNodes = new HashSet<>();

 // Serialize the tree to a string
 public String serialize(Node6<T> root) {
     visitedNodes.clear();
     StringBuilder sb = new StringBuilder();
     serializeHelper(root, sb);
     return sb.toString();
 }

 // Recursive helper for serialization
 private void serializeHelper(Node6<T> node, StringBuilder sb) {
     if (node == null) {
         sb.append("null").append(",");
         return;
     }

     if (visitedNodes.contains(node)) {
         throw new RuntimeException("Cyclic connection found in the tree");
     }

     visitedNodes.add(node);
     sb.append(node.data).append(",");
     serializeHelper(node.left, sb);
     serializeHelper(node.right, sb);
 }

 // Deserialize the string to a tree
 public Node6<T> deserialize(String str) {
     Deque<String> nodes = new LinkedList<>(Arrays.asList(str.split(",")));
     visitedNodes.clear();
     return deserializeHelper(nodes);
 }

 // Recursive helper for deserialization
 private Node6<T> deserializeHelper(Deque<String> nodes) {
     String val = nodes.poll();
     if (val.equals("null")) {
         return null;
     }
     Node6<T> node = new Node6<>(null); // Replace null with actual data type
     // Parse val to the appropriate data type T and assign to node.data

     if (visitedNodes.contains(node)) {
         throw new RuntimeException("Cyclic connection found in the tree");
     }

     visitedNodes.add(node);
     node.left = deserializeHelper(nodes);
     node.right = deserializeHelper(nodes);
     return node;
 }

 // Main method for testing
 public static void main(String[] args) {
     Scanner scanner = new Scanner(System.in);

     System.out.print("Enter space-separated tree values: ");
     String[] values = scanner.nextLine().split(" ");
     Node6<String> root = buildTree(values, 0); // Use the appropriate data type

     CyclicTreeSerializer3<String> serializer = new CyclicTreeSerializer3<>();

     try {
         String serialized = serializer.serialize(root);
         System.out.println("Serialized: " + serialized);
     } catch (RuntimeException e) {
         System.out.println("Error during serialization: " + e.getMessage());
     }

     try {
         Node6<String> deserialized = serializer.deserialize(serializer.serialize(root));
         System.out.println("Deserialized root value: " + deserialized.data);
     } catch (RuntimeException e) {
         System.out.println("Error during deserialization: " + e.getMessage());
     }

     scanner.close();
 }

 // Recursive method to build the tree from input values
 private static Node6<String> buildTree(String[] values, int index) {
     if (index >= values.length || values[index].equals("null")) {
         return null;
     }

     Node6<String> node = new Node6<>(values[index]); // Use the appropriate data type
     node.left = buildTree(values, 2 * index + 1);
     node.right = buildTree(values, 2 * index + 2);

     return node;
 }
}
