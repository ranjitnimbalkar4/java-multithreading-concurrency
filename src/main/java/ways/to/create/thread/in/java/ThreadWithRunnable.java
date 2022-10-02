package ways.to.create.thread.in.java;

public class ThreadWithRunnable {

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(() -> {//Anonymous Class
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Inside new Thread from Runnable - Anonymous class");
        });

        t.setDaemon(true); // Background thread - main thread does not wait for it.
        t.start();
        System.out.println("Staged t as background thread no need to wait...");

        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Inside new Thread from Runnable - Lambda");
        });
        t1.setDaemon(true);
        t1.start();
        System.out.println("Waiting for t1 to completed..");
        t1.join();

        System.out.println("Exiting main thread...");

    }
}
