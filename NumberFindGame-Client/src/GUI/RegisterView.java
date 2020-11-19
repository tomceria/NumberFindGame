package GUI;

import bus.RegisterBUS;
import bus.ViewBUS;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterView {

	/*
	 * LoginView.form's Components
	 */
	private JPanel contentPane = new JPanel();
	// ViewBinder's components
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JPasswordField txtPassword2;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtEmail;
	// Others
	private JButton btnSubmit = new JButton("Submit");
	private JButton btnNavBack = new JButton("<< Back");
	private RegisterBUS registerBUS;

	public RegisterView(RegisterBUS registerBUS) {
		this.registerBUS = registerBUS;

		registerViewSetup();
		bindListeners();
		initViewBinder();
	}

	public void registerViewSetup() {
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.setSize(1024, 768);
		contentPane.setBackground(Color.decode("#006994"));

		JLabel lblTitle = new JLabel("Create new account");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 32));

		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtUsername.setColumns(10);

		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setForeground(Color.WHITE);
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 15));

		txtFirstName = new JTextField();
		txtFirstName.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtFirstName.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setForeground(Color.WHITE);
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 15));

		txtLastName = new JTextField();
		txtLastName.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtLastName.setColumns(10);

		JLabel lblPassword2 = new JLabel("Confirm Password");
		lblPassword2.setForeground(Color.WHITE);
		lblPassword2.setFont(new Font("Tahoma", Font.BOLD, 15));

		txtPassword2 = new JPasswordField();
		txtPassword2.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 15));

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtEmail.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0,0,0,1));
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.rowWeights = new double[] { 0.0 };
		gbl_panel_1.rowHeights = new int[] { 45 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_panel_1.columnWidths = new int[] { 150, 150, 150 };
		panel_1.setLayout(gbl_panel_1);

		btnNavBack.setFont(new Font("Tahoma", Font.BOLD, 17));
		GridBagConstraints gbc_btnNavBack = new GridBagConstraints();
		gbc_btnNavBack.fill = GridBagConstraints.BOTH;
		gbc_btnNavBack.insets = new Insets(0, 0, 0, 50);
		gbc_btnNavBack.gridx = 0;
		gbc_btnNavBack.gridy = 0;
		panel_1.add(btnNavBack, gbc_btnNavBack);

		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 17));
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.fill = GridBagConstraints.VERTICAL;
		gbc_btnSubmit.insets = new Insets(0, 0, 0, 5);
		gbc_btnSubmit.gridx = 1;
		gbc_btnSubmit.gridy = 0;
		panel_1.add(btnSubmit, gbc_btnSubmit);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUsername.setText("");
				txtPassword.setText("");
				txtPassword2.setText("");
				txtFirstName.setText("");
				txtLastName.setText("");
				txtEmail.setText("");
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 17));
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.insets = new Insets(0, 50, 0, 0);
		gbc_btnReset.fill = GridBagConstraints.BOTH;
		gbc_btnReset.gridx = 2;
		gbc_btnReset.gridy = 0;
		panel_1.add(btnReset, gbc_btnReset);
		GroupLayout gl_formPane = new GroupLayout(contentPane);
		gl_formPane.setHorizontalGroup(gl_formPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_formPane.createSequentialGroup().addGap(323).addComponent(lblTitle,
						GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_formPane.createSequentialGroup().addGap(162)
						.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
						.addGap(74)
						.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
						.addGap(55).addComponent(lblFirstName).addGap(17)
						.addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_formPane.createSequentialGroup().addGap(162)
						.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
						.addGap(76)
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
						.addGap(55)
						.addComponent(lblLastName, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
						.addGap(19)
						.addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_formPane.createSequentialGroup().addGap(162)
						.addComponent(lblPassword2, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
						.addGap(11)
						.addComponent(txtPassword2, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
						.addGap(55).addComponent(lblEmail).addGap(58)
						.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_formPane.createSequentialGroup().addGap(162).addComponent(panel_1,
						GroupLayout.PREFERRED_SIZE, 700, GroupLayout.PREFERRED_SIZE)));
		gl_formPane.setVerticalGroup(gl_formPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_formPane.createSequentialGroup().addGap(234).addComponent(lblTitle).addGap(49)
						.addGroup(gl_formPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_formPane.createSequentialGroup().addGap(4).addComponent(lblUsername))
								.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_formPane.createSequentialGroup().addGap(4).addComponent(lblFirstName))
								.addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_formPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_formPane.createSequentialGroup().addGap(4).addComponent(lblPassword))
								.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_formPane.createSequentialGroup().addGap(4).addComponent(lblLastName))
								.addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_formPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_formPane.createSequentialGroup().addGap(4).addComponent(lblPassword2))
								.addComponent(txtPassword2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_formPane.createSequentialGroup().addGap(4).addComponent(lblEmail))
								.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(12)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)));
		contentPane.setLayout(gl_formPane);
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
					boolean result = RegisterView.this.registerBUS.action_RegisterSubmit();
					if (result == true) {
						ViewBUS.gotoLoginView();
						JOptionPane.showMessageDialog(RegisterView.this.contentPane, "Your account has been created.",
								"Information", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(RegisterView.this.contentPane, "Unknown register error", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(RegisterView.this.contentPane, exception.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
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

}
