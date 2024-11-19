package Ex02;

public class Main {
    static final int THREAD_COUNT = 5;
    private static final int CYCLE = 10;
    static int suspendedThread;

    public static void main(String[] args) {
        MyThread[] threads = createThreadList();

        for (MyThread thread : threads) {
            thread.start();
        }

        suspendedThread = 0;
        for (int i = 0; i < CYCLE; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.print("\nResuming " + (char) (suspendedThread + 'a') + ", suspending " + (char) ((suspendedThread + 1) % 5 + 'a') + ": ");
            threads[suspendedThread].setPaused(false);
            threads[suspendedThread].wakeUp();

            suspendedThread++;
            suspendedThread %= 5;
            threads[suspendedThread].setPaused(true);
        }

        System.out.println();
        for(MyThread thread: threads){
            thread.setWorking(false);
        }
    }

    private static MyThread[] createThreadList() {
        MyThread[] threads = new MyThread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++)
            threads[i] = new MyThread((char) ('a' + i));
        return threads;
    }
}

class MyThread extends Thread {
    private boolean isWorking;
    private boolean isPaused;
    private final char letter;

    public MyThread(char letter) {
        super();
        this.letter = letter;
        isWorking = true;
        isPaused = letter == 'a';
    }

    @Override
    public void run() {
        while (isWorking) {
            synchronized (this) {
                while (isPaused) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            System.out.print(letter);
            try {
                sleep((int) (Math.random() * 901 + 100));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Thread " + letter + " exits");
    }

    public void wakeUp() {
        synchronized (this) {
            this.notify();
        }
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }
}