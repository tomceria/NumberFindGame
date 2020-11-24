package GUI;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import bus.LeaderboardBUS;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;

public class LeaderboardView {

	private LeaderboardBUS leaderboardBUS;

	private JPanel contentPane = new JPanel();
	private final JButton btnNavBack = new JButton("<< Back");
	private final JLabel lblLeaderboard = new JLabel("Leaderboard");
	private final JLabel lblUsername = new JLabel("Username");
	private final JTextField txtSearch = new JTextField();
	private final JButton btnSearch = new JButton("Search");
	private final JButton btnReset = new JButton("Reset");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable table = new JTable();

	public LeaderboardView(LeaderboardBUS leaderboardBUS) {
		// Listen for changes in the text
		this.leaderboardBUS = leaderboardBUS;

		loginViewSetup();
		customizeComponents();
		bindListeners();
		initViewBinder();
	}

	public void loginViewSetup() {

		contentPane.setBackground(Color.decode("#35455d"));
		contentPane.setSize(1024, 768);
		btnNavBack.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblLeaderboard.setForeground(Color.WHITE);
		lblLeaderboard.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtSearch.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReset.setEnabled(false);
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"Rank", "Player", "Ranking Point", "Win Rate", "Total Matches"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.setRowSelectionAllowed(false);
		table.setFont(new Font("Tahoma", Font.BOLD, 15));
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		
		scrollPane.setViewportView(table);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnNavBack))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(287)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(124)
									.addComponent(lblLeaderboard))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
									.addGap(17)
									.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
									.addGap(5)
									.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
									.addGap(5)
									.addComponent(btnReset))
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 451, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(286, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNavBack)
					.addGap(155)
					.addComponent(lblLeaderboard)
					.addGap(50)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(5)
							.addComponent(lblUsername))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(1)
							.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnSearch)
						.addComponent(btnReset))
					.addGap(74)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(193, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
			//textField.setFont(new Font("Tahoma", Font.BOLD, 17));
			
	}

	// Functions

	private void customizeComponents() {
	}

	private void bindListeners() {
		txtSearch.getDocument().addDocumentListener((DocumentListener) new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  btnReset.setEnabled(true);
			  }
			  public void removeUpdate(DocumentEvent e) {
			    
			  }
			  public void insertUpdate(DocumentEvent e) {
				  btnReset.setEnabled(true);
			  }
			});
//		btnSubmit.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				try {
//					LeaderboardView.this.leaderboardBUS.action_LoginSubmit();
//				} catch (Exception exception) {
//					String message = exception.getMessage();
//					if (message.contains("Connection refused")) {
//						message = "Game server is currently unavailable.";
//					}
//
//					JOptionPane.showMessageDialog(LoginView.this.contentPane, message, "Error",
//							JOptionPane.ERROR_MESSAGE);
//				}
//			}
//		});
		
		btnReset.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			txtSearch.setText("");
		}
	});

	}

	private void initViewBinder() {
		this.leaderboardBUS.viewBinder.txtSearch = txtSearch;
	}

	// Properties

	public JPanel getContentPane() {
		return contentPane;
	}
}
