package bus;

import Common.ViewBinder;
import Run.GameMain;
import Socket.Request.SocketRequest_AccessChangePassword;
import Socket.Request.SocketRequest_AccessRegister;
import Socket.Request.SocketRequest_AccessUpdateInfo;

import javax.swing.*;
import java.io.IOException;

public class UpdateInfoBUS {
	public UpdateInfoBUS_ViewBinder viewBinder;

	public UpdateInfoBUS() {
		this.viewBinder = new UpdateInfoBUS_ViewBinder();
	}

	// Functions

	public boolean action_UpdateSubmit() {
		boolean result = false;

		String username = this.viewBinder.txtUsername.getText();
		String firstName = this.viewBinder.txtFirstName.getText();
		String lastName = this.viewBinder.txtLastName.getText();
		String email = this.viewBinder.txtEmail.getText();

		if (UpdateValidate(firstName, lastName, email)) {
			try {
				GameMain.client.sendRequest(new SocketRequest_AccessUpdateInfo(username, email, firstName, lastName));
				result = true;
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());

			}
		}

		return result;
	}

	public boolean action_ChangePassword() throws IOException {
		boolean result = false;

		String username = this.viewBinder.txtUsername.getText();
		String password = new String(this.viewBinder.txtPassword.getPassword());
		String password2 = new String(this.viewBinder.txtPassword2.getPassword());

		if (ChangePasswordValidate(password, password2)) {
			try {
				GameMain.client.sendRequest(new SocketRequest_AccessChangePassword(username, password));
				result = true;
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());

			}
		}

		return result;
	}

	// Update info Form Validate

	public static boolean UpdateValidate(String firstName, String lastName, String email) {
		String emailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		String fields[] = { email, firstName, lastName };
		String fieldsLabel[] = { "First Name", "Last Name", "Email" };
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].equals("")) {
				throw new RuntimeException(fieldsLabel[i] + " cannot be empty");
			}
		}

		if (!email.matches(emailRegex)) {
			throw new RuntimeException("Invalid email address");
		}

		return true;
	}

	// Change password form validate
	public static boolean ChangePasswordValidate(String password, String password2) {
		String fields[] = { password, password2 };
		String fieldsLabel[] = { "Password", "Confirm password" };
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].equals("")) {
				throw new RuntimeException(fieldsLabel[i] + " cannot be empty");
			}
		}

		if (password.length() < 5) {
			throw new RuntimeException("Password must be longer than 5 characters");
		}
		if (!password.equals(password2)) {
			throw new RuntimeException("Password confirmation does not match");
		}

		return true;
	}
	// Inner Classes

	public class UpdateInfoBUS_ViewBinder extends ViewBinder {
		public JTextField txtUsername;
		public JPasswordField txtPassword;
		public JPasswordField txtPassword2;
		public JTextField txtFirstName;
		public JTextField txtLastName;
		public JTextField txtEmail;

		public UpdateInfoBUS_ViewBinder() {
			super();
		}

		@Override
		public void update() {
		}
	}
}
