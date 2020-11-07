package bus;

import Common.ViewBinder;

import javax.swing.*;

public class LoginBUS {
    public LoginBUS_ViewBinder viewBinder = new LoginBUS_ViewBinder();

    // Functions

    public void action_LoginSubmit() {
        String username = this.viewBinder.txtUsername.getText();
        char[] password = this.viewBinder.txtPassword.getPassword();
        System.out.println(String.format("ACTION REQUEST RECEIVED. %s %s",
                username, new String(password)
                ));
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
