package GUI;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import bus.LeaderboardBUS;
import bus.ViewBUS;
import dto.PlayerDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Run.GameMain;
import Socket.GameClient;

import java.awt.event.MouseAdapter;

public class LeaderboardView {

	private LeaderboardBUS leaderboardBUS;

	private JPanel contentPane = new JPanel();
	private final JButton btnNavBack = new JButton("<< Back");
	private final JLabel lblLeaderboard = new JLabel("Leaderboard");
	private final JLabel lblUsername = new JLabel("Username");
	private final JTextField txtSearch = new JTextField();
	private final JButton btnSearch = new JButton("Search");
	private final JButton btnReset = new JButton("Reset");
	private JButton btnPreviousPage = new JButton("<");
	private JButton btnNextPage = new JButton(">");
	private final JScrollPane scrollPane = new JScrollPane();
	private JTable table = new JTable();
	private JLabel lblCurrentPage;
	private int currentPage = 1;

	private DefaultTableModel leaderboardTableModel;

	public LeaderboardView(LeaderboardBUS leaderboardBUS) {
		// Listen for changes in the text
		this.leaderboardBUS = leaderboardBUS;

		loginViewSetup();
		customizeComponents();
		bindListeners();
		initViewBinder();
		getLeaderboardAll();
	}

	public void loginViewSetup() {

		contentPane.setBackground(Color.decode("#35455d"));
		contentPane.setSize(1024, 768);
		btnNavBack.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblLeaderboard.setForeground(Color.WHITE);
		lblLeaderboard.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtSearch.setEnabled(false);
		txtSearch.setFont(new Font("Tahoma", Font.ITALIC, 17));
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));

		leaderboardTableModel = new DefaultTableModel(
				new String[] { "Rank", "Player", "Ranking Point", "Win Rate", "Matches" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		table = new JTable(leaderboardTableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
		;
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(1).setPreferredWidth(130);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < 5; i++) {
			if (i == 1)
				continue;
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		table.setRowSelectionAllowed(false);
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);

		scrollPane.setViewportView(table);

		lblCurrentPage = new JLabel("Page 1 of 5");
		lblCurrentPage.setForeground(Color.WHITE);
		lblCurrentPage.setFont(new Font("Tahoma", Font.BOLD, 15));

		btnPreviousPage.setEnabled(false);
		btnPreviousPage.setFont(new Font("Tahoma", Font.BOLD, 16));

		

		btnNextPage.setFont(new Font("Tahoma", Font.BOLD, 16));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(btnNavBack))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(287)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup().addGap(124)
												.addComponent(lblLeaderboard))
										.addGroup(gl_contentPane.createSequentialGroup().addGap(101)
												.addComponent(lblCurrentPage, GroupLayout.PREFERRED_SIZE, 107,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnPreviousPage, GroupLayout.PREFERRED_SIZE, 60,
														GroupLayout.PREFERRED_SIZE)
												.addGap(18).addComponent(btnNextPage, GroupLayout.PREFERRED_SIZE, 60,
														GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(271)
								.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
								.addGap(17)
								.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
								.addGap(5)
								.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addGap(5).addComponent(btnReset))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(237).addComponent(scrollPane,
								GroupLayout.PREFERRED_SIZE, 550, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(237, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap().addComponent(btnNavBack).addGap(120)
				.addComponent(lblLeaderboard).addGap(50)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(1).addComponent(txtSearch,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnSearch).addComponent(btnReset)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(5).addComponent(lblUsername)))
				.addGap(74).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
				.addGap(40)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNextPage, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPreviousPage).addComponent(lblCurrentPage))
				.addContainerGap(159, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);

		// textField.setFont(new Font("Tahoma", Font.BOLD, 17));

	}

	// Functions

	private void customizeComponents() {
	}

	private void bindListeners() {

		txtSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 17));
				txtSearch.setEnabled(true);
				txtSearch.requestFocus(true);
			}
		});

		PlayerDTO player = ((GameClient) GameMain.client).getClientPlayer().getPlayer();
		txtSearch.setText(player.getUsername());

		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = txtSearch.getText();

				try {
					leaderboardTableModel.setRowCount(0);
					LeaderboardView.this.leaderboardBUS.action_GetLeaderboardUser(username);

					// model.addRow(new Object[]{"DN Korina", "DN Madrid", "DN Romania"});
				} catch (Exception exception) {
					String message = exception.getMessage();
					if (message.contains("Connection refused")) {
						message = "Something went wrong.";
					}

					JOptionPane.showMessageDialog(LeaderboardView.this.contentPane, message, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtSearch.setText(player.getUsername());
				txtSearch.setFont(new Font("Tahoma", Font.ITALIC, 17));
				txtSearch.setEnabled(false);

				getLeaderboardAll();

			}
		});

		btnNavBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewBUS.gotoGameRoomView();
				btnReset.doClick();
			}
		});

		btnNextPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentPage++;
				try {

					LeaderboardView.this.leaderboardBUS.action_GetLeaderboardAll(currentPage);
					btnPreviousPage.setEnabled(true);

				} catch (Exception exception) {
					JOptionPane.showMessageDialog(LeaderboardView.this.contentPane, exception.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnPreviousPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentPage--;
				try {

					LeaderboardView.this.leaderboardBUS.action_GetLeaderboardAll(currentPage);
					if(currentPage==1) {
						btnPreviousPage.setEnabled(false);
					}

				} catch (Exception exception) {
					JOptionPane.showMessageDialog(LeaderboardView.this.contentPane, exception.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}

	public void getLeaderboardAll() {
		try {
			currentPage = 1;
			LeaderboardView.this.leaderboardBUS.action_GetLeaderboardAll(currentPage);
			btnPreviousPage.setEnabled(false);

		} catch (Exception exception) {
			JOptionPane.showMessageDialog(LeaderboardView.this.contentPane, exception.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void initViewBinder() {
		this.leaderboardBUS.viewBinder.txtSearch = txtSearch;
		this.leaderboardBUS.viewBinder.leaderboardTableModel = leaderboardTableModel;
		this.leaderboardBUS.viewBinder.btnNextPage = btnNextPage;
		this.leaderboardBUS.viewBinder.lblCurrentPage = lblCurrentPage;
	}

	// Properties

	public JPanel getContentPane() {
		return contentPane;
	}

	public LeaderboardBUS getLeaderboardBUS() {
		return leaderboardBUS;
	}
}
