package util;

public class Maths {
    public static double valueFromTwoRanges(double value, double minA, double maxA, double minB, double maxB) {
        // Từ một value nằm trong khoảng (minA,maxA), cho ra giá trị tỉ lệ tương ứng trong khoảng (minB, maxB)
        double percent = ((value - minA) * 100) / (maxA - minA);

        if (percent < 0) {
            percent = 0;
        } else if (percent > 100) {
            percent = 100;
        }

        double result = ((percent * (maxB - minB)) / 100) + minB;

        return result;
    }
}
