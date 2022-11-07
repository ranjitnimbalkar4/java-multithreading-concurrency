package semaphore.producer.cosumer.l26;

import com.github.javafaker.Faker;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class MultipleProducer implements Runnable{

    Semaphore full;
    Semaphore empty;
    Lock lock;

    public MultipleProducer(Semaphore full, Semaphore empty, Lock lock) {
        this.full = full;
        this.empty = empty;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            while(true) {
                full.acquire();
                Thread.sleep(1000);
                lock.lock();
                    System.out.println("Produced By : "+ Thread.currentThread().getName());
                    TestMultipleProducerConsumer.nameQueue.add(Faker.instance().name().name());
                lock.unlock();
                empty.release();
            }
        } catch (InterruptedException e) {
        }

    }
}
