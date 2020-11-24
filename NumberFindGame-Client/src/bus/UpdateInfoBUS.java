package bus;

import Common.ViewBinder;
import Run.GameMain;
import Socket.GameClient;
import Socket.Request.SocketRequest_AccessChangePassword;
import Socket.Request.SocketRequest_AccessRegister;
import Socket.Request.SocketRequest_AccessUpdateInfo;
import dto.MatchPlayer;
import dto.PlayerDTO;
import util.BCrypt;
import util.dateParse;

import javax.swing.*;

import org.jdatepicker.impl.JDatePickerImpl;

import java.io.IOException;

public class UpdateInfoBUS {
	public UpdateInfoBUS_ViewBinder viewBinder;
	public static MatchPlayer matchPlayer = ((GameClient) GameMain.client).getClientPlayer();
	public static PlayerDTO player = matchPlayer.getPlayer();

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
		String gender = this.viewBinder.comboBox.getItemAt(this.viewBinder.comboBox.getSelectedIndex()).toString();
		String tmpBirthday = this.viewBinder.datePicker.getJFormattedTextField().getText();

		if (UpdateValidate(firstName, lastName, email, gender, tmpBirthday)) {
			try {
				dateParse dp = new dateParse();
				String birthday = dp.dateParse(tmpBirthday);
				
				GameMain.client.sendRequest(new SocketRequest_AccessUpdateInfo(username, email, firstName, lastName, gender, birthday));

				player.setEmail(email);
				player.setFirstName(firstName);
				player.setLastName(lastName);
				player.setGender(gender);
				player.setBirthday(birthday);
				matchPlayer.setPlayer(player);

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
		String oldPassword = new String(this.viewBinder.txtOldPassword.getPassword());
		String newPassword = new String(this.viewBinder.txtNewPassword.getPassword());
		String newPassword2 = new String(this.viewBinder.txtNewPassword2.getPassword());

		if (ChangePasswordValidate(oldPassword, newPassword, newPassword2)) {
			try {
				GameMain.client.sendRequest(new SocketRequest_AccessChangePassword(username, newPassword));

				player.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt(12)));
				matchPlayer.setPlayer(player);

				result = true;
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());

			}
		}

		return result;
	}

	// Update info Form Validate

	public static boolean UpdateValidate(String firstName, String lastName, String email, String gender,
			String birthday) {
		String emailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		String fields[] = { email, firstName, lastName, gender, birthday };
		String fieldsLabel[] = { "First Name", "Last Name", "Email", "Gender", "Birthday" };
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
	public static boolean ChangePasswordValidate(String oldPassword, String newPassword, String newPassword2) {
		// PlayerDTO player = ((GameClient)
		// GameMain.client).getClientPlayer().getPlayer();
		String fields[] = { oldPassword, newPassword, newPassword2 };
		String fieldsLabel[] = { "Old Password", "New Password", "Confirm password" };
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].equals("")) {
				throw new RuntimeException(fieldsLabel[i] + " cannot be empty");
			}
		}

		if (!BCrypt.checkpw(oldPassword, player.getPassword())) {
			throw new RuntimeException("Old Password is incorrect");
		}

		if (newPassword.length() < 5) {
			throw new RuntimeException("New Password must be longer than 5 characters");
		}

		if (!newPassword.equals(newPassword2)) {
			throw new RuntimeException("New Password confirmation does not match");
		}

		return true;
	}
	// Inner Classes

	public class UpdateInfoBUS_ViewBinder extends ViewBinder {
		public JTextField txtUsername;
		public JPasswordField txtOldPassword;
		public JPasswordField txtNewPassword;
		public JPasswordField txtNewPassword2;
		public JTextField txtFirstName;
		public JTextField txtLastName;
		public JTextField txtEmail;
		public JComboBox comboBox;
		public JDatePickerImpl datePicker;

		public UpdateInfoBUS_ViewBinder() {
			super();
		}

		@Override
		public void update() {
		}
	}
}
