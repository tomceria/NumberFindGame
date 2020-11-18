package GUI;

import bus.RegisterBUS;
import bus.ViewBUS;
import dto.PlayerDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterView {

    /*
          LoginView.form's Components
         */
    private JPanel contentPane;
    // ViewBinder's components
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtPassword2;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    // Others
    private JButton btnSubmit;
    private JButton btnNavBack;
    private JPanel formPane;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblPassword2;
    private JLabel lblFirstName;
    private JLabel lblLastName;
    private JLabel lblEmail;
    private JPanel navPane;
    private JLabel lblTitle;

    private RegisterBUS registerBUS;

    public RegisterView(RegisterBUS registerBUS) {
        this.registerBUS = registerBUS;

        $$$setupUI$$$();
        bindListeners();
        initViewBinder();
    }

    private void bindListeners() {
        btnNavBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewBUS.gotoLoginView();
            }
        });
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean result = RegisterView.this.registerBUS
                            .action_RegisterSubmit();
                    if (result == true) {
                        ViewBUS.gotoLoginView();
                        JOptionPane.showMessageDialog(
                                RegisterView.this.contentPane,
                                "Your account has been created.",
                                "Information",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                RegisterView.this.contentPane,
                                "Unknown register error",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(
                            RegisterView.this.contentPane,
                            exception.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }

    private void initViewBinder() {
        RegisterView.this.registerBUS.viewBinder.txtUsername = txtUsername;
        RegisterView.this.registerBUS.viewBinder.txtPassword = txtPassword;
        RegisterView.this.registerBUS.viewBinder.txtPassword2 = txtPassword2;
        RegisterView.this.registerBUS.viewBinder.txtFirstName = txtFirstName;
        RegisterView.this.registerBUS.viewBinder.txtLastName = txtLastName;
        RegisterView.this.registerBUS.viewBinder.txtEmail = txtEmail;
    }

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
        formPane = new JPanel();
        formPane.setLayout(new GridBagLayout());
        formPane.setBackground(new Color(-14142061));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(formPane, gbc);
        txtUsername = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 150;
        formPane.add(txtUsername, gbc);
        lblUsername = new JLabel();
        lblUsername.setForeground(new Color(-1));
        lblUsername.setText("Username");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPane.add(lblUsername, gbc);
        lblPassword = new JLabel();
        lblPassword.setForeground(new Color(-1));
        lblPassword.setText("Password");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPane.add(lblPassword, gbc);
        txtFirstName = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPane.add(txtFirstName, gbc);
        lblFirstName = new JLabel();
        lblFirstName.setForeground(new Color(-1));
        lblFirstName.setText("First name");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        formPane.add(lblFirstName, gbc);
        txtLastName = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPane.add(txtLastName, gbc);
        lblLastName = new JLabel();
        lblLastName.setForeground(new Color(-1));
        lblLastName.setText("Last name");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        formPane.add(lblLastName, gbc);
        txtEmail = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPane.add(txtEmail, gbc);
        lblEmail = new JLabel();
        lblEmail.setForeground(new Color(-1));
        lblEmail.setText("Email");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        formPane.add(lblEmail, gbc);
        txtPassword = new JPasswordField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPane.add(txtPassword, gbc);
        final JSeparator separator1 = new JSeparator();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 16;
        formPane.add(separator1, gbc);
        btnSubmit = new JButton();
        btnSubmit.setText("Submit");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPane.add(btnSubmit, gbc);
        final JSeparator separator2 = new JSeparator();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 24;
        formPane.add(separator2, gbc);
        lblPassword2 = new JLabel();
        lblPassword2.setForeground(new Color(-1));
        lblPassword2.setText("Confirm Password");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        formPane.add(lblPassword2, gbc);
        txtPassword2 = new JPasswordField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPane.add(txtPassword2, gbc);
        navPane = new JPanel();
        navPane.setLayout(new GridBagLayout());
        navPane.setBackground(new Color(-14142061));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(navPane, gbc);
        btnNavBack = new JButton();
        btnNavBack.setText("<< Back");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.05;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        navPane.add(btnNavBack, gbc);
        lblTitle = new JLabel();
        lblTitle.setForeground(new Color(-1));
        lblTitle.setText("Create a new account");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gbc.fill = GridBagConstraints.VERTICAL;
        navPane.add(lblTitle, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.15;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        navPane.add(spacer1, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
