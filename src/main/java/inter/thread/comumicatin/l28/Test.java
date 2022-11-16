package inter.thread.comumicatin.l28;

import java.io.*;

public class Test {
    private static final String INPUT_FILE = "./out/matrices";
    private static final String OUTPUT_FILE = "./out/matrices_multiply";
    private static final  int N = 10;

    public static void main(String[] args) throws IOException {
        ThreadSafeQueue queue = new ThreadSafeQueue();
        File inputFile = new File(INPUT_FILE);
        File outputFile = new File(OUTPUT_FILE);

        MatrixPairProducer pairProducer = new MatrixPairProducer(new FileReader(inputFile), queue);
        MatrixMultiplierConsumer consumer = new MatrixMultiplierConsumer(new FileWriter(outputFile), queue);

        pairProducer.start();
        consumer.start();
    }
}
