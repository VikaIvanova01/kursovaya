package Main.WorkWIthBooks;

import Main.Book;
import static Main.Main.BookModel;
import static Main.Main.typeModel;
import Main.Reader;
import static Main.SeeDebetors.GetBookModel;
import static Main.SeeDebetors.panel_debetors;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DistrBook extends JDialog {

    private JPanel panel;
    private final JLabel label_fio = new JLabel("Выберите читателя");
    private final JComboBox enter_fio;
    private final JLabel label_book = new JLabel("Выберите книгу");
    private final JComboBox enter_book;
    private final JLabel label_count = new JLabel("Количество дней на возврат: ");
    private final JButton ok = new JButton("Выдать");
    private final Connection con;
    private final ArrayList<Book> books = new ArrayList<>();
    private final ArrayList<Reader> readers = new ArrayList<>();
    private final ArrayList<String> books_string = new ArrayList<>();
    private final ArrayList<String> readers_string = new ArrayList<>();
    private int countDay = 0;

    public DistrBook(Connection conn) {
        super((JFrame) SwingUtilities.getRoot(panel_debetors), "Выдача книг", Dialog.ModalityType.APPLICATION_MODAL);
        this.con = conn;
        this.getFIOAndBooks();
        enter_fio = new JComboBox(readers_string.toArray(new String[0]));
        enter_book = new JComboBox(books_string.toArray(new String[0]));
        changeCount();

        this.panelsParameters();
        this.pack(); //для того, чтобы окно создавалось под размер всех панелей
        this.setResizable(false);//нельзя раздвигать окно
        this.setSize(330, 260);
        this.setLocationRelativeTo(null);//чтобы окно создавалось по середине экрана
    }
    
    private void changeCount(){
        countDay = typeModel.getItemById(books.get(enter_book.getSelectedIndex()).getType()).getCount();
        label_count.setText("Количество дней на возврат: "+countDay);
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
        label_fio.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label_fio, gbc);

        //Поле ввода названия
        enter_fio.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(enter_fio, gbc);

        //Заголовок поля книги
        label_book.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(label_book, gbc);

        //Поле выбора книги
        enter_book.setPreferredSize(new Dimension(300, 30));
        enter_book.addItemListener((ItemEvent e) -> {
            this.changeCount();
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(enter_book, gbc);

        //Заголовок поля вбора дня
        label_count.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(label_count, gbc);

        //Кнопка "ОК"
        ok.setHorizontalAlignment(JLabel.CENTER);
        ok.addActionListener(this::distrBook);
        ok.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(ok, gbc);

        getContentPane().add(panel);//сделали основную панель основной
    }

    private void getFIOAndBooks() {
        try (PreparedStatement statement = this.con.prepareStatement(
                "SELECT * FROM books")) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                books.add(new Book(rs.getString(2), rs.getString(3), rs.getInt(1), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
                books_string.add(rs.getString(2));
            }
            if (books.isEmpty()) {
                books_string.add("Книги отсутствуют");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try (PreparedStatement statement = this.con.prepareStatement(
                "SELECT * FROM readers")) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                readers.add(new Reader(rs.getString(2), rs.getInt(1), rs.getString(3), rs.getDate(4)));
                readers_string.add(rs.getString(2));
            }
            if (readers.isEmpty()) {
                readers_string.add("Читатели отсутствуют");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void distrBook(ActionEvent e) {

        Calendar cal = Calendar.getInstance();
        Date dat = cal.getTime();
        String format = "YYYY-MM-dd";
        DateFormat dateFormat = new SimpleDateFormat(format);
        String formDate = dateFormat.format(dat);

        try (PreparedStatement statement0 = this.con.prepareStatement(
                "SELECT * from books WHERE id_book=?")) {
            statement0.setInt(1, books.get(enter_book.getSelectedIndex()).getId());
            statement0.execute();
            ResultSet rs = statement0.getResultSet();
            rs.next();
            if (rs.getInt(4) > 0) {

                try (PreparedStatement statement = this.con.prepareStatement(
                        "INSERT INTO getBook(`id_reader`, `id_book`, `countDay`, `startDate`) VALUES(?,?,?,?)")) {
                    statement.setInt(1, readers.get(enter_fio.getSelectedIndex()).getId());
                    statement.setInt(2, books.get(enter_book.getSelectedIndex()).getId());
                    statement.setInt(3, countDay);
                    statement.setString(4, formDate);
                    statement.execute();

                    try (PreparedStatement statement2 = this.con.prepareStatement(
                            "UPDATE `books` SET countAvail=countAvail-1 WHERE id_book=?")) {
                        statement2.setInt(1, books.get(enter_book.getSelectedIndex()).getId());
                        statement2.execute();
                        GetBookModel.GetAll();
                        JOptionPane.showMessageDialog(null, "Книга выдана");
                        dispose();
                    }
                }
                BookModel.Refresh();
            } else {
                JOptionPane.showMessageDialog(null, "Все экземпляры книги выданы");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DistrBook.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
