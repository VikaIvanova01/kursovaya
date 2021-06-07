package Main;

import static Main.Main.BookModel;
import static Main.Main.typeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.*;

public class ChangeBook extends JDialog {

    private JPanel panel;
    private final JLabel label_name = new JLabel("Введите название книги");
    private final JLabel label_author = new JLabel("Введите автора книги");
    private final JLabel label_type = new JLabel("Выберите тип книги");
    private final JLabel label_countEkz = new JLabel("Введите количество экземпляров");
    private final JSpinner enter_countEkz = new JSpinner();
    private final JTextField enter_name = new JTextField();
    private final JTextField enter_author = new JTextField();
    private final JLabel label_typeDay = new JLabel();
    private final JComboBox enter_type;
    private final JButton ok = new JButton("ок");

    private String attrib = "";
    private int id_book = 0;
    private final ArrayList<TypeBook> type = new ArrayList();
    private final ArrayList<String> type_string = new ArrayList();
    Book book;

    public ChangeBook(JPanel mainFrame, Connection conn) {
        super((JFrame) SwingUtilities.getRoot(mainFrame), "Добавить книгу", Dialog.ModalityType.DOCUMENT_MODAL);
        this.attrib = "Добавить";
        this.getTypes();
        this.enter_type = new JComboBox(this.type_string.toArray(new String[0]));
        this.panelsParameters();
        this.changeType();
        this.pack(); //для того, чтобы окно создавалось под размер всех панелей
        this.setResizable(false);//нельзя раздвигать окно
        this.setSize(330, 350);
        this.setLocationRelativeTo(null);//чтобы окно создавалось по середине экрана
    }

    public ChangeBook(JPanel mainFrame, Connection conn, Book book) {
        super((JFrame) SwingUtilities.getRoot(mainFrame), "Изменить книгу", Dialog.ModalityType.DOCUMENT_MODAL);
        this.attrib = "Изменить";
        this.getTypes();
        this.enter_type = new JComboBox(this.type_string.toArray(new String[0]));
        this.panelsParameters();
        this.book = book;
        enter_name.setText(book.getName());
        enter_author.setText(book.getAuthor());
        enter_countEkz.setValue(book.getCount());
        this.enter_type.setSelectedIndex(book.getType() - 1);
        this.changeType();
        this.id_book = book.getId();
        this.pack(); //для того, чтобы окно создавалось под размер всех панелей
        this.setResizable(false);//нельзя раздвигать окно
        this.setSize(330, 350);
        this.setLocationRelativeTo(null);//чтобы окно создавалось по середине экрана

    }

    private void getTypes() {
        for (TypeBook tp : typeModel.getItems()) {
            this.type.add(tp);
            this.type_string.add(tp.getName());
        }
    }

    private void changeType() {
        label_typeDay.setText("Дней на возврат: " + this.type.get(this.enter_type.getSelectedIndex()).getCount());
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
        enter_name.setPreferredSize(new Dimension(280, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(enter_name, gbc);

        //Заголовок поля автора
        label_author.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(label_author, gbc);

        //Поле ввода автора
        enter_author.setPreferredSize(new Dimension(280, 30));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(enter_author, gbc);

        label_countEkz.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(label_countEkz, gbc);

        //Поле ввода количества экземпляров
        enter_countEkz.setPreferredSize(new Dimension(280, 30));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(enter_countEkz, gbc);

        label_type.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(label_type, gbc);

        //Поле выбора типа
        enter_type.setPreferredSize(new Dimension(280, 30));
        enter_type.addItemListener((ItemEvent e) -> {
            this.changeType();
        });
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(enter_type, gbc);

        //Поле вывода количества дней
        label_typeDay.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(label_typeDay, gbc);

        //Кнопка "ОК"
        ok.setHorizontalAlignment(JLabel.CENTER);
        ok.addActionListener(this::addBook);
        ok.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(ok, gbc);

        getContentPane().add(panel);//сделали основную панель основной
    }

    private void addBook(ActionEvent e) {

        if (enter_name.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Навзание не заполнено");
        } else if (enter_author.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Автор не указан");
        } else if ((int) enter_countEkz.getValue() == 0) {
            JOptionPane.showMessageDialog(this, "Количество экземпляров слишком мало");
        } else if ("Добавить".equals(this.attrib)) {
            try {
                BookModel.Add(new Book(enter_name.getText(), enter_author.getText(), -1, (int) enter_countEkz.getValue(), (int) enter_countEkz.getValue(), this.type.get(this.enter_type.getSelectedIndex()).getTypeBookId()));
                JOptionPane.showMessageDialog(null, "Книга добавлена!");
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Книга не была добавлена!");
            }
        } else if ("Изменить".equals(this.attrib)) {
            try {

                int countA = (int) enter_countEkz.getValue() - (this.book.getCount() - this.book.getCountAvail());

                if (countA < 0) {
                    JOptionPane.showMessageDialog(this, "Количество экземпляров не может быть меньше чем выдано");
                } else {

                    BookModel.Update(new Book(enter_name.getText(), enter_author.getText(), this.id_book, countA, (int) enter_countEkz.getValue(), this.type.get(this.enter_type.getSelectedIndex()).getTypeBookId()));
                    JOptionPane.showMessageDialog(null, "Книга изменена!");
                    dispose();
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Книга не была изменена!");
            }
        }

    }

}
