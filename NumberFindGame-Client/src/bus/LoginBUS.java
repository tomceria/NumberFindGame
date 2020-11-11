package bus;

import Common.ViewBinder;
import Run.GameMain;

import javax.security.sasl.AuthenticationException;
import javax.swing.*;
import java.io.IOException;

public class LoginBUS {
    private final String HOSTNAME = "127.0.0.1";
    private int netPort;

    public LoginBUS_ViewBinder viewBinder;

    public LoginBUS() {
        this.netPort = 54321;
        this.viewBinder = new LoginBUS_ViewBinder();
    }

    // Functions

    public void action_LoginSubmit() throws IOException {
        String username = this.viewBinder.txtUsername.getText();
        String password = new String(this.viewBinder.txtPassword.getPassword());
        String hostname = this.viewBinder.txtNetIp.getText();

        this.performConnectToServer(hostname, username, password);
    }

    // Private BUSINESS functions

    private void performConnectToServer(String hostname, String username, String password) throws IOException {
        GameMain.client.start(hostname, this.netPort, username, password);
    }

    // Properties

    public int getNetPort() {
        return netPort;
    }

    // Inner Classes

    public class LoginBUS_ViewBinder extends ViewBinder {
        public JTextField txtUsername;
        public JPasswordField txtPassword;
        public JTextField txtNetIp;

        public LoginBUS_ViewBinder() {
            super();

            /**
             * Thực hiện vòng lặp update() (với startUpdatePeriod()) trong lúc đợi các Component được render
             * Khi render hoàn tất, các Component được gán dữ liệu của mình,
             * Kết thúc vòng lặp với stopUpdatePeriod()
             */
            this.update();
            this.startUpdatePeriod();
        }

        @Override
        public void update() {
            /**
             * ViewBinder này áp dụng cơ chế Update-once-initiated
             */

            if (this.txtNetIp != null) {
                this.txtNetIp.setText(HOSTNAME);
                this.stopUpdatePeriod();
            }
        }
    }
}
