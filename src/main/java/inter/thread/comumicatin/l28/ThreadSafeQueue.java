package inter.thread.comumicatin.l28;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadSafeQueue {

    private Queue<MatrixPair> queue = new LinkedList<>();
    private boolean isEmpty = true;
    private boolean isTerminate = false;

    public synchronized void add(MatrixPair matrixPair){
        queue.add(matrixPair);
        isEmpty = false;
        notify();
    }

    public synchronized MatrixPair remove(){
        while (isEmpty && !isTerminate) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        if(queue.size() == 1){
            isEmpty = true;
        }
        if(queue.size() == 0 && isTerminate){
            return null;
        }
        System.out.println("Queue size : "+ queue.size());
        return queue.remove();
    }


    public synchronized void terminate(){
        isTerminate = true;
        notifyAll();
    }

}
