package Threads;

import Interfeces.SendToNextInterface;
import Layers.BscColumnLayer;
import Layers.BscLayer;
import Layers.BtsLayerLeft;
import Layers.Layer;
import Panels.BtsPanelLeft;

import java.util.*;

public class BtsLeftThread extends Thread {

    private int incomingMsg;
    private int sentMsg;
    private int head;
    private int tail;
    private byte[] pdu;
    private SendToNextInterface nextLayer;
    private boolean working;
    private Queue<byte[]> pendingMsgg;
    private BtsPanelLeft btsPanelLeft;

    public BtsLeftThread(SendToNextInterface nextLayer, BtsPanelLeft btsPanelLeft) {
        this.btsPanelLeft = btsPanelLeft;
        working = true;
        this.incomingMsg = 0;
        this.sentMsg = 0;
        this.head = 0;
        this.tail = 0;
        this.nextLayer = nextLayer;
        pendingMsgg = new Queue<byte[]>() {
            private ArrayList<byte[]> list = new ArrayList<>();

            @Override
            public boolean add(byte[] pdu) {
                list.add(pdu);
                tail++;
                return true;
            }

            @Override
            public boolean offer(byte[] pdu) {
                list.add(pdu);
                tail++;
                return true;
            }

            @Override
            public byte[] remove() {
                if (isEmpty()) {
                    throw new IllegalStateException("Queue is empty");
                }
                byte[] element = list.remove(head);
                head++;
                return element;
            }

            @Override
            public byte[] poll() {
                if (isEmpty()) {
                    return null;
                }
                byte[] element = list.get(head);
                head++;
                return element;
            }

            @Override
            public byte[] element() {
                if (isEmpty()) {
                    throw new IllegalStateException("Queue is empty");
                }
                return list.get(head);
            }

            @Override
            public byte[] peek() {
                if (isEmpty()) {
                    return null;
                }
                return list.get(head);
            }

            @Override
            public int size() {
                return tail - head;
            }

            @Override
            public boolean isEmpty() {
                return head == tail;
            }

            @Override
            public boolean contains(Object o) {
                return list.contains(o);
            }

            @Override
            public Iterator<byte[]> iterator() {
                return list.iterator();
            }

            @Override
            public Object[] toArray() {
                return list.toArray();
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return list.toArray(a);
            }

            @Override
            public boolean remove(Object o) {
                return list.remove(o);
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return list.containsAll(c);
            }

            @Override
            public boolean addAll(Collection<? extends byte[]> c) {
                if (c.isEmpty()) {
                    return false;
                }
                for (byte[] element : c) {
                    list.add(element);
                }
                tail += c.size();
                return true;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return list.removeAll(c);
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return list.retainAll(c);
            }

            @Override
            public void clear() {
                list.clear();
                head = 0;
                tail = 0;
            }
        };
    }

    @Override
    public void run() {
        while (working) {
            if (!pendingMsgg.isEmpty()) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.pdu = pendingMsgg.poll();
                nextLayer.sendToNext(pdu);
                endKeeping();
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public synchronized void increaseIncomingMsg() {
        this.incomingMsg++;
    }

    public synchronized void increaseSentMsg() {
        this.incomingMsg++;
    }

    public synchronized void decreaseIncomingMsg() {
        this.incomingMsg--;
    }

    public synchronized int getIncomingMsg() {
        return incomingMsg;
    }

    public synchronized void setIncomingMsg(int incomingMsg) {
        this.incomingMsg = incomingMsg;
    }

    public synchronized int getSentMsg() {
        return sentMsg;
    }

    public synchronized void setSentMsg(int sentMsg) {
        this.sentMsg = sentMsg;
    }

    public synchronized void receiveMsg(byte[] pdu) {
        this.pendingMsgg.add(pdu);
        this.btsPanelLeft.increaseIncoming();
        incomingMsg++;
    }


    protected synchronized void endKeeping() {
        this.pdu = null;
        this.btsPanelLeft.decreaseIncoming();
        this.btsPanelLeft.increaseSent();
        incomingMsg--;
        //incrementSentSMSCounter();
    }
}
