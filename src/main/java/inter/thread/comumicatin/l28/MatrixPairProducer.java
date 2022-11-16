package inter.thread.comumicatin.l28;

import jdk.nashorn.internal.ir.WhileNode;

import java.io.FileReader;
import java.util.Scanner;

public class MatrixPairProducer extends Thread{
    private static final int N = 10;
    private Scanner scanner;
    private ThreadSafeQueue queue;

    public MatrixPairProducer(FileReader reader, ThreadSafeQueue queue) {
        this.scanner = new Scanner(reader);
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            float[][] matrix1 = readMatrix();
            float[][] matrix2 = readMatrix();
            if(matrix1 == null || matrix2 == null){
                queue.terminate();
                System.out.println("No more matrix pairs....");
                return;
            }
            MatrixPair pair = new MatrixPair();
            pair.matrix1 = matrix1;
            pair.matrix2 = matrix2;

            queue.add(pair);
        }

    }

    private float[][] readMatrix(){
        float [][] matrixFromFile = new float[N][N];

        for (int i = 0; i < N; i++) {
            if(!scanner.hasNext()) {
                return null;
            }
            String[] rowHavingStringElement = scanner.nextLine().split(",");
            for (int j = 0; j < N; j++) {
               matrixFromFile[i][j] = Float.parseFloat(rowHavingStringElement[j]);
            }
        }
        scanner.nextLine();
        return matrixFromFile;
    }
}
