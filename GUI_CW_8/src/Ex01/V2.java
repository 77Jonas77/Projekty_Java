package Ex01;

import java.util.ArrayList;
import java.util.List;

public class V2 {
    public static void main(String[] args) {
        Lettersv2 letters = new Lettersv2("ABCD");
        for (MyThread t : letters.getThreadList2())
            System.out.println(t.getName() + " starting");
        letters.start();
        try {
            MyThread.sleep(5000);
        } catch (InterruptedException ignore) {
        }
        letters.stop();
        System.out.println("\nProgram completed.");
    }
}

class Lettersv2 {
    private String ciag;
    private List<MyThread> threadList2= new ArrayList<>();

    public Lettersv2(String ciag) {
        this.ciag = ciag;
        for(int i=0; i < ciag.length(); i++) {
            char letter = ciag.charAt(i);
            MyThread mythread = new MyThread(letter);
            threadList2.add(mythread);
        }
    }
    public void stop(){
        for (MyThread thread: threadList2
             ) {
            thread.setWorking(false);
        }
    }
    public void start(){
        for (MyThread thread: threadList2) {
            thread.start();
        }
    }

    public List<MyThread> getThreadList2() {
        return threadList2;
    }
}

class MyThread extends Thread{
    private char letter;
    private boolean isWorking;

    public MyThread(char letter) {
        this.letter=letter;
        this.isWorking=true;
        this.setName("Thread " + letter);
    }

    @Override
    public void run() {
        while(isWorking){
            synchronized (this) {
                System.out.print(letter);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }
}
