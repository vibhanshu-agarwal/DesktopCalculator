// You can experiment here, it won’t be checked

import java.util.function.Function;

public class Task {
    public static void main(String[] args) {
        // put your code here
        Function<Long, Long> square = Functions::square;
        System.out.println(square.apply(10L));

    }

}

class Functions {
    public static long square(long val) {
        return val * val;
    }
}