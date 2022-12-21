class Predicate {
    public static final TernaryIntPredicate ALL_DIFFERENT = // Write a lambda expression here
            (a, b, c) -> a != b && b != c && a != c;

    @FunctionalInterface
    public interface TernaryIntPredicate {
        // Write a method
        boolean test(int a, int b, int c);
    }
}
