// You can experiment here, it won’t be checked

import java.sql.SQLOutput;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Task {
    public static void main(String[] args) {
        // put your code here
        Function<String, Integer> func = Integer::parseInt;
    }
}

class Article {

    String name;
    String text;
    String[] authors;

    public String[] getAuthors() {
        return authors;
    }

    // other getters and setters
}