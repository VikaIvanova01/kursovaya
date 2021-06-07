package Main;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import javax.swing.JTable;


public class PopupListener extends MouseAdapter {

    private final JPopupMenu popup;
    private final JTable table;

    PopupListener(JPopupMenu popupMenu, JTable jtable) {
        popup = popupMenu;
        table = jtable;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (table.getSelectedRow() != -1) {
            maybeShowPopup(e);
        }
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }
  
}


