package util;

public class Interpolate {
    public static int interpolateInt(double ratio, int value1, int value2) {
        return (int)Math.abs((ratio * value1) + ((1 - ratio) * value2));
    }
}
