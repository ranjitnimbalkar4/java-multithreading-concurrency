package read.write.lock.l24;

import java.util.Random;

public class AddItemsThread implements  Runnable{

    public static final int HIGHEST_RANGE = 1000;

    InventoryRepository repository;

    public AddItemsThread(InventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            repository.addItem(random.nextInt(HIGHEST_RANGE));
            repository.deleteItems(random.nextInt(HIGHEST_RANGE));
            //System.out.println("PRICES : "+ repository.price);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }
}
