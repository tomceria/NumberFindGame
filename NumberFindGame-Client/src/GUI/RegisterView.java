package GUI;

import bus.RegisterBUS;
import bus.ViewBUS;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

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
	private JComboBox comboBox = new JComboBox();
	private JDatePickerImpl datePicker;
	// Others
	private JButton btnSubmit = new JButton("Submit");
	private JButton btnNavBack = new JButton("<< Back");
	private JButton btnReset = new JButton("Reset");
	private RegisterBUS registerBUS;

	public RegisterView(RegisterBUS registerBUS) {
		this.registerBUS = registerBUS;

		registerViewSetup();
		bindListeners();
		initViewBinder();
	}

	public void test() {
		JFrame frame = new JFrame("Number Find Game");

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

		// JDatePanelImpl datePanel = new JDatePanelImpl(model, null);
		datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
		SpringLayout springLayout = (SpringLayout) datePicker.getLayout();

		// frame.getContentPane().add(datePicker);

		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 116, SpringLayout.SOUTH,
				datePicker.getJFormattedTextField());
		springLayout.putConstraint(SpringLayout.WEST, panel, 92, SpringLayout.WEST, datePicker);
		// datePicker.add(panel);
		panel.add(datePicker);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
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

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 15));

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtEmail.setColumns(10);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setForeground(Color.WHITE);
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 15));

		txtLastName = new JTextField();
		txtLastName.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtLastName.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));

		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JLabel lblGender = new JLabel("Gender");
		lblGender.setForeground(Color.WHITE);
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 15));

		// String[] gender = { "Male", "Female", "Other" };
		DefaultComboBoxModel gender = new DefaultComboBoxModel();
		gender.addElement("Male");
		gender.addElement("Female");
		gender.addElement("Other");
		comboBox = new JComboBox(gender);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JLabel lblPassword2 = new JLabel("Confirm Password");
		lblPassword2.setForeground(Color.WHITE);
		lblPassword2.setFont(new Font("Tahoma", Font.BOLD, 15));

		txtPassword2 = new JPasswordField();
		txtPassword2.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setForeground(Color.WHITE);
		lblBirthday.setFont(new Font("Tahoma", Font.BOLD, 15));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(3);

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
		datePicker.getJFormattedTextField().setFont(new Font("Tahoma", Font.PLAIN, 17));
		panel.add(datePicker);

		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 16));

		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUsername.setText("");
				txtPassword.setText("");
				txtPassword2.setText("");
				txtFirstName.setText("");
				txtLastName.setText("");
				txtEmail.setText("");
				datePicker.getJFormattedTextField().setText("");
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
						.createSequentialGroup().addGap(159)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(192).addComponent(lblTitle,
										GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 76,
												GroupLayout.PREFERRED_SIZE)
										.addGap(68)
										.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 185,
												GroupLayout.PREFERRED_SIZE)
										.addGap(65).addComponent(lblFirstName).addGap(17).addComponent(txtFirstName,
												GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblEmail)
														.addGap(102)
														.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 185,
																GroupLayout.PREFERRED_SIZE)
														.addGap(65).addComponent(lblLastName,
																GroupLayout.PREFERRED_SIZE, 81,
																GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 74,
																GroupLayout.PREFERRED_SIZE)
														.addGap(70)
														.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 185,
																GroupLayout.PREFERRED_SIZE)
														.addGap(65).addComponent(lblGender, GroupLayout.PREFERRED_SIZE,
																54, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(lblPassword2, GroupLayout.PREFERRED_SIZE, 139,
																GroupLayout.PREFERRED_SIZE)
														.addGap(5)
														.addComponent(txtPassword2, GroupLayout.PREFERRED_SIZE, 185,
																GroupLayout.PREFERRED_SIZE)
														.addGap(65).addComponent(lblBirthday)))
										.addGap(19)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, 212,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 212,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(panel, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_contentPane.createSequentialGroup().addGap(228)
										.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 91,
												GroupLayout.PREFERRED_SIZE)
										.addGap(65).addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 95,
												GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(btnNavBack)))
				.addContainerGap(159, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap().addComponent(btnNavBack).addGap(222)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addGroup(gl_contentPane
								.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(4).addComponent(lblUsername))
								.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(4).addComponent(lblFirstName))
								.addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
								.addGap(5)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_contentPane.createSequentialGroup().addGap(4).addComponent(lblEmail))
										.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createSequentialGroup().addGap(4)
												.addComponent(lblLastName))
										.addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addGroup(Alignment.TRAILING,
								gl_contentPane.createSequentialGroup().addComponent(lblTitle).addGap(124)))
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
						.createSequentialGroup().addGap(5)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(5).addComponent(lblPassword))
								.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(5).addComponent(lblGender)))
						.addGap(7)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(6).addComponent(lblPassword2))
								.addComponent(txtPassword2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(6).addComponent(lblBirthday)))
						.addGap(45)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(btnSubmit)
								.addComponent(btnReset)))
						.addGroup(gl_contentPane.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(panel,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
				.addGap(356)));
		contentPane.setLayout(gl_contentPane);

	}

	private void bindListeners() {
		btnNavBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtUsername.setText("");
				txtPassword.setText("");
				txtPassword2.setText("");
				txtFirstName.setText("");
				txtLastName.setText("");
				txtEmail.setText("");
				datePicker.getJFormattedTextField().setText("");
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
		RegisterView.this.registerBUS.viewBinder.comboBox = comboBox;
		RegisterView.this.registerBUS.viewBinder.datePicker = datePicker;
	}

	public JPanel getContentPane() {
		return contentPane;
	}

}
