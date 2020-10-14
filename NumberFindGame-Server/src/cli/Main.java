package cli;

import bus.PlayerBUS;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PlayerBUS playerBus = new PlayerBUS();
		System.out.print(playerBus.login("saidan00", "123"));
	}

}
