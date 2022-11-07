package semaphore.producer.cosumer.l26;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class MultipleConsumer implements  Runnable{

    Semaphore full;
    Semaphore empty;
    Lock lock;

    public MultipleConsumer(Semaphore full, Semaphore empty, Lock lock) {
        this.full = full;
        this.empty = empty;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            while (true) {
                empty.acquire();
               // Thread.sleep(1000);
                lock.lock();
                    System.out.println("Consumed : " + Thread.currentThread().getName() + " : " + TestMultipleProducerConsumer.nameQueue.remove());
                lock.unlock();
                full.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
