package thread.coordination.join;

public class ThreadBusyWait {


    public static void main(String[] args) throws InterruptedException {
        Thread f = new Thread(new FirstThread());
        Thread s = new Thread(new SecondThread(f));
        f.start();
        s.start();
        s.join();
    }

    static class FirstThread implements Runnable{
        @Override
        public void run() {
            try {
                Thread.sleep(10000);
                System.out.println("Thread First Completed..");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class SecondThread implements Runnable{

        Thread waitForThread;

        public SecondThread(Thread waitForThread) {
            this.waitForThread = waitForThread;
        }

        @Override
        public void run() {
            while(waitForThread.isAlive()){}//Busy wait - not good as this waiting thread consumes CPU Cycles
            System.out.println("Thread Second Completed..");
        }
    }

}
