package race.condition.automic.operation;

public class MetricsTest {

    public static void main(String[] args) throws InterruptedException {

        Metrics metrics = new Metrics();

        BusinessLogic businessLogicThread = new BusinessLogic(metrics);
        BusinessLogic businessLogicThread1 = new BusinessLogic(metrics);
        MetricsPrinter metricsPrinterThread = new MetricsPrinter(metrics);

        businessLogicThread.start();
        businessLogicThread1.start();
        metricsPrinterThread.start();

        businessLogicThread.join();
        businessLogicThread.join();
        businessLogicThread1.join();

    }

    public static class MetricsPrinter extends Thread{
        private Metrics metrics;

        public MetricsPrinter(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run(){
            while (true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Current Average : "+ metrics.getAverage());
            }
        }
    }

    public static class BusinessLogic extends Thread{
        private Metrics metrics;

        public BusinessLogic(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run(){
            while (true){
                long start = System.currentTimeMillis();

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                long end = System.currentTimeMillis();

                metrics.addSample(end - start);
            }
        }
    }

    public static class Metrics {
        private long count = 0;
        private volatile double average = 0;

        public void addSample(long sample){
            double currentSum = average * count;
            count++;
            average = (currentSum + sample) / count;
        }

        public double getAverage(){
            return average;
        }
    }

}
