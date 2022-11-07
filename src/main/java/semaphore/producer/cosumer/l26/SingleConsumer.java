package semaphore.producer.cosumer.l26;

import java.util.concurrent.Semaphore;

public class SingleConsumer implements  Runnable{

    Semaphore full;
    Semaphore empty;

    public SingleConsumer(Semaphore full, Semaphore empty) {
        this.full = full;
        this.empty = empty;
    }

    @Override
    public void run() {
        try {
            while (true) {
                empty.acquire();
                Thread.sleep(1000);
                System.out.println("Consumed : " + TestSingleProducerConsumer.name);
                full.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
