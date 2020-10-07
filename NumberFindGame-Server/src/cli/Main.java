package cli;

import bus.PlayerBUS;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PlayerBUS playerBus = new PlayerBUS();
		playerBus.getAll();
//		String pwString = BCrypt.hashpw("123", BCrypt.gensalt(12));
//		System.out.println(pwString);
	}

}
