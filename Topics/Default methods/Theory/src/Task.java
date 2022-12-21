// You can experiment here, it won’t be checked

import java.util.function.Function;

public class Task {
    public static void main(String[] args) {
        // put your code here
        // It prints the number of digits: 4
        printResultOfLambda(s -> {
            int count = 0;
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    count++;
                }
            }
            return count;
        });
    }

    private static void printResultOfLambda(Function<String, Integer> function) {
        System.out.println(function.apply("HAPPY NEW YEAR 3000!"));
    }
}
