package GUI.Components;

import Run.GameMain;
import Socket.GameClient;
import dto.Game_Client;
import dto.MatchPlayer;
import dto.MatchPlayer_Client;
import dto.PlayerDTO;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GameMatchPlayerCellRenderer extends JPanel implements ListCellRenderer<MatchPlayer>  {
    private JPanel paneAvatar;
    private JLabel lblNameInitial;
    private JLabel lblScore;

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public GameMatchPlayerCellRenderer() {
        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(-1));
        this.setMinimumSize(new Dimension(75, 50));
        this.setPreferredSize(new Dimension(75, 50));
        paneAvatar = new JPanel();
        paneAvatar.setLayout(new GridBagLayout());
        paneAvatar.setAlignmentX(0.5f);
        paneAvatar.setAlignmentY(0.5f);
        paneAvatar.setMinimumSize(new Dimension(50, 50));
        paneAvatar.setPreferredSize(new Dimension(50, 50));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(paneAvatar, gbc);
        lblNameInitial = new JLabel();
        lblNameInitial.setText("HL");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        paneAvatar.add(lblNameInitial, gbc);
        lblScore = new JLabel();
        lblScore.setForeground(new Color(-16777216));
        lblScore.setHorizontalAlignment(0);
        lblScore.setText("1");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(lblScore, gbc);
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

        paneAvatar.setBackground(matchPlayer.getUiColor());

        lblNameInitial.setText(String.format(
                "%c%c",
                player.getFirstName().toUpperCase().charAt(0),
                player.getLastName().toUpperCase().charAt(0)
        ));
        lblNameInitial.setFont(new Font("SansSerif", Font.PLAIN, 21));

        lblScore.setText(value.getScore() + "");
        lblScore.setFont(new Font("SansSerif", Font.BOLD, 12));

        if (matchPlayer.getPlayer().getUsername().equals(
                ((Game_Client)
                        ((GameClient) GameMain.client).getGameRoom().getGame()
                ).getClientPlayer().getPlayer().getUsername()
        )) {
            Border clientPlayerIndicator = BorderFactory.createLineBorder(Color.BLACK, 3);
            paneAvatar.setBorder(clientPlayerIndicator);
        } else {
            paneAvatar.setBorder(null);
        }

        return this;
    }
}
