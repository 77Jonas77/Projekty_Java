import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Random;

//programowanie wspolbierzne -1. http://skinderowicz.pl/static/pw/programowanie-wspolbiezne-java.html#Semafory
//http://skinderowicz.pl/static/pw/programowanie-wspolbiezne-java.html
//ten sam link ig --> anyway sa na teamsach
public class Infolinia {

    private static final int MAX_CZAS_OBLSUGI = 5000; // maksymalny czas obsługi klienta w ms
    private static final int MAX_CZAS_MIEDZY_KLIENTAMI = 3000; // maksymalny czas między kolejnymi klientami

    private static final LinkedBlockingQueue<String> kolejkaKlientow = new LinkedBlockingQueue<>();
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        // Tworzenie i uruchamianie wątków dla konsultantów
        Thread konsultant1 = new Thread(new Konsultant("Konsultant 1"));
        Thread konsultant2 = new Thread(new Konsultant("Konsultant 2"));
        konsultant1.start();
        konsultant2.start();

        // Symulowanie dzwoniących klientów
        int klientId = 1;
        while (true) {
            try {
                String klient = "Klient " + klientId;
                System.out.println(klient + " dzwoni do infolinii.");
                kolejkaKlientow.put(klient);
                klientId++;
                Thread.sleep(new Random().nextInt(MAX_CZAS_MIEDZY_KLIENTAMI));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Konsultant implements Runnable {
        private String nazwa;

        public Konsultant(String nazwa) {
            this.nazwa = nazwa;
        }

        @Override
        public void run() {
            Random random = new Random();
            while (true) {
                try {
                    // Pobieranie klienta z kolejki
                    String klient = kolejkaKlientow.take();

                    // Blokowanie dostępu do klienta, aby inni konsultanci nie mogli go obsłużyć
                    lock.lock();
                    try {
                        System.out.println(this.nazwa + " rozpoczyna obsługę " + klient);
                        Thread.sleep(random.nextInt(MAX_CZAS_OBLSUGI));
                        System.out.println(this.nazwa + " zakończył obsługę " + klient);
                    } finally {
                        // Odblokowanie po obsłużeniu klienta
                        lock.unlock();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
