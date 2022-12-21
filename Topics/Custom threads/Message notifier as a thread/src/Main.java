class MessageNotifier extends Thread {

    // write fields to store variables here
    private final String message;
    private final int count;

    public MessageNotifier(String msg, int repeats) {
        // implement the constructor
        message = msg;
        count = repeats;
    }

    @Override
    public void run() {
        // implement the method to print the message stored in a field
        // the number of times stored in the field
        for (int i = 0; i < count; i++) {
            System.out.println(message);
        }
    }
}