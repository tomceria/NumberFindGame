package GUI;

import bus.UpdateInfoBUS;
import bus.ViewBUS;
import dto.PlayerDTO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import Run.GameMain;
import Socket.GameClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.LayoutStyle.ComponentPlacement;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import util.DateUtil;

public class UpdateInfoView {

	/*
	 * LoginView.form's Components
	 */
	private JPanel contentPane = new JPanel();
	// ViewBinder's components
	private JTextField txtUsername;
	private JPasswordField txtNewPassword;
	private JPasswordField txtNewPassword2;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtEmail;
	private JComboBox comboBox = new JComboBox();
	private JTextField datePicker;
	// Others
	private JButton btnUpdate = new JButton("Update");
	private JButton btnNavBack = new JButton("<< Back");
	private JButton btnChangePassword = new JButton("Submit");

	private UpdateInfoBUS updateInfoBUS;
	private JPasswordField txtOldPassword;

	public UpdateInfoView(UpdateInfoBUS updateInfoBUS) {
		this.updateInfoBUS = updateInfoBUS;

		updateInfoViewSetup();
		bindListeners();
		initViewBinder();
	}

	public void updateInfoViewSetup() {
		contentPane.setSize(1024, 768);
		contentPane.setBackground(Color.decode("#35455d"));
		btnNavBack.setFont(new Font("Tahoma", Font.BOLD, 16));

		JLabel lblTitle = new JLabel("Update account");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBackground(new Color(255, 255, 255));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 32));

		JLabel lblAccountInfo = new JLabel("Update Account Info");
		lblAccountInfo.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblAccountInfo.setForeground(Color.WHITE);

		JLabel lblChangePassword = new JLabel("Change Password");
		lblChangePassword.setForeground(Color.WHITE);
		lblChangePassword.setFont(new Font("Tahoma", Font.PLAIN, 27));
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 15));

		txtUsername = new JTextField();
		txtUsername.setEditable(false);
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtUsername.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setBackground(Color.WHITE);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.WHITE);

		JLabel lblOldPassword = new JLabel("Old Password");
		lblOldPassword.setForeground(Color.WHITE);
		lblOldPassword.setFont(new Font("Tahoma", Font.BOLD, 15));

		txtOldPassword = new JPasswordField();
		txtOldPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setForeground(Color.WHITE);
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 15));

		txtFirstName = new JTextField();
		txtFirstName.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtFirstName.setColumns(10);

		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setForeground(Color.WHITE);
		lblNewPassword.setFont(new Font("Tahoma", Font.BOLD, 15));

		txtNewPassword = new JPasswordField();
		txtNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setForeground(Color.WHITE);
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 15));

		txtLastName = new JTextField();
		txtLastName.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtLastName.setColumns(10);

		JLabel lblNewPassword2 = new JLabel("Confirm Password");
		lblNewPassword2.setForeground(Color.WHITE);
		lblNewPassword2.setFont(new Font("Tahoma", Font.BOLD, 15));

		txtNewPassword2 = new JPasswordField();
		txtNewPassword2.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 15));

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtEmail.setColumns(10);

		JLabel lblGender = new JLabel("Gender");
		lblGender.setHorizontalAlignment(SwingConstants.LEFT);
		lblGender.setForeground(Color.WHITE);
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 15));

		DefaultComboBoxModel gender = new DefaultComboBoxModel();
		gender.addElement("Male");
		gender.addElement("Female");
		gender.addElement("Other");
		comboBox = new JComboBox(gender);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 17));

		btnChangePassword.setFont(new Font("Tahoma", Font.BOLD, 16));

		JLabel lblBirthday = new JLabel("Birthdate");
		lblBirthday.setForeground(Color.WHITE);
		lblBirthday.setFont(new Font("Tahoma", Font.BOLD, 15));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(3);

		datePicker = new JTextField();
		datePicker.setFont(new Font("Tahoma", Font.PLAIN, 17));
		datePicker.setColumns(10);
		panel.add(datePicker);

		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
										.createSequentialGroup().addGap(
												91)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 76,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(lblFirstName)
												.addComponent(lblLastName, GroupLayout.PREFERRED_SIZE, 81,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(lblEmail)
												.addComponent(lblGender, GroupLayout.PREFERRED_SIZE, 54,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(lblBirthday))
										.addGap(61)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 212,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, 212,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, 212,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 212,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 212,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(panel, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_contentPane
														.createSequentialGroup().addGap(121).addComponent(btnUpdate)))
										.addGap(64)
										.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(62)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(lblOldPassword, GroupLayout.PREFERRED_SIZE, 105,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(lblNewPassword, GroupLayout.PREFERRED_SIZE, 113,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(lblNewPassword2, GroupLayout.PREFERRED_SIZE, 139,
														GroupLayout.PREFERRED_SIZE))
										.addGap(5)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(txtOldPassword, GroupLayout.PREFERRED_SIZE, 212,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(txtNewPassword, GroupLayout.PREFERRED_SIZE, 212,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(txtNewPassword2, GroupLayout.PREFERRED_SIZE, 212,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_contentPane.createSequentialGroup().addGap(54)
														.addComponent(btnChangePassword, GroupLayout.PREFERRED_SIZE, 91,
																GroupLayout.PREFERRED_SIZE))))
										.addGroup(gl_contentPane.createSequentialGroup().addGap(385).addComponent(
												lblTitle, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
												.addComponent(btnNavBack)))
								.addContainerGap(93, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING,
								gl_contentPane.createSequentialGroup().addGap(149)
										.addComponent(lblAccountInfo, GroupLayout.PREFERRED_SIZE, 247,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 252, Short.MAX_VALUE)
										.addComponent(lblChangePassword, GroupLayout.PREFERRED_SIZE, 212,
												GroupLayout.PREFERRED_SIZE)
										.addGap(164)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap().addComponent(btnNavBack).addGap(148).addComponent(lblTitle)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
						.createSequentialGroup().addGap(58)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblChangePassword)
								.addComponent(lblAccountInfo))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(34)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup().addGap(8)
														.addComponent(lblUsername).addGap(9).addComponent(lblFirstName)
														.addGap(13).addComponent(lblLastName).addGap(13)
														.addComponent(lblEmail).addGap(14).addComponent(lblGender)
														.addGap(14).addComponent(lblBirthday))
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addGap(5)
														.addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addGap(5)
														.addComponent(txtLastName, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addGap(5)
														.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addGap(5)
														.addComponent(comboBox, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addGap(5)
														.addComponent(panel, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addGap(35).addComponent(btnUpdate))))
								.addGroup(gl_contentPane.createSequentialGroup().addGap(32).addGroup(gl_contentPane
										.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup().addGap(8)
												.addComponent(lblOldPassword).addGap(9).addComponent(lblNewPassword)
												.addGap(13).addComponent(lblNewPassword2))
										.addGroup(
												gl_contentPane.createSequentialGroup()
														.addComponent(txtOldPassword, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addGap(5)
														.addComponent(txtNewPassword, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addGap(5)
														.addComponent(txtNewPassword2, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addGap(37).addComponent(btnChangePassword))))))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(96).addComponent(separator,
								GroupLayout.PREFERRED_SIZE, 313, GroupLayout.PREFERRED_SIZE)))));
		contentPane.setLayout(gl_contentPane);
	}

	private void bindListeners() {
		PlayerDTO player = ((GameClient) GameMain.client).getClientPlayer().getPlayer();
		txtUsername.setText(player.getUsername());
		txtFirstName.setText(player.getFirstName());
		txtLastName.setText(player.getLastName());
		txtEmail.setText(player.getEmail());
		comboBox.setSelectedItem(player.getGender());
		datePicker.setText(DateUtil.parseDateToString(player.getBirthday()));

		btnNavBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewBUS.gotoGameRoomView();

				txtUsername.setText(player.getUsername());
				txtFirstName.setText(player.getFirstName());
				txtLastName.setText(player.getLastName());
				txtEmail.setText(player.getEmail());
				txtOldPassword.setText("");
				txtNewPassword.setText("");
				txtNewPassword2.setText("");
				datePicker.setText("");
			}
		});
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					UpdateInfoView.this.updateInfoBUS.action_UpdateSubmit();

				} catch (Exception exception) {
					JOptionPane.showMessageDialog(UpdateInfoView.this.contentPane, exception.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnChangePassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UpdateInfoView.this.updateInfoBUS.action_ChangePassword();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(UpdateInfoView.this.contentPane, exception.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private void initViewBinder() {
		UpdateInfoView.this.updateInfoBUS.viewBinder.txtUsername = txtUsername;
		UpdateInfoView.this.updateInfoBUS.viewBinder.txtOldPassword = txtOldPassword;
		UpdateInfoView.this.updateInfoBUS.viewBinder.txtNewPassword = txtNewPassword;
		UpdateInfoView.this.updateInfoBUS.viewBinder.txtNewPassword2 = txtNewPassword2;
		UpdateInfoView.this.updateInfoBUS.viewBinder.txtFirstName = txtFirstName;
		UpdateInfoView.this.updateInfoBUS.viewBinder.txtLastName = txtLastName;
		UpdateInfoView.this.updateInfoBUS.viewBinder.txtEmail = txtEmail;
		UpdateInfoView.this.updateInfoBUS.viewBinder.comboBox = comboBox;
		UpdateInfoView.this.updateInfoBUS.viewBinder.datePicker = datePicker;
	}

	public JPanel getContentPane() {
		return contentPane;
	}
}
