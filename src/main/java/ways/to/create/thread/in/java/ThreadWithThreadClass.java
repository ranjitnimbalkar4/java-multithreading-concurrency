package ways.to.create.thread.in.java;

public class ThreadWithThreadClass {

    public static void main(String[] args) {

        MyThread t = new MyThread();
        t.start();

    }

    static class MyThread extends  Thread {
        @Override
        public void run() {
            System.out.println("Inside the thread - Extended from thread.");
        }
    }
}
