package Main;

import static Main.Main.BookModel;
import static Main.Main.ReaderModel;
import static Main.Main.book_table;
import static Main.Main.conn;
import static Main.Main.panel_books;
import static Main.Main.panel_readers;
import static Main.Main.readers_table;
import static Main.Main.typeModel;
import static Main.TypeBooks.typeTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import javax.swing.JOptionPane;

public class PopupMenuListener implements ActionListener {

    private final String button;

    PopupMenuListener(String text) {
        this.button = text;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (button) {
            case "Книга":
                switch (e.getActionCommand()) {
                    case "change":
                        if (typeModel.getItems().length == 0) {
                            JOptionPane.showMessageDialog(panel_readers, "Типы книг отсутствуют. Добавьте типы книг и попробуйте еще раз.");
                        } else {
                            ChangeBook add = new ChangeBook(panel_books, conn, BookModel.getItem(book_table.getSelectedRow()));
                            add.setVisible(true);
                        }
                        break;
                    case "remove":
                        BookModel.Remove(book_table.getSelectedRow());
                        break;
                    default:
                        System.out.println("Неизвесная команда");
                }
                break;
            case "Читатель":
                switch (e.getActionCommand()) {
                    case "change":
                        ChangeReader add = new ChangeReader(panel_readers, conn, ReaderModel.getItem(readers_table.getSelectedRow()));
                        add.setVisible(true);
                        break;
                    case "remove":
                        ReaderModel.Remove(new Reader(readers_table.getValueAt(readers_table.getSelectedRow(), 1).toString(),
                                Integer.parseInt(readers_table.getValueAt(readers_table.getSelectedRow(), 0).toString()),
                                readers_table.getValueAt(readers_table.getSelectedRow(), 2).toString(),
                                Date.valueOf(readers_table.getValueAt(readers_table.getSelectedRow(), 3).toString())));
                        break;
                    default:
                        System.out.println("Неизвесная команда");
                }
                break;

            case "Тип":
                switch (e.getActionCommand()) {
                    case "change":
                        AddType add = new AddType(typeModel.getItem(typeTable.getSelectedRow()));
                        add.setVisible(true);
                        break;
                    case "remove":
                        typeModel.Remove(typeTable.getSelectedRow());
                        break;
                    default:
                        System.out.println("Неизвесная команда");
                }
                break;
            default:
                System.out.println("Неизвесная команда");
        }

    }

}
