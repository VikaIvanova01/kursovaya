package Main.Find;

import Main.Reader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;

public class FindReader extends JDialog {

    private JPanel panel;
    private final JLabel label_fio = new JLabel("Введите ФИО читателя (если не знаете полностью, то можно указать что-то одно)");
    private final JTextField enter_fio = new JTextField();
    private final JButton ok = new JButton("ок");
    private final Connection con;
    private final ArrayList<Reader> readers = new ArrayList<>();

    public FindReader(JPanel mainFrame, Connection conn) {
        super((JFrame) SwingUtilities.getRoot(mainFrame), "Найти читателя", Dialog.ModalityType.DOCUMENT_MODAL);
        this.con = conn;
        this.panelsParameters();
        this.pack(); //для того, чтобы окно создавалось под размер всех панелей
        this.setResizable(false);//нельзя раздвигать окно
        this.setSize(500, 150);
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
        label_fio.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label_fio, gbc);

        //Поле ввода названия
        enter_fio.setPreferredSize(new Dimension(400, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(enter_fio, gbc);

        //Кнопка "ОК"
        ok.setHorizontalAlignment(JLabel.CENTER);
        ok.addActionListener(this::findReader);
        ok.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(ok, gbc);

        getContentPane().add(panel);//сделали основную панель основной
    }

    private void findReader(ActionEvent e) {

        if (enter_fio.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Поле для поиска пустое");
        } else {

            try (PreparedStatement statement = this.con.prepareStatement(
                    "SELECT * FROM readers WHERE lower(fio) LIKE lower(?)")) {
                statement.setString(1, '%' + enter_fio.getText() + '%');
                statement.execute();
                ResultSet rs = statement.getResultSet();
                while (rs.next()) {
                    readers.add(new Reader(rs.getString(2), rs.getInt(1), rs.getString(3), rs.getDate(4)));
                }
                if (readers.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Такого читателя нет.");
                } else {
                    FoundReaders found = new FoundReaders(readers);
                    found.setVisible(true);
                    dispose();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
