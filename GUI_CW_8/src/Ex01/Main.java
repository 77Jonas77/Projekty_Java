package Ex01;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Letters letters = new Letters("ABCD");
        for (LetterThread t : letters.getThreads())
            System.out.println(t.getName() + " starting");
        letters.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignore) {
        }
        letters.stop();
        System.out.println("\nProgram completed.");
    }
}

class Letters {
    private String letters;
    private List<LetterThread> threads;

    public Letters(String letters) {
        this.letters = letters;
        threads = new ArrayList<>();
        for (int i = 0; i < letters.length(); i++) {
            char letter = letters.charAt(i);
            LetterThread letterThread = new LetterThread(letter);
            threads.add(letterThread);
        }
    }
        public void start () {
            for (LetterThread lt : threads) {
                lt.start();
            }
        }

        public List<LetterThread> getThreads () {
            return threads;
        }

        public void stop () {
            for (LetterThread thread : threads) {
                thread.setWorking(false);
            }
        }
    }

    class LetterThread extends Thread {
        private char name;
        private boolean isWorking;

        public LetterThread(char name) {
            this.name = name;
            isWorking = true;
        }

        @Override
        public void run() {
            try {
                while (this.isWorking) {
                    synchronized (this) {
                        System.out.print(name);
                        Thread.sleep(1000);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public void setWorking(boolean working) {
            isWorking = working;
        }

    }