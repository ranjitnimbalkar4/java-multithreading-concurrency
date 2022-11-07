package semaphore.producer.cosumer.l26;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestMultipleProducerConsumer {

    public static Queue<String> nameQueue = new ArrayDeque<>(3);

    public static void main(String[] args) throws InterruptedException {
        Semaphore full = new Semaphore(3);
        Semaphore empty = new Semaphore(0);
        Lock lock = new ReentrantLock();

        Thread producer1 = new Thread(new MultipleProducer(full,empty, lock), "Produce-1");
        Thread producer2 = new Thread(new MultipleProducer(full,empty, lock), "Produce-2");
        Thread producer3 = new Thread(new MultipleProducer(full,empty, lock), "Produce-3");

        Thread consumer1 = new Thread(new MultipleConsumer(full,empty, lock), "Consumer-1");
        Thread consumer2 = new Thread(new MultipleConsumer(full,empty, lock), "Consumer-2");

        producer1.start();
        producer2.start();
        producer3.start();

        consumer1.start();
        consumer2.start();

        producer1.join();
        producer2.join();
        producer3.join();

        consumer1.join();
        consumer2.join();

    }
}
