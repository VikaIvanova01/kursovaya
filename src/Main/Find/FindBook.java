package Main.Find;

import Main.Book;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;

public class FindBook extends JDialog {

    private JPanel panel;
    private final JLabel label_name = new JLabel("Введите название книги");
    private final JTextField enter_name = new JTextField();
    private final JButton ok = new JButton("ок");
    private final Connection con;
    private final ArrayList<Book> books = new ArrayList<>();

    public FindBook(JPanel mainFrame, Connection conn) {
        super((JFrame) SwingUtilities.getRoot(mainFrame), "Найти книгу", Dialog.ModalityType.DOCUMENT_MODAL);
        this.con = conn;
        this.panelsParameters();
        this.pack(); //для того, чтобы окно создавалось под размер всех панелей
        this.setResizable(false);//нельзя раздвигать окно
        this.setSize(330, 150);
        this.setLocationRelativeTo(null);//чтобы окно создавалось по середине экрана
    }

    private void panelsParameters() //параметры панелей
    {
        //Основная панель
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(3, 0, 3, 0);

        //Заголовок поля названия
        label_name.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label_name, gbc);

        //Поле ввода названия
        enter_name.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(enter_name, gbc);

        //Кнопка "ОК"
        ok.setHorizontalAlignment(JLabel.CENTER);
        ok.addActionListener(this::findBook);
        ok.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(ok, gbc);

        getContentPane().add(panel);//сделали основную панель основной
    }

    private void findBook(ActionEvent e) {

        if (enter_name.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Поле для поиска пустое");
        } else {

            try (PreparedStatement statement = this.con.prepareStatement(
                    "SELECT * FROM books WHERE lower(name) LIKE lower(?)")) {
                statement.setString(1, '%' + enter_name.getText() + '%');
                statement.execute();
                ResultSet rs = statement.getResultSet();
                while (rs.next()) {
                    books.add(new Book(rs.getString(2), rs.getString(3), rs.getInt(1), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
                }
                if (books.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Такой книги нет.");
                } else {
                    FoundBooks found = new FoundBooks(books);
                    found.setVisible(true);
                    dispose();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
