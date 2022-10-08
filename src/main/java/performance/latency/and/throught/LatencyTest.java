package performance.latency.and.throught;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class LatencyTest {

    public static final String SOURCE_FILE = "many-flowers.jpg";
    public static final String DESTINATION_FILE = "./out/u-flowers.jpg";

    public static void main(String[] args) throws IOException, InterruptedException {

        BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        long startTime = System.currentTimeMillis();
        //recolorSingleThreaded(originalImage, resultImage);
        // recolorMultithreaded(originalImage, resultImage, numberOfThreads);
        recolorMultithreadedUsingThreadPool(originalImage, resultImage, executorService);
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        File outputFile = new File(DESTINATION_FILE);
        ImageIO.write(resultImage, "jpg", outputFile);

        System.out.println(String.valueOf(duration));

        executorService.shutdown();
    }

    public static void recolorMultithreaded(BufferedImage originalImage, BufferedImage resultImage, int numberOfThreads) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight() / numberOfThreads;

        ArrayList<Thread> threads = new ArrayList<Thread>();

        for(int i = 0; i < numberOfThreads; i++){
            int xForThread = 0;
            int yForThread = height * i;

            Thread t = new Thread(() -> {
                recolorImage(originalImage, resultImage, xForThread, yForThread, width, height);
            });
            threads.add(t);
        }

        threads.forEach(t -> t.start());
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void recolorMultithreadedUsingThreadPool(BufferedImage originalImage, BufferedImage resultImage, ExecutorService executorService) throws InterruptedException {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight() / 16;
        ArrayList<Callable<String>> runnable = new ArrayList<>();

        for(int i = 0; i < 16; i++){
            int xForThread = 0;
            int yForThread = height * i;
            runnable.add(() -> {
                recolorImage(originalImage, resultImage, xForThread, yForThread, width, height);
                return  "completed - "+ yForThread;
            });
        }

        List<Future<String>> futures = executorService.invokeAll(runnable);

        futures.forEach(f -> {
            try {
                System.out.println(f.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
        recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
    }

    public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner, int topCorner,
                                    int width, int height) {
        for(int x = leftCorner ; x < leftCorner + width && x < originalImage.getWidth() ; x++) {
            for(int y = topCorner ; y < topCorner + height && y < originalImage.getHeight() ; y++) {
                recolorPixel(originalImage, resultImage, x , y);
            }
        }
    }

    public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
        int rgb = originalImage.getRGB(x, y);

        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed;
        int newGreen;
        int newBlue;

        if(isShadeOfGray(red, green, blue)) {
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green - 80);
            newBlue = Math.max(0, blue - 20);
        } else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }
        int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
        setRGB(resultImage, x, y, newRGB);
    }

    public static void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }

    public static boolean isShadeOfGray(int red, int green, int blue) {
        return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs( green - blue) < 30;
    }

    public static int createRGBFromColors(int red, int green, int blue) {
        int rgb = 0;

        rgb |= blue;
        rgb |= green << 8;
        rgb |= red << 16;

        rgb |= 0xFF000000;

        return rgb;
    }

    public static int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }

    public static int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    public static int getBlue(int rgb) {
        return rgb & 0x000000FF;
    }
}
