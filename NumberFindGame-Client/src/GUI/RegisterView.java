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
	private JComboBox comboBox = new JComboBox();
//	private UtilDateModel model = new UtilDateModel();
//	private Properties p = new Properties();
	//private JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
	private JDatePickerImpl datePicker;// = new JDatePickerImpl(datePanel, new DateComponentFormatter());
	
	//private JDatePicker datePicker = new JDatePicker();

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
		
		//JDatePanelImpl datePanel = new JDatePanelImpl(model, null);
		datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
		SpringLayout springLayout = (SpringLayout) datePicker.getLayout();
		 
		//frame.getContentPane().add(datePicker);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 116, SpringLayout.SOUTH, datePicker.getJFormattedTextField());
		springLayout.putConstraint(SpringLayout.WEST, panel, 92, SpringLayout.WEST, datePicker);
		//datePicker.add(panel);
		panel.add(datePicker);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

	public void registerViewSetup() {
		contentPane.setSize(1024, 768);
		contentPane.setBackground(Color.decode("#35455d"));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 103, 54, 139, 190, 60, 100, 200, 0 };
		gbl_contentPane.rowHeights = new int[] { 29, 227, 39, 54, 27, 27, 27, 33, 29, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		btnNavBack.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_btnNavBack = new GridBagConstraints();
		gbc_btnNavBack.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNavBack.insets = new Insets(0, 0, 5, 5);
		gbc_btnNavBack.gridx = 0;
		gbc_btnNavBack.gridy = 0;
		contentPane.add(btnNavBack, gbc_btnNavBack);

		JLabel lblTitle = new JLabel("Create new account");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 32));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridwidth = 4;
		gbc_lblTitle.gridx = 3;
		gbc_lblTitle.gridy = 2;
		contentPane.add(lblTitle, gbc_lblTitle);
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 2;
		gbc_lblUsername.gridy = 4;
		contentPane.add(lblUsername, gbc_lblUsername);

		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtUsername.setColumns(10);
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.anchor = GridBagConstraints.NORTH;
		gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsername.insets = new Insets(0, 0, 5, 5);
		gbc_txtUsername.gridx = 3;
		gbc_txtUsername.gridy = 4;
		contentPane.add(txtUsername, gbc_txtUsername);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setForeground(Color.WHITE);
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.anchor = GridBagConstraints.WEST;
		gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstName.gridx = 5;
		gbc_lblFirstName.gridy = 4;
		contentPane.add(lblFirstName, gbc_lblFirstName);

		txtFirstName = new JTextField();
		txtFirstName.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtFirstName.setColumns(10);
		GridBagConstraints gbc_txtFirstName = new GridBagConstraints();
		gbc_txtFirstName.anchor = GridBagConstraints.NORTH;
		gbc_txtFirstName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFirstName.insets = new Insets(0, 0, 5, 0);
		gbc_txtFirstName.gridx = 6;
		gbc_txtFirstName.gridy = 4;
		contentPane.add(txtFirstName, gbc_txtFirstName);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 2;
		gbc_lblEmail.gridy = 5;
		contentPane.add(lblEmail, gbc_lblEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtEmail.setColumns(10);
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.anchor = GridBagConstraints.NORTH;
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmail.gridx = 3;
		gbc_txtEmail.gridy = 5;
		contentPane.add(txtEmail, gbc_txtEmail);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setForeground(Color.WHITE);
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.anchor = GridBagConstraints.WEST;
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.gridx = 5;
		gbc_lblLastName.gridy = 5;
		contentPane.add(lblLastName, gbc_lblLastName);

		txtLastName = new JTextField();
		txtLastName.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtLastName.setColumns(10);
		GridBagConstraints gbc_txtLastName = new GridBagConstraints();
		gbc_txtLastName.anchor = GridBagConstraints.NORTH;
		gbc_txtLastName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLastName.insets = new Insets(0, 0, 5, 0);
		gbc_txtLastName.gridx = 6;
		gbc_txtLastName.gridy = 5;
		contentPane.add(txtLastName, gbc_txtLastName);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 2;
		gbc_lblPassword.gridy = 6;
		contentPane.add(lblPassword, gbc_lblPassword);

		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.anchor = GridBagConstraints.NORTH;
		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtPassword.gridx = 3;
		gbc_txtPassword.gridy = 6;
		contentPane.add(txtPassword, gbc_txtPassword);

		JLabel lblLastName_1 = new JLabel("Gender");
		lblLastName_1.setForeground(Color.WHITE);
		lblLastName_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_lblLastName_1 = new GridBagConstraints();
		gbc_lblLastName_1.anchor = GridBagConstraints.WEST;
		gbc_lblLastName_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName_1.gridx = 5;
		gbc_lblLastName_1.gridy = 6;
		contentPane.add(lblLastName_1, gbc_lblLastName_1);

		//String[] gender = { "Male", "Female", "Other" };
		DefaultComboBoxModel gender = new DefaultComboBoxModel();
		gender.addElement("Male");
		gender.addElement("Female");
		gender.addElement("Other");
		comboBox = new JComboBox(gender);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 6;
		gbc_comboBox.gridy = 6;
		contentPane.add(comboBox, gbc_comboBox);

		JLabel lblPassword2 = new JLabel("Confirm Password");
		lblPassword2.setForeground(Color.WHITE);
		lblPassword2.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_lblPassword2 = new GridBagConstraints();
		gbc_lblPassword2.anchor = GridBagConstraints.WEST;
		gbc_lblPassword2.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword2.gridx = 2;
		gbc_lblPassword2.gridy = 7;
		contentPane.add(lblPassword2, gbc_lblPassword2);

		txtPassword2 = new JPasswordField();
		txtPassword2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_txtPassword2 = new GridBagConstraints();
		gbc_txtPassword2.anchor = GridBagConstraints.NORTH;
		gbc_txtPassword2.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword2.insets = new Insets(0, 0, 5, 5);
		gbc_txtPassword2.gridx = 3;
		gbc_txtPassword2.gridy = 7;
		contentPane.add(txtPassword2, gbc_txtPassword2);

		JLabel lblLastName_2 = new JLabel("Birthdate");
		lblLastName_2.setForeground(Color.WHITE);
		lblLastName_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_lblLastName_2 = new GridBagConstraints();
		gbc_lblLastName_2.anchor = GridBagConstraints.WEST;
		gbc_lblLastName_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName_2.gridx = 5;
		gbc_lblLastName_2.gridy = 7;
		contentPane.add(lblLastName_2, gbc_lblLastName_2);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 6;
		gbc_panel.gridy = 7;
		contentPane.add(panel, gbc_panel);
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
		panel.add(datePicker);

		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnSubmit.insets = new Insets(0, 0, 0, 5);
		gbc_btnSubmit.gridx = 3;
		gbc_btnSubmit.gridy = 9;
		contentPane.add(btnSubmit, gbc_btnSubmit);

		JButton btnReset = new JButton("Reset");
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
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.anchor = GridBagConstraints.NORTH;
		gbc_btnReset.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnReset.insets = new Insets(0, 0, 0, 5);
		gbc_btnReset.gridx = 5;
		gbc_btnReset.gridy = 9;
		contentPane.add(btnReset, gbc_btnReset);
		
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
