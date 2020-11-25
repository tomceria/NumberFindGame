package bus;

import Common.ViewBinder;
import Run.GameMain;
import Socket.Request.SocketRequest_LeaderboardAll;
import Socket.Request.SocketRequest_LeaderboardUser;
import Socket.Response.SocketResponse_LeaderboardResult;
import dto.LeaderBoard;
import dto.PagedResult;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LeaderboardBUS {
	public LeaderboardBUS_ViewBinder viewBinder;

	private ArrayList<LeaderBoard> rankings;

	public LeaderboardBUS() {
		this.viewBinder = new LeaderboardBUS_ViewBinder();
	}

	public void listen_showResult(SocketResponse_LeaderboardResult response) {

		PagedResult<LeaderBoard> pagedResult = response.result;
//		if (pagedResult.getResult().size()) {
//			throw new RuntimeException("No user found1");
//		}
		// int i = 0;
		this.rankings = pagedResult.getResult();

		this.viewBinder.update();
	}

	public void ui_setLeaderboard(DefaultTableModel model) {
	    model.setRowCount(0);

		for (LeaderBoard ranking : this.rankings) {
			model.addRow(new Object[] {
					ranking.getRanking(),
					ranking.getUsername(),
					ranking.getSumRP(),
					ranking.getWinrate(),
					ranking.getTotalMatches()
			});
		}
	}
	// Functions

	public void action_GetLeaderboardAll(int page) {
		boolean result = false;

		GameMain.client.sendRequest(new SocketRequest_LeaderboardAll(page));

		result = true;

		return;
	}

	public void action_GetLeaderboardUser(String username) {
		// ArrayList<LeaderBoard> result = new ArrayList<>();
		if (username.trim().equals("")) {
			throw new RuntimeException("Please enter search keyword");
		}

		GameMain.client.sendRequest(new SocketRequest_LeaderboardUser(username));

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
		public DefaultTableModel leaderboardTableModel;

		public LeaderboardBUS_ViewBinder() {
			super();
		}

		@Override
		public void update() {
			ui_setLeaderboard(leaderboardTableModel);
			// this.stopUpdatePeriod();
		}
	}
}
