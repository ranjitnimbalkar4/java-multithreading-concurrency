package thread.coordination.join;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactorialThead {

    public static void main(String[] args) {

        List<Long> inputNumbers = Arrays.asList(0L, 3435L, 35435l, 2342L, 4656L, 32L, 5556L);

        List<FactorialThread> threads = new ArrayList<>();

        inputNumbers.forEach(inputNumber -> threads.add(new FactorialThread(inputNumber)));

        threads.forEach(Thread :: start);

          threads.forEach(thread -> {
              try {
                  thread.join();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          });

        threads.forEach(thread -> {
            if(thread.isFinished)
                System.out.println(thread.inputNumber + " --> "+thread.getResult());
            else
                System.out.println("Still Calculating Factorial : "+ thread.inputNumber);
        });

    }

    static class FactorialThread extends Thread {
        private long inputNumber;
        BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            result = factorial(inputNumber);
            isFinished = true;
        }

        public BigInteger factorial(long inputNumber){
            BigInteger tempResult = BigInteger.ONE;

            for(long i = inputNumber; i > 0; i--){
                tempResult = tempResult.multiply(BigInteger.valueOf(i));
            }

            return  tempResult;
        }

        public BigInteger getResult() {
            return result;
        }

        public boolean isFinished() {
            return isFinished;
        }

    }

}


