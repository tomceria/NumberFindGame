package Common;

public class OrdinalNumber {
    public static String generate(int number) {
        String result;
        String[] suffixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch (number % 100) {
            case 11:
            case 12:
            case 13:
                result = number + "th";
            default:
                result = number + suffixes[number % 10];
        }

        return result;
    }
}
