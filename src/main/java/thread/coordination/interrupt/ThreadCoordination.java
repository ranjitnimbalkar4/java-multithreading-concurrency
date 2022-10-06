package thread.coordination.interrupt;

public class ThreadCoordination {

    public static void main(String[] args) {
            Thread t = new BlockingTask();
            t.start();
            t.interrupt();
    }

   static class BlockingTask extends Thread{
        @Override
        public void run() {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Thread InterruptedException");
            }

        }
    }
}
