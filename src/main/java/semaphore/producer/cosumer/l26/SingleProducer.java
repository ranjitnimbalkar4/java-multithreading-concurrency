package semaphore.producer.cosumer.l26;

import com.github.javafaker.Faker;

import java.util.concurrent.Semaphore;

public class SingleProducer implements Runnable{

    Semaphore full;
    Semaphore empty;

    public SingleProducer(Semaphore full, Semaphore empty) {
        this.full = full;
        this.empty = empty;
    }

    @Override
    public void run() {
        try {
            while(true) {
                full.acquire();
                Thread.sleep(1000);
                TestSingleProducerConsumer.name = Faker.instance().name().name();
                empty.release();
            }
        } catch (InterruptedException e) {
        }

    }
}
