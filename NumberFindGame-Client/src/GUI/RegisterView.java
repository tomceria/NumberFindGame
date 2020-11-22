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
		contentPane.setSize(1024, 768);
		contentPane.setBackground(Color.decode("#35455d"));
						
								btnNavBack.setFont(new Font("Tahoma", Font.BOLD, 16));
						
								JLabel lblTitle = new JLabel("Create new account");
								lblTitle.setForeground(Color.WHITE);
								lblTitle.setFont(new Font("Tahoma", Font.BOLD, 32));
						JLabel lblUsername = new JLabel("Username");
						lblUsername.setForeground(Color.WHITE);
						lblUsername.setFont(new Font("Tahoma", Font.BOLD, 15));
				
						txtUsername = new JTextField();
						txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
						txtUsername.setColumns(10);
						
								JLabel lblFirstName = new JLabel("First Name");
								lblFirstName.setForeground(Color.WHITE);
								lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 15));
								
										txtFirstName = new JTextField();
										txtFirstName.setFont(new Font("Tahoma", Font.PLAIN, 17));
										txtFirstName.setColumns(10);
								
										JLabel lblPassword = new JLabel("Password");
										lblPassword.setForeground(Color.WHITE);
										lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
						
								txtPassword = new JPasswordField();
								txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
				
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
				
						btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 16));
						
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
								btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));
								GroupLayout gl_contentPane = new GroupLayout(contentPane);
								gl_contentPane.setHorizontalGroup(
									gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGap(161)
													.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
														.addGroup(gl_contentPane.createSequentialGroup()
															.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
															.addGap(68)
															.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
															.addGap(60)
															.addComponent(lblFirstName)
															.addGap(25)
															.addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
														.addGroup(gl_contentPane.createSequentialGroup()
															.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
															.addGap(70)
															.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
															.addGap(60)
															.addComponent(lblLastName, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
															.addGap(27)
															.addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
														.addGroup(gl_contentPane.createSequentialGroup()
															.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
																.addGroup(gl_contentPane.createSequentialGroup()
																	.addComponent(lblPassword2, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
																	.addGap(5)
																	.addComponent(txtPassword2, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))
																.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
															.addGap(60)
															.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
																.addGroup(gl_contentPane.createSequentialGroup()
																	.addComponent(lblEmail)
																	.addGap(66)
																	.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))))
														.addGroup(gl_contentPane.createSequentialGroup()
															.addGap(190)
															.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE))))
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGap(4)
													.addComponent(btnNavBack)))
											.addContainerGap(161, Short.MAX_VALUE))
								);
								gl_contentPane.setVerticalGroup(
									gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(5)
											.addComponent(btnNavBack)
											.addGap(227)
											.addComponent(lblTitle)
											.addGap(54)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGap(4)
													.addComponent(lblUsername))
												.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGap(4)
													.addComponent(lblFirstName))
												.addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											.addGap(5)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGap(4)
													.addComponent(lblPassword))
												.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGap(4)
													.addComponent(lblLastName))
												.addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											.addGap(5)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGap(4)
													.addComponent(lblPassword2))
												.addComponent(txtPassword2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGap(4)
													.addComponent(lblEmail))
												.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											.addGap(33)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(btnReset)
												.addComponent(btnSubmit))
											.addGap(261))
								);
								contentPane.setLayout(gl_contentPane);
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
