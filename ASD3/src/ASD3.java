import java.io.*;

public class ASD3 {

    public static void main(String[] args) throws IOException {
        new AVLTree(args[0]);
        //w przypadku gdy drzewo jest puste wstawiany jest sout() - nie bylo okreslone w poleceniu
    }
}

class Node {
    int number, size, height;
    Node left, right, parent;

    public Node(int number, Node parent) {
        this.number = number;
        this.parent = parent;
        this.height = 0;
        this.size = 1;
    }

    public int balFactor(Node node) {
        return getHeight(node.right) - getHeight(node.left);
    }

    public Node checkRot() {
        Node node = this;
        int balance = balFactor(node);

        if (balance < -1) {
            if (balFactor(node.left) <= 0) {
                node = node.rotationRight();
            } else {
                node.left = node.left.rotationLeft();
                node = node.rotationRight();
            }
        }

        if (balance > 1) {
            if (balFactor(node.right) >= 0) {
                node = node.rotationLeft();
            } else {
                node.right = node.right.rotationRight();
                node = node.rotationLeft();
            }
        }
        return node;
    }

    Node rotationLeft() {
        Node rc = right;

        right = rc.left;
        if (rc.left != null) rc.left.parent = this;

        rc.left = this;
        rc.parent = parent;

        if (parent != null) {
            if (parent.left == this) {
                parent.left = rc;
            } else if (parent.right == this) {
                parent.right = rc;
            }
        }
        parent = rc;
        this.refSize();
        this.refHeight();
        rc.refSize();
        rc.refHeight();

        return rc;
    }

    Node rotationRight() {
        Node lc = left;

        left = lc.right;
        if (lc.right != null) lc.right.parent = this;

        lc.right = this;
        lc.parent = parent;

        if (parent != null) {
            if (parent.left == this) {
                parent.left = lc;
            } else if (parent.right == this) {
                parent.right = lc;
            }
        }
        parent = lc;
        this.refSize();
        this.refSize();
        lc.refSize();
        lc.refHeight();

        return lc;
    }

    public void refSize() {
        size = 0;
        if (left != null)
            size += left.size;
        if (right != null)
            size += right.size;
        size += 1;
    }

    public void refHeight() {
        int l = getHeight(left);
        int r = getHeight(right);
        height = (l > r ? l : r) + 1;
    }

    public int getHeight(Node node) {
        return node != null ? node.height : -1;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Node getParent() {
        return parent;
    }
}

class AVLTree {
    Node p;
    Node mainRoot;
    int numOfOper;

    public AVLTree(String filename) throws IOException {
        createAVLFromInput(filename);
    }

    public void createAVLFromInput(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        int countLine = 0, index = 0, course_length;
        String line;
        String[] course;

        while ((line = reader.readLine()) != null) {
            //wczytanie danych
            if (countLine == 0) {
                numOfOper = Integer.parseInt(line);
                countLine++;
            } else {
                course = line.split(" ");

                for (String val : course) {
                    mainRoot = insertNode(mainRoot, Integer.parseInt(val));
                }

                //operacje
                course_length = course.length;
                for (int i = 0; i < numOfOper; i++) {
                    if (mainRoot != null) {
                        index = index % course_length;
                        this.p = this.getNodeAtIndex(index, mainRoot);
                        if (this.p.number % 2 == 0) {
                            int indexToDel;
                            if (index + 1 != course_length) {
                                indexToDel = index + 1;
                            } else {
                                indexToDel = 0;
                                index--;
                            }
                            index += getNodeAtIndex(indexToDel, mainRoot).number;
                            this.mainRoot = DELETE(indexToDel);
                            course_length--;
                        } else {
                            this.mainRoot = this.ADD();
                            index += p.number;
                            course_length++;
                        }
                    }
                }
                index = (mainRoot != null) ? index % mainRoot.size : index;
                printResult(index);
            }
        }
    }

    public void saveToFile(int startEnd) throws IOException { //metoda do zapisywania wyn do pliku
        try (BufferedWriter br = new BufferedWriter(new FileWriter("/Users/jonaszsojka/IdeaProjects/compareFile/src/myFile.txt"))) {
            if (mainRoot != null) {
                Node index;
                for (int i = startEnd; i < mainRoot.size; i++) {
                    index = getNodeAtIndex(i, mainRoot);
                    br.write(index.number + " ");
                }
                for (int i = 0; i < startEnd; i++) {
                    index = getNodeAtIndex(i, mainRoot);
                    br.write(index.number + " ");
                }
            }else{
                br.write("");
                br.newLine();
                br.flush();
            }
        }
    }

    public void printResult(int startEnd) {
        //przy duzych plikach trzeba uwazac na ogr limtu wielkosci pliku + mozliwej liczby wyswietlania na konsoli
        if (mainRoot != null) {
            Node index;
            for (int i = startEnd; i < mainRoot.size; i++) {
                index = getNodeAtIndex(i, mainRoot);
                System.out.print(index.number + " ");
            }
            for (int i = 0; i < startEnd; i++) {
                index = getNodeAtIndex(i, mainRoot);
                System.out.print(index.number + " ");
            }
        }else{
            System.out.println(); //jesli drzewo jest puste to nowa linia po prostu
        }
    }

    private Node insertNode(Node node, int item) {
        if (node == null)
            return new Node(item, null);
        Node tmp = node;
        while (tmp.right != null)
            tmp = tmp.right;
        tmp.right = new Node(item, tmp);
        tmp = tmp.right;

        while (tmp.parent != null) {
            tmp = tmp.parent;
            tmp.refHeight();
            tmp.refSize();
            tmp = tmp.checkRot();
        }
        return tmp;
    }

    private Node ADD() {
        Node tmp_Node;

        Node rNode = this.p.right;
        this.p.right = new Node(this.p.number - 1, this.p);
        this.p.right.parent = this.p;
        if (rNode != null) {
            rNode.parent = this.p.right;
            this.p.right.right = rNode;
            tmp_Node = rNode;
        } else
            tmp_Node = this.p.right;

        while (tmp_Node.parent != null) {
            tmp_Node = tmp_Node.parent;
            tmp_Node.refHeight();
            tmp_Node.refSize();
            tmp_Node = tmp_Node.checkRot();
        }
        return tmp_Node;
    }

    private Node getNodeAtIndex(int index, Node root) {
        Node tmp_Node = root;
        int tmp_index = 0;
        if (root.left != null) {
            tmp_index = root.left.size;
        }

        if (tmp_index < index) {
            tmp_Node = tmp_Node.right;
            index = index - tmp_index - 1;
            tmp_Node = getNodeAtIndex(index, tmp_Node);
        } else if (tmp_index > index) {
            tmp_Node = tmp_Node.left;
            tmp_Node = getNodeAtIndex(index, tmp_Node);
        }
        return tmp_Node;
    }

    private Node DELETE(int indexToDel) {
        Node nodeToDel = getNodeAtIndex(indexToDel, mainRoot);
        Node tmp_node = nodeToDel;

        if (nodeToDel.right == null && nodeToDel.left == null) {
            if (tmp_node.parent != null) {
                if (tmp_node.parent.left == tmp_node) {
                    tmp_node.parent.left = null;
                } else if (tmp_node.parent.right == tmp_node) {
                    tmp_node.parent.right = null;
                }
            } else {
                return null;
            }
        } else {
            if (nodeToDel.right != null) {
                tmp_node = nodeToDel.right;

                while (tmp_node.left != null) {
                    tmp_node = tmp_node.left;
                }

                if (tmp_node.right != null) {
                    tmp_node.right.parent = tmp_node.parent;
                    if (tmp_node.parent.left == tmp_node) {
                        tmp_node.parent.left = tmp_node.right;
                    } else if (tmp_node.parent.right == tmp_node) {
                        tmp_node.parent.right = tmp_node.right;
                    }
                } else {
                    if (tmp_node.parent.left == tmp_node) {
                        tmp_node.parent.left = null;
                    } else if (tmp_node.parent.right == tmp_node) {
                        tmp_node.parent.right = null;
                    }
                }

            } else {
                tmp_node = nodeToDel.left;
                if (tmp_node.left != null) {
                    tmp_node.left.parent = nodeToDel;
                }
                if (tmp_node.right != null) {
                    tmp_node.right.parent = nodeToDel;
                }
                nodeToDel.left = tmp_node.left;
                nodeToDel.right = tmp_node.right;
            }
        }
        nodeToDel.number = tmp_node.number;

        while (tmp_node.parent != null) {
            tmp_node = tmp_node.parent;
            tmp_node.refHeight();
            tmp_node.refSize();
            tmp_node = tmp_node.checkRot();
        }
        return tmp_node;
    }

}