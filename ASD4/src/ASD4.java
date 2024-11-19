import java.io.*;
import java.util.Arrays;

public class ASD4 {
    public static void main(String[] args) throws IOException {
        PriorityHeap heap = new PriorityHeap("/Users/jonaszsojka/IdeaProjects/ASD4/src/dane.txt");
        AlphabetCreator alphabetCreator = new AlphabetCreator(heap);
    }
}

class AlphabetCreator {
    PriorityHeap heap;

    public AlphabetCreator(PriorityHeap heap) {
        this.heap = heap;
        createAlpabet();
    }

    private void createAlpabet() {
        Node newNode = null;
        int worth;
        while (!heap.lastLeft()) {
            Node n1 = heap.DELMIN();
            Node n2 = heap.DELMIN();

            System.out.println(n1.s + " + " + n2.s + " WART: " + (n1.number + n2.number) + " czesciowo " + n1.number + " " + n2.number);
            worth = (n1.number + n2.number);
            newNode = new Node(worth, n1.s + n2.s);

            newNode.leftBin = n1;
            newNode.rightBin = n2;

            heap.insert(newNode);
        }
        traverseInOrder(newNode, "");
    }

    public void traverseInOrder(Node node, String binCode) {
        if (node != null) {
            traverseInOrder(node.leftBin, binCode + "0");
            if (node.leftBin == null && node.rightBin == null)
                System.out.println(node.s + " " + binCode);
            traverseInOrder(node.rightBin, binCode + "1");
        }
    }
}

class Node {
    int number;
    Node leftBin, rightBin;
    String s;

    public Node(int number, String s) {
        this.number = number;
        this.s = s;
    }
}

class PriorityHeap {

    public Node[] heap;
    public int position = -1;

    public PriorityHeap(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        int countLines = 0;
        while ((br.readLine()) != null) {
            countLines++;
        }
        br.close();
        heap = new Node[countLines];
        fillPHeap(filename);
    }

    public void fillPHeap(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        String[] divided;
        while ((line = br.readLine()) != null) {
            divided = line.split("\\s+");
            insert(new Node(Integer.parseInt(divided[1]), divided[0]));
        }
        br.close();
    }

    protected void fixUpward() {
        int indx = position;
        int parent = (indx - 1) / 2;
        while (parent >= 0 && heap[indx].number < (heap[parent].number)) {
            swap(indx, parent);
            indx = parent;
            parent = (indx - 1) / 2;
        }
    }
    protected void fixDownward(int endindx) {
        if (endindx == -1) return;
        int indx = 0;
        while (indx <= endindx) {
            int leftindx = 2 * indx + 1;
            int rightindx = 2 * indx + 2;
            if (leftindx > endindx) break;
            int toSwap = rightindx > endindx
                    ? leftindx
                    : heap[leftindx].number < (heap[rightindx].number)
                    ? leftindx
                    : rightindx;

            if (heap[indx].number < (heap[toSwap].number)) break;
            swap(indx, toSwap);
            indx = toSwap;
        }
    }


    public void insert(Node nodeToInsert) {
        heap[++position] = nodeToInsert;
        fixUpward();
    }

    public Node DELMIN() {
        if (isEmpty()) {
            return null;
        }
        Node result = heap[0];
        heap[0] = heap[position--];
        heap[position + 1] = null;
        fixDownward(position);
        return result;
    }
    public void print() {
        Arrays.stream(heap).forEach(node ->{
            if (node != null)
                System.out.print(node.number + " -> ");
            else{
                System.out.print(" -> x ");
            }});
        System.out.println();
    }

    public void swap(int firstIndx, int secondIndx) {
        Node temp = heap[firstIndx];
        heap[firstIndx] = heap[secondIndx];
        heap[secondIndx] = temp;
    }

    private boolean isEmpty() {
        return heap.length == 0;
    }

    public boolean lastLeft() {
        return heap[0] != null && heap[1] == null && heap[2] == null;
    }
}
