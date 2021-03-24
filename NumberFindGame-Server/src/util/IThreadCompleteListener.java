package util;

public interface IThreadCompleteListener {
    void notifyOfThreadComplete(final Runnable thread);
}
