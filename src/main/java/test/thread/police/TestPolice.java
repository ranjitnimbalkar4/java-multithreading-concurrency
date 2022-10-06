package test.thread.police;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestPolice {

    private static final int MAX_PASSWORD = 9999;

    public static void main(String[] args) {

        Random pass = new Random();

        Vault vault = new Vault(pass.nextInt(MAX_PASSWORD));

        List<Thread> threads = new ArrayList<>();
        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        threads.forEach(Thread::start);


    }

    static class Vault {
        private int password;

        public Vault(int password) {
            this.password = password;
        }

        public boolean isPassowrdCorrect(int guess){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return password == guess;
        }
    }

    static class HackerThread extends Thread{
        Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(MAX_PRIORITY);
        }

        @Override
        public synchronized void start() {
            System.out.println("Starting thread : "+ this.getName());
            super.start();
        }
    }

    static class AscendingHackerThread extends  HackerThread{

        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = 0; guess < MAX_PASSWORD; guess++) {
                if(vault.isPassowrdCorrect(guess)){
                    System.out.println("Password guessed is : "+guess);
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
            for (int guess = MAX_PASSWORD; guess >= 0; guess--) {
                if(vault.isPassowrdCorrect(guess)){
                    System.out.println("Password guessed is : "+guess);
                    System.exit(0);
                }
            }
        }
    }

    static class PoliceThread extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Time to Catch you hacker :" + i);
            }
            System.out.println("Game Over you Hacker!!!");
            System.exit(0);
        }
    }
}
