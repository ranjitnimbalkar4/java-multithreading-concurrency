package read.write.lock.l24;

public class Main {

    public static void main(String[] args) {

        InventoryRepository repository = new InventoryRepository();

        Thread addItemsThread = new Thread(new AddItemsThread(repository));
        addItemsThread.setDaemon(true);
        addItemsThread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int itemsForPriceRange = repository.getItemsForPriceRange(1, 100);

        System.out.println(itemsForPriceRange);
    }
}
