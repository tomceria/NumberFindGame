package GUI;

import bus.LoginBUS;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LoginView {

	private LoginBUS loginBUS;

	private JPanel contentPane = new JPanel();
	private static JLabel lblUsername = new JLabel("Username");
	private static JLabel lblPassword = new JLabel("Password");
	private static JTextField txtUsername = new JTextField();
	private static JPasswordField txtPassword = new JPasswordField();
	private static JLabel lblTitle = new JLabel("Number Find Game");
	private static JButton btnSubmit = new JButton("Login");
	private static JLabel lnkGotoRegister = new JLabel("Create a new account...");
	private static JLabel lblNetIp = new JLabel("Server Hostname");
	private static JTextField txtNetIp;

	public LoginView(LoginBUS loginBUS) {
		this.loginBUS = loginBUS;

		loginViewSetup();
		customizeComponents();
		bindListeners();
		initViewBinder();
	}

	public void loginViewSetup() {
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtUsername.setColumns(10);
		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 15));

		contentPane.setBackground(new Color(-14142061));
		contentPane.setSize(1024, 768);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 15));

		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPassword.setColumns(10);
		lnkGotoRegister.setForeground(Color.WHITE);
		lnkGotoRegister.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNetIp.setForeground(Color.WHITE);

		lblNetIp.setFont(new Font("Tahoma", Font.BOLD, 13));

		txtNetIp = new JTextField();
		txtNetIp.setEditable(true);
		txtNetIp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtNetIp.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(contentPane);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(358)
								.addComponent(lblNetIp, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(txtNetIp, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup().addGap(413).addComponent(lnkGotoRegister))
						.addGroup(gl_panel.createSequentialGroup().addGap(366)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(gl_panel.createSequentialGroup()
												.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 90,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(txtUsername,
														GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panel.createSequentialGroup().addGap(366)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(btnSubmit, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGroup(gl_panel.createSequentialGroup()
												.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 90,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(txtPassword,
														GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)))))
						.addContainerGap(364, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGap(289).addComponent(lblTitle)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup().addGap(3).addComponent(lblUsername)))
						.addGap(5)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup().addGap(3).addComponent(lblPassword)))
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnSubmit)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lnkGotoRegister).addGap(191)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNetIp, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtNetIp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))));
		contentPane.setLayout(gl_panel);

	}

	// Functions

	private void customizeComponents() {
		lnkGotoRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	private void bindListeners() {
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					LoginView.this.loginBUS.action_LoginSubmit();
				} catch (Exception exception) {
					String message = exception.getMessage();
					if (message.contains("Connection refused")) {
						message = "Game server is currently unavailable.";
					}

					JOptionPane.showMessageDialog(LoginView.this.contentPane, message, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		lnkGotoRegister.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginView.this.loginBUS.action_GotoRegisterView();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lnkGotoRegister.setText("<html><a href='' style='color: white'>Create a new account...</a></html>");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lnkGotoRegister.setText("Create a new account...");
			}
		});
	}

    private void initViewBinder() {
        this.loginBUS.viewBinder.txtUsername = txtUsername;
        this.loginBUS.viewBinder.txtPassword = txtPassword;
        this.loginBUS.viewBinder.txtNetIp = txtNetIp;
    }

	// Properties

	public JPanel getContentPane() {
		return contentPane;
	}

}
