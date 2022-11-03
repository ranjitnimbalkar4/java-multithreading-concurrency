package race.condition.data.race;

public class DataRaceTest {

    public static void main(String[] args) {
        SharedClass shared = new SharedClass();

        Thread t1 = new Thread(() -> {
            for(int i = 0; i < Integer.MAX_VALUE; i++)
                 shared.increment();;
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                shared.checkForDataRace();
            }
        });

        t1.start();
        t2.start();

    }

    public static class SharedClass {
        volatile int x = 0; // volatile - happens before semantic i.e all the instruction before volatile variable runs before
        // and all instruction after volatile variable runs after volatile variable - no data race happens (Data races occurs du to
        // rearrangement of unrelated operations by compiler and CPU), here x and y are unrelated operations
        volatile int y = 0;

        public void increment(){
            x++;
            y++;
        }

        public void checkForDataRace(){
            if(y > x){
                System.out.println("Y > X : Data race detected");
            }
        }
    }
}
