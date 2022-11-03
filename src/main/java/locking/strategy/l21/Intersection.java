package locking.strategy.l21;

public class Intersection {

    private Object roadA = new Object();
    private Object roadB = new Object();

    public void takeRoadA(){
        synchronized (roadA){
            System.out.println("Road A is locked by Thread : "+ Thread.currentThread().getName());

            synchronized (roadB){
                System.out.println("Train is passed through road A...");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void takeRoadB(){
        synchronized (roadA){
            System.out.println("Road B is locked by Thread : "+ Thread.currentThread().getName());

            synchronized (roadB){
                System.out.println("Train is passed through road B...");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
