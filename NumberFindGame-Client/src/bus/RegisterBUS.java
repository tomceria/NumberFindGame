package bus;

import Common.ViewBinder;
import Run.GameMain;
import Socket.Request.SocketRequest_AccessRegister;

import javax.swing.*;
import java.io.IOException;

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

    public void action_RegisterSubmit() throws IOException {
        String username = this.viewBinder.txtUsername.getText();
        String password = new String(this.viewBinder.txtPassword.getPassword());
        String firstName = this.viewBinder.txtFirstName.getText();
        String lastName = this.viewBinder.txtLastName.getText();
        String email = this.viewBinder.txtEmail.getText();

        GameMain.client.performOneTimeSocketRequest(
                this.hostname,
                this.netPort,
                new SocketRequest_AccessRegister(username, password, email, firstName, lastName)
        );
    }

    // Inner Classes

    public class RegisterBUS_ViewBinder extends ViewBinder {
        public JTextField txtUsername;
        public JPasswordField txtPassword;
        public JTextField txtFirstName;
        public JTextField txtLastName;
        public JTextField txtEmail;

        public RegisterBUS_ViewBinder() {
            super();
        }

        @Override
        public void update() {
        }
    }
}
