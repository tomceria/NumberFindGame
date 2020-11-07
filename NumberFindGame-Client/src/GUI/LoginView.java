package GUI;

import Run.GameMain;
import bus.LoginBUS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView {

    // LoginView.form's Components
    private JPanel contentPane;
    private JLabel lblTitle;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnSubmit;
    private JLabel lblUsername;
    private JLabel lblPassword;

    private LoginBUS loginBUS;

    public LoginView(LoginBUS loginBUS) {
        this.loginBUS = loginBUS;

        $$$setupUI$$$();
        customizeComponents();
        bindListeners();
        initViewBinder();
    }

    // Functions

    private void customizeComponents() {
        return;
    }

    private void bindListeners() {

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Submitting...");
                LoginView.this.loginBUS.action_LoginSubmit();
            }
        });
    }

    private void initViewBinder() {
        LoginView.this.loginBUS.viewBinder.txtUsername = txtUsername;
        LoginView.this.loginBUS.viewBinder.txtPassword = txtPassword;
    }

    // Properties

    public JPanel getContentPane() {
        return contentPane;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setBackground(new Color(-14142061));
        contentPane.setPreferredSize(new Dimension(1024, 768));
        lblTitle = new JLabel();
        lblTitle.setFocusCycleRoot(true);
        Font lblTitleFont = this.$$$getFont$$$("Fira Code", Font.BOLD, 32, lblTitle.getFont());
        if (lblTitleFont != null) lblTitle.setFont(lblTitleFont);
        lblTitle.setForeground(new Color(-1));
        lblTitle.setText("NumberFindGame");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 16, 0);
        contentPane.add(lblTitle, gbc);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setBackground(new Color(-14208619));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(panel1, gbc);
        btnSubmit = new JButton();
        btnSubmit.setText("Login");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(16, 0, 0, 0);
        panel1.add(btnSubmit, gbc);
        lblUsername = new JLabel();
        lblUsername.setForeground(new Color(-1));
        lblUsername.setText("Username");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 8, 8);
        panel1.add(lblUsername, gbc);
        lblPassword = new JLabel();
        lblPassword.setForeground(new Color(-1));
        lblPassword.setText("Password");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, 8);
        panel1.add(lblPassword, gbc);
        txtUsername = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 8, 0);
        panel1.add(txtUsername, gbc);
        txtPassword = new JPasswordField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 100;
        panel1.add(txtPassword, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
