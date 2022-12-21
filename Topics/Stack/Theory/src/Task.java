// You can experiment here, it won’t be checked

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

public class Task {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // put your code here
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        JLabel label = new JLabel("Long Running Task");
        final JTextArea textArea = new JTextArea();
        final JProgressBar progressBar = new JProgressBar(0, 100);

        frame.add(label, BorderLayout.PAGE_START);
        frame.add(textArea);
        frame.add(progressBar, BorderLayout.PAGE_END);

        ProgressBarTask task = new ProgressBarTask(textArea, progressBar);
        task.execute();
        task.get();
        System.exit(0);
    }
}

class ProgressBarTask extends SwingWorker<Integer, Integer> {
    private int counter;
    private final JTextArea textArea;
    private final JProgressBar progressBar;

    public ProgressBarTask(JTextArea textArea, JProgressBar progressBar) {
        this.textArea = textArea;
        this.progressBar = progressBar;
    }

    @Override
    protected Integer doInBackground() throws Exception {
        // put your code here
        while (counter < 10 && !isCancelled()) {
            Thread.sleep(100L);
            publish(counter++);
            setProgress(counter);
        }
        return counter;
    }

    @Override
    protected void process(java.util.List<Integer> chunks) {
        // put your code
        int value = chunks.get(0);

        textArea.setText("loading " + value + "%");
        progressBar.setValue(value);
    }

}