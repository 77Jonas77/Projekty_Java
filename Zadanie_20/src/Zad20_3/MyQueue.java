package Zad20_3;

import java.util.Objects;

public class MyQueue {
    private int front;
    private int rear;

    private String[] queue;

    public MyQueue(int maxSize) {
        this.front = -1;
        this.rear = -1;
        queue = new String[maxSize];
    }

    public boolean isEmpty() {
        return this.front == -1 && this.rear == -1;
    }

    public void show() {
        if(isEmpty()){
            System.out.println("Kolejka jest pusta");
        }else {
            for (int i = 0; i < queue.length; i++) {
                System.out.println(queue[i]);
            }
        }
    }

    public void put(String club) {
        if (isEmpty()) {
            this.front = 0;
            this.rear = 0;
        } else {
            ++this.rear;
        }
        queue[rear] = club;
    }

    public String getClub() {
        if (isEmpty()) {
            return "Kolejka jest pusta";
        }
        if (this.front == this.rear) {
            int tmp = this.front;
            this.front = -1;
            this.rear = -1;
            return queue[tmp];
        } else {
            return queue[this.front++];
        }
    }

}
