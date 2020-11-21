package GUI;

import bus.RegisterBUS;
import bus.UpdateInfoBUS;
import bus.ViewBUS;
import dto.GameRoom;
import dto.MatchPlayer;
import dto.PlayerDTO;
import util.BCrypt;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import Run.GameMain;
import Socket.GameClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		GridBagLayout gbl_formPane = new GridBagLayout();
		gbl_formPane.columnWidths = new int[] { 135, 139, 195, 55, 83, 200, 0 };
		gbl_formPane.rowHeights = new int[] { 211, 39, 49, 45, 0, 27, 27, 27, 0, 0, 0, 0, 120, 0 };
		gbl_formPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_formPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_formPane);

		// JButton btnNavBack = new JButton("<< Back");
		GridBagConstraints gbc_btnNavBack = new GridBagConstraints();
		gbc_btnNavBack.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNavBack.insets = new Insets(10, 10, 5, 5);
		gbc_btnNavBack.gridx = 0;
		gbc_btnNavBack.gridy = 0;
		contentPane.add(btnNavBack, gbc_btnNavBack);
		btnNavBack.setFont(new Font("Tahoma", Font.BOLD, 16));

		JLabel lblTitle = new JLabel("Update account");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBackground(new Color(255, 255, 255));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 32));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.NORTH;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridwidth = 5;
		gbc_lblTitle.gridx = 1;
		gbc_lblTitle.gridy = 1;
		contentPane.add(lblTitle, gbc_lblTitle);

		JLabel lblAccountInfo = new JLabel("Update Account Info");
		lblAccountInfo.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblAccountInfo.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblAccountInfo = new GridBagConstraints();
		gbc_lblAccountInfo.gridwidth = 2;
		gbc_lblAccountInfo.insets = new Insets(0, 0, 5, 5);
		gbc_lblAccountInfo.gridx = 1;
		gbc_lblAccountInfo.gridy = 3;
		contentPane.add(lblAccountInfo, gbc_lblAccountInfo);

		JLabel lblChangePassword = new JLabel("Change Password");
		lblChangePassword.setForeground(Color.WHITE);
		lblChangePassword.setFont(new Font("Tahoma", Font.PLAIN, 27));
		GridBagConstraints gbc_lblChangePassword = new GridBagConstraints();
		gbc_lblChangePassword.gridwidth = 2;
		gbc_lblChangePassword.insets = new Insets(0, 0, 5, 0);
		gbc_lblChangePassword.gridx = 4;
		gbc_lblChangePassword.gridy = 3;
		contentPane.add(lblChangePassword, gbc_lblChangePassword);
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 5;
		contentPane.add(lblUsername, gbc_lblUsername);

		txtUsername = new JTextField();
		txtUsername.setEditable(false);
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtUsername.setColumns(10);
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.anchor = GridBagConstraints.NORTH;
		gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsername.insets = new Insets(0, 0, 5, 5);
		gbc_txtUsername.gridx = 2;
		gbc_txtUsername.gridy = 5;
		contentPane.add(txtUsername, gbc_txtUsername);
		
		JLabel lblOldPassword = new JLabel("Old Password");
		lblOldPassword.setForeground(Color.WHITE);
		lblOldPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_lblOldPassword = new GridBagConstraints();
		gbc_lblOldPassword.anchor = GridBagConstraints.WEST;
		gbc_lblOldPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblOldPassword.gridx = 4;
		gbc_lblOldPassword.gridy = 5;
		contentPane.add(lblOldPassword, gbc_lblOldPassword);
		
		txtOldPassword = new JPasswordField();
		txtOldPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_txtOldPassword = new GridBagConstraints();
		gbc_txtOldPassword.insets = new Insets(0, 0, 5, 0);
		gbc_txtOldPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtOldPassword.gridx = 5;
		gbc_txtOldPassword.gridy = 5;
		contentPane.add(txtOldPassword, gbc_txtOldPassword);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setForeground(Color.WHITE);
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.anchor = GridBagConstraints.WEST;
		gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstName.gridx = 1;
		gbc_lblFirstName.gridy = 6;
		contentPane.add(lblFirstName, gbc_lblFirstName);

		txtFirstName = new JTextField();
		txtFirstName.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtFirstName.setColumns(10);
		GridBagConstraints gbc_txtFirstName = new GridBagConstraints();
		gbc_txtFirstName.anchor = GridBagConstraints.NORTH;
		gbc_txtFirstName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_txtFirstName.gridx = 2;
		gbc_txtFirstName.gridy = 6;
		contentPane.add(txtFirstName, gbc_txtFirstName);

		JSeparator separator = new JSeparator();
		separator.setBackground(Color.WHITE);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.WHITE);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridheight = 8;
		gbc_separator.fill = GridBagConstraints.VERTICAL;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 3;
		gbc_separator.gridy = 4;
		contentPane.add(separator, gbc_separator);
		
				JLabel lblNewPassword = new JLabel("New Password");
				lblNewPassword.setForeground(Color.WHITE);
				lblNewPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
				GridBagConstraints gbc_lblNewPassword = new GridBagConstraints();
				gbc_lblNewPassword.anchor = GridBagConstraints.WEST;
				gbc_lblNewPassword.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewPassword.gridx = 4;
				gbc_lblNewPassword.gridy = 6;
				contentPane.add(lblNewPassword, gbc_lblNewPassword);
		
				txtNewPassword = new JPasswordField();
				txtNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
				GridBagConstraints gbc_txtNewPassword = new GridBagConstraints();
				gbc_txtNewPassword.anchor = GridBagConstraints.NORTH;
				gbc_txtNewPassword.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtNewPassword.insets = new Insets(0, 0, 5, 0);
				gbc_txtNewPassword.gridx = 5;
				gbc_txtNewPassword.gridy = 6;
				contentPane.add(txtNewPassword, gbc_txtNewPassword);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setForeground(Color.WHITE);
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.anchor = GridBagConstraints.WEST;
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.gridx = 1;
		gbc_lblLastName.gridy = 7;
		contentPane.add(lblLastName, gbc_lblLastName);

		txtLastName = new JTextField();
		txtLastName.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtLastName.setColumns(10);
		GridBagConstraints gbc_txtLastName = new GridBagConstraints();
		gbc_txtLastName.anchor = GridBagConstraints.NORTH;
		gbc_txtLastName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLastName.insets = new Insets(0, 0, 5, 5);
		gbc_txtLastName.gridx = 2;
		gbc_txtLastName.gridy = 7;
		contentPane.add(txtLastName, gbc_txtLastName);
		
				JLabel lblNewPassword2 = new JLabel("Confirm Password");
				lblNewPassword2.setForeground(Color.WHITE);
				lblNewPassword2.setFont(new Font("Tahoma", Font.BOLD, 15));
				GridBagConstraints gbc_lblNewPassword2 = new GridBagConstraints();
				gbc_lblNewPassword2.anchor = GridBagConstraints.WEST;
				gbc_lblNewPassword2.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewPassword2.gridx = 4;
				gbc_lblNewPassword2.gridy = 7;
				contentPane.add(lblNewPassword2, gbc_lblNewPassword2);
		
				txtNewPassword2 = new JPasswordField();
				txtNewPassword2.setFont(new Font("Tahoma", Font.PLAIN, 17));
				GridBagConstraints gbc_txtNewPassword2 = new GridBagConstraints();
				gbc_txtNewPassword2.anchor = GridBagConstraints.NORTH;
				gbc_txtNewPassword2.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtNewPassword2.insets = new Insets(0, 0, 5, 0);
				gbc_txtNewPassword2.gridx = 5;
				gbc_txtNewPassword2.gridy = 7;
				contentPane.add(txtNewPassword2, gbc_txtNewPassword2);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 8;
		contentPane.add(lblEmail, gbc_lblEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtEmail.setColumns(10);
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.anchor = GridBagConstraints.NORTH;
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmail.gridx = 2;
		gbc_txtEmail.gridy = 8;
		contentPane.add(txtEmail, gbc_txtEmail);
		
				btnChangePassword.setFont(new Font("Tahoma", Font.BOLD, 16));
				GridBagConstraints gbc_btnChangePassword = new GridBagConstraints();
				gbc_btnChangePassword.insets = new Insets(0, 0, 5, 0);
				gbc_btnChangePassword.gridx = 5;
				gbc_btnChangePassword.gridy = 9;
				contentPane.add(btnChangePassword, gbc_btnChangePassword);

		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.anchor = GridBagConstraints.EAST;
		gbc_btnUpdate.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpdate.gridx = 2;
		gbc_btnUpdate.gridy = 10;
		contentPane.add(btnUpdate, gbc_btnUpdate);

		contentPane.setLayout(gbl_formPane);
	}

	private void bindListeners() {
		PlayerDTO player = ((GameClient) GameMain.client).getClientPlayer().getPlayer();
		txtUsername.setText(player.getUsername());
		txtFirstName.setText(player.getFirstName());
		txtLastName.setText(player.getLastName());
		txtEmail.setText(player.getEmail());

		btnNavBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewBUS.gotoGameRoomView();
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
	}

	public JPanel getContentPane() {
		return contentPane;
	}

}
