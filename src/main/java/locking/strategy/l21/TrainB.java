package locking.strategy.l21;

import java.util.Random;

public class TrainB implements Runnable{

    private Intersection intersection;
    Random randomSchedule = new Random();

    public TrainB(Intersection intersection) {
        this.intersection = intersection;
    }

    @Override
    public void run() {
        while (true){
            int sleep = randomSchedule.nextInt(5);
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
            }
            intersection.takeRoadB();
        }
    }
}
