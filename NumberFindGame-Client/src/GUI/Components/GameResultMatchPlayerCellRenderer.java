package GUI.Components;

import Common.OrdinalNumber;
import Run.GameMain;
import Socket.GameClient;
import dto.Game_Client;
import dto.MatchPlayer;
import dto.MatchPlayer_Client;
import dto.PlayerDTO;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GameResultMatchPlayerCellRenderer extends JPanel implements ListCellRenderer<MatchPlayer> {
    private JPanel panePlacing;
    private JPanel panePlayer;
    private JPanel paneScore;
    private JLabel lblPlacing;
    private JLabel lblInitials;
    private JLabel lblName;
    private JLabel lblScore;

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public GameResultMatchPlayerCellRenderer() {
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(300, 50));
        panePlacing = new JPanel();
        panePlacing.setLayout(new GridBagLayout());
        panePlacing.setMinimumSize(new Dimension(50, 50));
        panePlacing.setPreferredSize(new Dimension(50, 50));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(panePlacing, gbc);
        lblPlacing = new JLabel();
//        Font lblPlacingFont = this.$$$getFont$$$(null, -1, 16, lblPlacing.getFont());
//        if (lblPlacingFont != null) lblPlacing.setFont(lblPlacingFont);
        lblPlacing.setText("0th");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panePlacing.add(lblPlacing, gbc);
        panePlayer = new JPanel();
        panePlayer.setLayout(new GridBagLayout());
        panePlayer.setMinimumSize(new Dimension(185, 16));
        panePlayer.setPreferredSize(new Dimension(185, 16));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(panePlayer, gbc);
        lblName = new JLabel();
        lblName.setText("usernameusernam");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panePlayer.add(lblName, gbc);
        lblInitials = new JLabel();
//        Font lblInitialsFont = this.$$$getFont$$$(null, -1, 21, lblInitials.getFont());
//        if (lblInitialsFont != null) lblInitials.setFont(lblInitialsFont);
        lblInitials.setHorizontalAlignment(0);
        lblInitials.setMinimumSize(new Dimension(56, 16));
        lblInitials.setPreferredSize(new Dimension(56, 16));
        lblInitials.setText("AB");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panePlayer.add(lblInitials, gbc);
        paneScore = new JPanel();
        paneScore.setLayout(new GridBagLayout());
        paneScore.setBackground(new Color(-1));
        paneScore.setMinimumSize(new Dimension(65, 0));
        paneScore.setPreferredSize(new Dimension(65, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(paneScore, gbc);
        lblScore = new JLabel();
//        Font lblScoreFont = this.$$$getFont$$$(null, -1, 24, lblScore.getFont());
//        if (lblScoreFont != null) lblScore.setFont(lblScoreFont);
        lblScore.setForeground(new Color(-16777216));
        lblScore.setText("10");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        paneScore.add(lblScore, gbc);
    }

    /**
     * Return a component that has been configured to display the specified
     * value. That component's <code>paint</code> method is then called to
     * "render" the cell.  If it is necessary to compute the dimensions
     * of a list because the list cells do not have a fixed size, this method
     * is called to generate a component on which <code>getPreferredSize</code>
     * can be invoked.
     *
     * @param list         The JList we're painting.
     * @param value        The value returned by list.getModel().getElementAt(index).
     * @param index        The cells index.
     * @param isSelected   True if the specified cell was selected.
     * @param cellHasFocus True if the specified cell has the focus.
     * @return A component whose paint() method will render the specified value.
     * @see JList
     * @see ListSelectionModel
     * @see ListModel
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends MatchPlayer> list, MatchPlayer value, int index, boolean isSelected, boolean cellHasFocus) {
        MatchPlayer_Client matchPlayer = (MatchPlayer_Client) value;
        PlayerDTO player = value.getPlayer();

        panePlayer.setBackground(matchPlayer.getUiColor());
        lblPlacing.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblInitials.setFont(new Font("SansSerif", Font.PLAIN, 21));
        lblScore.setFont(new Font("SansSerif", Font.PLAIN, 24));

        String[] placingColor = {"#F4CB0D", "#C4C5C7", "#D9895E"};
        if (matchPlayer.getPlacing() >= 1 && matchPlayer.getPlacing() <= 3) {
            panePlacing.setBackground(
                    Color.decode(placingColor[matchPlayer.getPlacing() - 1])
            );
        } else {
            panePlacing.setBackground(Color.WHITE);
        }

        lblPlacing.setText(OrdinalNumber.generate(matchPlayer.getPlacing()));
        lblInitials.setText(String.format(
                "%c%c",
                player.getFirstName().toUpperCase().charAt(0),
                player.getLastName().toUpperCase().charAt(0)
        ));
        lblName.setText(matchPlayer.getPlayer().getUsername());
        lblScore.setText(matchPlayer.getScore() + "");

        if (matchPlayer.getPlayer().equals(
                ((Game_Client)
                        ((GameClient) GameMain.client).getGameRoom().getGame()
                ).getClientPlayer().getPlayer()
        )) {
            Border clientPlayerIndicator = BorderFactory.createLineBorder(Color.BLACK, 3);
            panePlayer.setBorder(clientPlayerIndicator);
        } else {
            panePlayer.setBorder(null);
        }

        return this;
    }
}
