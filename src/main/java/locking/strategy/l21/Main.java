package locking.strategy.l21;

public class Main {

    public static void main(String[] args) {
        Intersection intersection = new Intersection();

        Thread trainA = new Thread(new TrainA(intersection));
        Thread trainB = new Thread(new TrainB(intersection));

        trainA.start();
        trainB.start();
    }
}
