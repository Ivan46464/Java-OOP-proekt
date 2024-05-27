import java.util.TimerTask;

class EraserThread extends TimerTask {
    private boolean stop;

    public EraserThread(String prompt) {
        System.out.print(prompt);
    }

    public void run() {
        if (!stop) {
            System.out.print("\010*");
        }
    }

    public void stopMasking() {
        this.stop = true;
    }
}