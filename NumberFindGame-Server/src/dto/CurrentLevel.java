package dto;

import java.io.Serializable;
import java.time.LocalTime;

public class CurrentLevel implements Serializable {
    int value;
    LocalTime timeStart;

    public CurrentLevel() {
    }

    public CurrentLevel(CurrentLevel currentLevel) {
        this.value = currentLevel.value;
        this.timeStart = currentLevel.timeStart;
    }

    public int getValue() {
        return value;
    }

    protected void setValue(int value) {
        this.value = value;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    protected void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }
}
