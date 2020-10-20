package util;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class NotifyingThread extends Thread {
    private final Set<IThreadCompleteListener> listeners = new CopyOnWriteArraySet<IThreadCompleteListener>();

    public final void addListener(final IThreadCompleteListener listener) {
        listeners.add(listener);
    }
    public final void removeListener(final IThreadCompleteListener listener) {
        listeners.remove(listener);
    }
    private final void notifyListeners() {
        for (IThreadCompleteListener listener : listeners) {
            listener.notifyOfThreadComplete(this);
        }
    }
    @Override
    public final void run() {
        try {
            doRun();
        } finally {
            notifyListeners();
        }
    }
    public abstract void doRun();

}
