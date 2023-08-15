package defaultpackage;
//I. Implement a TreeSerializer given the above assumptions.
import java.util.*;

//Node class represents a node in the binary tree
class Node {
 int num;
 Node left;
 Node right;

 // Constructor to initialize a node with a value
 Node(int num) {
     this.num = num;
 }
}

//TreeSerializer5 interface defines the methods for tree serialization and deserialization
interface TreeSerializer5 {
 String serialize(Node root);
 Node deserialize(String str);
}

//NonCyclicTreeSerializer implements TreeSerializer5 to serialize and deserialize a binary tree
public class NonCyclicTreeSerializer implements TreeSerializer5 {
 // Serialize the tree to a string
 public String serialize(Node root) {
     if (root == null) {
         return "null";
     }
     StringBuilder sb = new StringBuilder();
     serializeHelper(root, sb);
     return sb.toString();
 }

 // Recursive helper method to serialize the tree
 private void serializeHelper(Node node, StringBuilder sb) {
     if (node == null) {
         sb.append("null").append(",");
     } else {
         sb.append(node.num).append(","); // Append the node's value
         serializeHelper(node.left, sb); // Recursively serialize the left subtree
         serializeHelper(node.right, sb); // Recursively serialize the right subtree
     }
 }

 // Deserialize the string to a tree
 public Node deserialize(String str) {
     Deque<String> nodes = new LinkedList<>(Arrays.asList(str.split(",")));
     return deserializeHelper(nodes);
 }

 // Recursive helper method to deserialize the tree
 private Node deserializeHelper(Deque<String> nodes) {
     String val = nodes.poll(); // Get the next value from the deque
     if (val.equals("null")) {
         return null;
     }
     Node node = new Node(Integer.parseInt(val)); // Create a new node with the parsed value
     node.left = deserializeHelper(nodes); // Recursively deserialize the left subtree
     node.right = deserializeHelper(nodes); // Recursively deserialize the right subtree
     return node;
 }

 public static void main(String[] args) {
     Scanner scanner = new Scanner(System.in);

     System.out.print("Enter space-separated tree values: ");
     String[] values = scanner.nextLine().split(" ");
     Node root = buildTree(values, 0);

     NonCyclicTreeSerializer serializer = new NonCyclicTreeSerializer();

     // Serialize and print the serialized tree
     String serialized = serializer.serialize(root);
     System.out.println("Serialized: " + serialized);

     // Deserialize the serialized string and print the root value
     Node deserialized = serializer.deserialize(serialized);
     System.out.println("Deserialized root value: " + deserialized.num);

     scanner.close();
 }

 // Recursive method to build the binary tree from input values
 private static Node buildTree(String[] values, int index) {
     if (index >= values.length || values[index].equals("null")) {
         return null;
     }

     Node node = new Node(Integer.parseInt(values[index])); // Create a new node with the parsed value
     node.left = buildTree(values, 2 * index + 1); // Recursively build the left subtree
     node.right = buildTree(values, 2 * index + 2); // Recursively build the right subtree

     return node;
 }
}

