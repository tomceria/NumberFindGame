package bus;

import Common.ViewBinder;
import Run.GameMain;
import Socket.Request.SocketRequest_AccessRegister;

import javax.swing.*;

import java.io.IOException;
import java.util.Date;

import util.DateUtil;

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
		String tmpBirthday = this.viewBinder.txtBirthday.getText();

		if (RegisterValidate(username, password, password2, firstName, lastName, email, gender, tmpBirthday)) {
			Date birthday = DateUtil.parseStringToDate(tmpBirthday);
			result = (boolean) GameMain.client.performOneTimeSocketRequest(this.hostname, this.netPort,
					new SocketRequest_AccessRegister(username, password, email, firstName, lastName, gender, birthday));
		}

		return result;
	}

	// Register Form Validate

	public static boolean RegisterValidate(String username, String password, String password2, String firstName,
			String lastName, String email, String gender, String birthday) {
		String emailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		String birthdayRegex = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)"
				+ "(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|"
				+ "-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579]"
				+ "[26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])"
				+ "(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
		Object fields[] = { username, password, password2, email, firstName, lastName, gender, birthday };
		String fieldsLabel[] = { "Username", "Password", "Confirm password", "Email", "First Name", "Last Name",
				"Gender", "Birthday" };

		for (int i = 0; i < fields.length; i++) {
			if (fields[i].equals("")) {
				throw new RuntimeException(fieldsLabel[i] + " cannot be empty");
			}
		}

		if (!birthday.matches(birthdayRegex)) {
			throw new RuntimeException("Invalid Birthday");
		}
		
		if (birthday.matches(birthdayRegex)) {
			if (!DateUtil.checkAge(birthday)) {
				throw new RuntimeException("Invalid Birthday");
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
		public JTextField datePicker;
		public JTextField txtBirthday;

		public RegisterBUS_ViewBinder() {
			super();
		}

		@Override
		public void update() {
		}
	}
}
