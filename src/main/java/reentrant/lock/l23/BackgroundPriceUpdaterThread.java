package reentrant.lock.l23;

import java.util.Random;

public class BackgroundPriceUpdaterThread implements Runnable {

    PriceContainer pricesContainer;
    private Random random = new Random();

    public BackgroundPriceUpdaterThread(PriceContainer priceContainer) {
        this.pricesContainer = priceContainer;
    }

    @Override
    public void run() {
        while (true) {
            pricesContainer.getLock().lock();
            try {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }

                pricesContainer.setBitcoinPrice(random.nextInt(20000));
                pricesContainer.setEtherPrice(random.nextInt(2000));
                pricesContainer.setLitecoinPrice(random.nextInt(500));
                pricesContainer.setBitcoinCashPrice(random.nextInt(5000));
                pricesContainer.setRipplePrice(random.nextDouble());
                System.out.println("Fetched updated prices...");
            } finally {
                pricesContainer.getLock().unlock();
            }
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }
    }
}
