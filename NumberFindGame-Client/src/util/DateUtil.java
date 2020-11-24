package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

	public static Date parseStringToDate(String dateStr) {
		// String date to dd/mm/yyyy Date
		Date date = null;
		try {
			date = (Date) dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String parseDateToString(Date date) {
		return dateFormat.format(date);
	}
}
