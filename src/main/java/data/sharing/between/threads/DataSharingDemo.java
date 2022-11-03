package data.sharing.between.threads;

public class DataSharingDemo {

    public static void main(String[] args) {
        InventoryCounter inventoryCounter = new InventoryCounter();

        Thread incrementThread = new Thread(new IncrementInventory(inventoryCounter));
        Thread decrementThread = new Thread(new DecrementInventory(inventoryCounter));

        incrementThread.start();
        decrementThread.start();

        try {
            incrementThread.join();
            decrementThread.join();
        }catch (Exception ignored){

        }

        System.out.println(inventoryCounter.getItems());

    }

    public static class IncrementInventory implements Runnable{
        InventoryCounter inventoryCounter;

        public IncrementInventory(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for(int i = 0; i < 10000; i++){
                inventoryCounter.increment();
            }
        }
    }

    public static class DecrementInventory implements Runnable{
        InventoryCounter inventoryCounter;

        public DecrementInventory(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for(int i = 0; i < 10000; i++){
                inventoryCounter.decrement();
            }
        }
    }

    private static class InventoryCounter{
        private volatile int items = 0;
        final Object lock1 = new Object();
        final Object lock2 = new Object();

        public void increment(){
            synchronized (lock1){
                items++;
            }
        }

        public void decrement(){
            synchronized (lock1){
                items--;
            }
        }

        public synchronized int getItems(){
            return items;
        }
    }
}
