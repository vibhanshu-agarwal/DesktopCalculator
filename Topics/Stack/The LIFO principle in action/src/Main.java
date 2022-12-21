import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int noElements = Integer.parseInt(scanner.nextLine());
        Deque<String> deque = new ArrayDeque<>();
        for(int i = 0; i < noElements; i++) {
            String line = scanner.nextLine();
            deque.offer(line);
//            System.out.println(line);
        }
        while(!deque.isEmpty()) {
            System.out.println(deque.pollLast());
        }
    }
}