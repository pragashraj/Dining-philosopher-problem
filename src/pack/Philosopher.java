package pack;

import java.util.concurrent.ThreadLocalRandom;

import static pack.DiningPhilosopher.forks;
import static pack.DiningPhilosopher.philosophers;
import static pack.DiningPhilosopher.philosophersNumber;


 class Philosopher extends Thread {

    public int number;
    public DiningPhilosopher.Fork leftFork;
    public DiningPhilosopher.Fork rightFork;

    Philosopher(int num, DiningPhilosopher.Fork left, DiningPhilosopher.Fork right) {
        number = num;
        leftFork = left;
        rightFork = right;
    }

    public void run(){
        System.out.println("Hi! I'm philosopher #" + number);

        while (true) {
            leftFork.grab();
            System.out.println("Philosopher #" + number + " grabs left fork.");
            rightFork.grab();
            System.out.println("Philosopher #" + number + " grabs right fork.");
            eat();
            leftFork.release();
            System.out.println("Philosopher #" + number + " releases left fork.");
            rightFork.release();
            System.out.println("Philosopher #" + number + " releases right fork.");
        }
    }

     void eat() {
        try {
            int sleepTime = ThreadLocalRandom.current().nextInt(0, 1000);
            System.out.println("Philosopher #" + " eats for " + sleepTime);
            Thread.sleep(sleepTime);
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }



    public static void main(String argv[]) {
        System.out.println("Dining philosophers problem.");

        for (int i = 0; i < philosophersNumber; i++) {
            forks[i] = new DiningPhilosopher.Fork();
        }

        for (int i = 0; i < philosophersNumber; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % philosophersNumber]);
            philosophers[i].start();
        }

        while (true) {
            try {
                // sleep 1 sec
                Thread.sleep(1000);

                // check for deadlock
                boolean deadlock = true;
                for (DiningPhilosopher.Fork f : forks) {
                    if (f.isFree()) {
                        deadlock = false;
                        break;
                    }
                }
                if (deadlock) {
                    Thread.sleep(1000);
                    System.out.println("Hurray!");
                    break;
                }
            }
            catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

        System.out.println("Finished!");
        System.exit(0);
    }
}
