package bus;

import Common.ViewBinder;
import Run.GameMain;
import Socket.Request.SocketRequest_AccessRegister;

import javax.swing.*;

import org.jdatepicker.impl.JDatePickerImpl;

import java.io.IOException;
import util.dateParse;

public class RegisterBUS {
	private String hostname;
	private int netPort;

	public RegisterBUS_ViewBinder viewBinder;

	public RegisterBUS(String hostname, int netPort) {
		this.hostname = hostname;
		this.netPort = netPort;
		this.viewBinder = new RegisterBUS_ViewBinder();
	}

	// Functions

	public boolean action_RegisterSubmit() throws IOException {
		boolean result = false;
		String username = this.viewBinder.txtUsername.getText();
		String password = new String(this.viewBinder.txtPassword.getPassword());
		String password2 = new String(this.viewBinder.txtPassword2.getPassword());
		String firstName = this.viewBinder.txtFirstName.getText();
		String lastName = this.viewBinder.txtLastName.getText();
		String email = this.viewBinder.txtEmail.getText();
		String gender = this.viewBinder.comboBox.getItemAt(this.viewBinder.comboBox.getSelectedIndex()).toString();
		String tmpBirthday = this.viewBinder.datePicker.getJFormattedTextField().getText();

		if (RegisterValidate(username, password, password2, firstName, lastName, email, gender, tmpBirthday)) {
			dateParse dp = new dateParse();
			String birthday = dp.dateParse(tmpBirthday);

			result = (boolean) GameMain.client.performOneTimeSocketRequest(this.hostname, this.netPort,
					new SocketRequest_AccessRegister(username, password, email, firstName, lastName, gender, birthday));
		}

		return result;
	}

	// Register Form Validate

	public static boolean RegisterValidate(String username, String password, String password2, String firstName,
			String lastName, String email, String gender, String birthday) {
		String emailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		String fields[] = { username, password, password2, email, firstName, lastName, gender, birthday };
		String fieldsLabel[] = { "Username", "Password", "Confirm password", "Email", "First Name", "Last Name",
				"Gender", "Birthday" };

		for (int i = 0; i < fields.length; i++) {
			if (fields[i].equals("")) {
				throw new RuntimeException(fieldsLabel[i] + " cannot be empty");
			}
		}

		if (username.length() > 15) {
			throw new RuntimeException("Username cannot be longer than 15 characters");
		}
		if (password.length() < 5) {
			throw new RuntimeException("Password must be longer than 5 characters");
		}
		if (!password.equals(password2)) {
			throw new RuntimeException("Password confirmation does not match");
		}
		if (!email.matches(emailRegex)) {
			throw new RuntimeException("Invalid email address");
		}

		return true;
	}

	// Inner Classes

	public class RegisterBUS_ViewBinder extends ViewBinder {
		public JTextField txtUsername;
		public JPasswordField txtPassword;
		public JPasswordField txtPassword2;
		public JTextField txtFirstName;
		public JTextField txtLastName;
		public JTextField txtEmail;
		public JComboBox comboBox;
		public JDatePickerImpl datePicker;

		public RegisterBUS_ViewBinder() {
			super();
		}

		@Override
		public void update() {
		}
	}
}
