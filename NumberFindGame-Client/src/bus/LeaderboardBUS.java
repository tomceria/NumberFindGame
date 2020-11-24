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

import javax.swing.*;
import java.io.IOException;

public class LeaderboardBUS {
	public LeaderboardBUS_ViewBinder viewBinder;

	public LeaderboardBUS() {
		this.viewBinder = new LeaderboardBUS_ViewBinder();
	}

	// Functions

	public boolean action_UpdateSubmit() {
		boolean result = false;

		String username = this.viewBinder.txtSearch.getText();
		
		
		return result;
	}

//	public static boolean searchValidate(String firstName, String lastName, String email) {
//		String emailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
//		String fields[] = { email, firstName, lastName };
//		String fieldsLabel[] = { "First Name", "Last Name", "Email" };
//		for (int i = 0; i < fields.length; i++) {
//			if (fields[i].equals("")) {
//				throw new RuntimeException(fieldsLabel[i] + " cannot be empty");
//			}
//		}
//
//		if (!email.matches(emailRegex)) {
//			throw new RuntimeException("Invalid email address");
//		}
//
//		return true;
//	}

	// Inner Classes

	public class LeaderboardBUS_ViewBinder extends ViewBinder {
		public JTextField txtSearch;

		public LeaderboardBUS_ViewBinder() {
			super();
		}

		@Override
		public void update() {
		}
	}
}
