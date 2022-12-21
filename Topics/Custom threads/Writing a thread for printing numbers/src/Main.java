class NumbersThread extends Thread {

    private int start;
    private int end;

    public NumbersThread(int from, int to) {
        // implement the constructor
        start = from;
        end = to;
    }

    // you should override some method here

    @Override
    public void run() {
        for (int i = start; i <= end ; i++) {
            System.out.println(i);
        }
    }
}