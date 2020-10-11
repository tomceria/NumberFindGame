package Common;

import java.util.Timer;
import java.util.TimerTask;

public abstract class ViewBinder {
    private Timer timer = new Timer();
    private int renderRate = 250;

    public ViewBinder() {
    }

    public ViewBinder(int renderRate) {
        this.renderRate = renderRate;
    }

    public void startUpdatePeriod() {
        timer.schedule(updateUiTask, 0, renderRate);
    }

    public void stopUpdatePeriod() {
        timer.cancel();
    }

    public abstract void update();

    private TimerTask updateUiTask = new TimerTask() {
        @Override
        public void run() {
            update();
        }
    };
}