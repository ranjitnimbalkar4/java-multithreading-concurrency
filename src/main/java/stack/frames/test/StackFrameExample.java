package stack.frames.test;

public class StackFrameExample {

    Object reference1 = new Object();

    public static void main(String[] args) {
        int x = 1;
        int y = 2;
        int result = sum(x, y);
    }

    private static int sum(int x, int y) {
        Object reference2 = new Object();
        int a = x;
        int b = y;
        return  a + b;
    }
}
