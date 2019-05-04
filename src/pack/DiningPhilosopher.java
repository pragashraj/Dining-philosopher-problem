package pack;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class DiningPhilosopher
{
    static int philosophersNumber = 5;
    static Philosopher philosophers[] = new Philosopher[philosophersNumber];
    static Fork forks[] = new Fork[philosophersNumber];

    static class Fork {

        public Semaphore mutex = new Semaphore(1);

        void grab() {
            try {
                mutex.acquire();
            }
            catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

        void release() {
            mutex.release();
        }

        boolean isFree() {
            return mutex.availablePermits() > 0;
        }

    }



}
