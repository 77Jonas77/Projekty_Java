import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ASD2 {

    static Node currNode;
    static String lastWord = "";
    static String currWord = "";

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(args[0]));

        Node root = new Node();
        currNode = root;
        String label = "";
        String line;
        String[] parts;
        while (true) {
            line = reader.readLine();

            if (line == null) {
                break;
            }

            parts = line.split(" ");
            label = parts[0];

            if (parts.length > 1) {
                for (char znak : parts[1].toCharArray()) {
                    if (znak == 'R') {
                        if (currNode.right == null) {
                            currNode.right = new Node();
                            currNode.right.parent = currNode;
                        }
                        currNode = currNode.right;
                    } else if (znak == 'L') {
                        if (currNode.left == null) {
                            currNode.left = new Node();
                            currNode.left.parent = currNode;
                        }
                        currNode = currNode.left;
                    }
                }
                currNode.label = label;
                currNode = root;
            }else{
                currNode.label = label;
                currNode = root;
            }

        }

        root.findLongest();
        System.out.println(lastWord);
    }

    static class Node {
        String label;
        Node left;
        Node right;
        Node parent;

        public Node() {
            this.label = "";
            this.left = null;
            this.right = null;
            this.parent = null;
        }

        void findLongest() {
            if (this.left != null) {
                this.left.findLongest();
            }

            if (this.right != null) {
                this.right.findLongest();
            }

            if (this.left == null && this.right == null) {
                currNode = this;
                while (currNode.parent != null) {
                    currWord = currWord + currNode.label;
                    currNode = currNode.parent;
                }
                currWord = currWord + currNode.label;
                if (lastWord.compareTo(currWord) < 0) {
                    lastWord = currWord;
                }
                currWord = "";
            }
        }
    }
}
