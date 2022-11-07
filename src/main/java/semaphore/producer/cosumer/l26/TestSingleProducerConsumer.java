package semaphore.producer.cosumer.l26;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class TestSingleProducerConsumer {

    public static String name;

    public static void main(String[] args) throws InterruptedException {
        Semaphore full = new Semaphore(1);
        Semaphore empty = new Semaphore(0);

        Thread producer = new Thread(new SingleProducer(full,empty));
        Thread consumer = new Thread(new SingleConsumer(full,empty));

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

    }
}
