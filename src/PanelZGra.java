import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by user_name on 19.06.2017.
 */
public class PanelZGra extends JPanel {

    public InfoPanel infoPanel;
    public Board board;
    public PanelZGra(ActionListener glowneMenu){

        board = new Board(glowneMenu);
        infoPanel = new InfoPanel(glowneMenu);

        setLayout(new BorderLayout());
        add(board, BorderLayout.CENTER);
        board.setFocusable(true);
        add(infoPanel, BorderLayout.NORTH);
        setVisible(true);
    }
}
