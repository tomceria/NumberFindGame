package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class dateParse {
	public String dateParse(String dateParse) {
		// JDatePicker date to dd/mm/yyyy
		DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		DateFormat inputFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
		Date date = null;
		try {
			date = inputFormat.parse(dateParse);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String strDate = outputFormat.format(date);
		return strDate;
	}
}
