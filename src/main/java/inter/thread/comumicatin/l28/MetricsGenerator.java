package inter.thread.comumicatin.l28;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.StringJoiner;

public class MetricsGenerator {

    private static final String OUTPUT_FILE = "./out/matrices";
    private static final int N = 10;
    private static final int NUMBER_OF_MATRIX_PAIRS = 100000;

    public static void main(String[] args) throws IOException {
        File file = new File(OUTPUT_FILE);
        FileWriter fileWriter = new FileWriter(file);

        Random random = new Random(N);

        for (int n = 0; n < NUMBER_OF_MATRIX_PAIRS; n++) {
           float[][] matrix = createMatrix(random);
           saveToFile(fileWriter, matrix);
        }

        fileWriter.flush();
        fileWriter.close();
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

    private static float[][] createMatrix(Random random) {
        float[][] matrix = new float[N][N];
        for (int i = 0; i < N; i++) {
           matrix[i] = createRow(random);
        }
        return matrix;
    }

    private static float[] createRow(Random random) {
        float[] row = new float[N];
        for (int i = 0; i < N; i++) {
            row[i] = random.nextFloat() * random.nextInt(100);
        }
        return  row;
    }


}
