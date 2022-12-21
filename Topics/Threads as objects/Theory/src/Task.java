// You can experiment here, it won’t be checked

import java.util.Scanner;

public class Task {
    public static void main(String[] args) {
        // put your code here
        Thread worker = new SquareWorkerThread("square-worker");
        worker.start(); // start a worker (not run!)

        for (long i = 0; i < 5_555_555_543L; i++) {
            if (i % 1_000_000_000 == 0) {
                System.out.println("Hello from the main!");
            }
        }
    }
}

class SquareWorkerThread extends Thread {
    private final Scanner scanner = new Scanner(System.in);

    public SquareWorkerThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (true) {
            int number = scanner.nextInt();
            if (number == 0) {
                break;
            }
            System.out.println(number * number);
        }
        System.out.println(String.format("%s finished", getName()));
    }
}