package bus;

import Common.ViewBinder;
import Run.GameMain;

import javax.swing.*;
import java.io.IOException;

public class LoginBUS {
    private String netHostname = "127.0.0.1";
    private int netPort = 54321;

    public LoginBUS_ViewBinder viewBinder = new LoginBUS_ViewBinder();

    // Functions

    public void action_LoginSubmit() {
        String username = this.viewBinder.txtUsername.getText();
        String password = new String(this.viewBinder.txtPassword.getPassword());

        try {
            this.performConnectToServer(username, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Private BUSINESS functions

    private void performConnectToServer(String username, String password) throws IOException {
        GameMain.client.start(
                this.netHostname,
                this.netPort,
                username,
                password);
    }

    // Properties

    public String getNetHostname() {
        return netHostname;
    }
    public void setNetHostname(String netHostname) {
        this.netHostname = netHostname;
    }

    public int getNetPort() {
        return netPort;
    }
    public void setNetPort(int netPort) {
        this.netPort = netPort;
    }

    // Inner Classes

    public class LoginBUS_ViewBinder extends ViewBinder {
        public JTextField txtUsername;
        public JPasswordField txtPassword;

        @Override
        public void update() {
        }
    }
}
