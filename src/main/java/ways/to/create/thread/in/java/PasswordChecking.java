package ways.to.create.thread.in.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasswordChecking {

    public  static final int MAX_PASSWORD = 9999;

    public static void main(String[] args) {

        Random randomNumber = new Random();

        Vault vault = new Vault(randomNumber.nextInt(MAX_PASSWORD));

        List<Thread> threads = new ArrayList<>();

        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        threads.forEach(Thread::start);

    }

    static class Vault{

        private final int password;

        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int pass){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return pass == password;
        }
    }

    static  abstract  class HackerThread extends Thread{

        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(MAX_PRIORITY);
        }

        @Override
        public synchronized void start() {
            System.out.println("Starting thread : "+this.getName());
            super.start();
        }
    }

    static class AscendingHackerThread extends  HackerThread{

        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int i = 0; i < MAX_PASSWORD; i++) {
               System.out.println(this.getName() + " Guessing for : " + i);
                if(vault.isCorrectPassword(i)){
                    System.out.println(this.getName() + " guessed password : "+ i);
                    System.exit(0);
                }
            }
        }
    }

    static class DescendingHackerThread extends  HackerThread{

        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int i = MAX_PASSWORD; i >= 0; i--) {
                System.out.println(this.getName() + " Guessing for : "+ i);
                if(vault.isCorrectPassword(i)){
                    System.out.println(this.getName() + " guessed password : "+ i);
                    System.exit(0);
                }
            }
        }
    }

    static class  PoliceThread extends Thread{
        @Override
        public void run(){
            for (int i = 10; i >= 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Time left to catch Hacker : "+i);
            }
            System.out.println("Game over for you Hacker!!!");
            System.exit(0);
        }
    }
}
