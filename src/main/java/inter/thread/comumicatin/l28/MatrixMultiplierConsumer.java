package inter.thread.comumicatin.l28;

import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

public class MatrixMultiplierConsumer extends  Thread{
    private static final int N = 10;
    ThreadSafeQueue queue;
    FileWriter writer;

    public MatrixMultiplierConsumer(FileWriter writer,ThreadSafeQueue queue) {
        this.queue = queue;
        this.writer = writer;
    }

    private static void saveToFile(FileWriter fileWriter, float[][] matrix) throws IOException {
        //save the matrix to file
        for (int i = 0; i < N; i++) {
            StringJoiner stringJoiner = new StringJoiner(", ");
            for (int j = 0; j < N; j++) {
                stringJoiner.add(String.format("%.2f", matrix[i][j]));
            }
            fileWriter.write(stringJoiner.toString());
            fileWriter.write('\n');
        }
        fileWriter.write('\n');
    }

    @Override
    public void run() {

        while (true){
            MatrixPair pair = queue.remove();
            if (pair == null){
                System.out.println("No more pairs to multiply...");
                break;
            }
            float[][] multiplyMatrix = multiplyMatrix(pair.matrix1, pair.matrix2);
            try {
                saveToFile(writer, multiplyMatrix);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private float[][] multiplyMatrix(float[][] matrix1, float[][] matrix2){
        float[][] result = new float[N][N];
        for(int r = 0; r < N; r++){
            for(int c = 0; c < N; c++){
                for (int k = 0; k < N; k++) {
                    result[r][c] = result[r][c] + matrix1[r][k] * matrix2[k][c];
                }
            }
        }
        return result;
    }
}
