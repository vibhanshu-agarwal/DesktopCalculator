// You can experiment here, it won’t be checked

import java.util.ArrayDeque;
import java.util.Deque;

public class Task {
  public static void main(String[] args) {
    // put your code here
    Deque<Integer> deque= new ArrayDeque<Integer>();
    deque.add(1);
    deque.add(2);
    deque.add(3);
    deque.add(4);
    System.out.println(deque);
    deque.push(5);
    System.out.println(deque);
    System.out.println(deque.peek());
    System.out.println(deque);
    System.out.println(deque.pop());
    System.out.println(deque);
  }
}
