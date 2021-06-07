package Main;

import static Main.Main.typeModel;
import static Main.TypeBooks.typeTable;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class AddType extends JDialog {

    private final JLabel label_type = new JLabel("Введите название типа");
    private final JLabel label_count = new JLabel("Введите количество дней на возврат");
    private final JTextField enter_type = new JTextField();
    private final JSpinner enter_count = new JSpinner();
    private final JButton ok = new JButton("Добавить");
    private JPanel panel;
    private String attrib = "";
    private TypeBook type;

    public AddType() {
        super((JFrame) SwingUtilities.getRoot(typeTable), "Добавить тип книги", Dialog.ModalityType.DOCUMENT_MODAL);
        this.initComponents();
        this.attrib = "Создать";
        this.pack(); //для того, чтобы окно создавалось под размер всех панелей
        this.setResizable(false);//нельзя раздвигать окно
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);//чтобы окно создавалось по середине экрана
    }

    public AddType(TypeBook type) {
        super((JFrame) SwingUtilities.getRoot(typeTable), "Добавить тип книги", Dialog.ModalityType.DOCUMENT_MODAL);
        this.initComponents();
        this.type = type;
        this.attrib = "Изменить";
        this.ok.setText("Обновить");
        enter_type.setText(type.getName());
        enter_count.setValue(type.getCount());
        this.pack(); //для того, чтобы окно создавалось под размер всех панелей
        this.setResizable(false);//нельзя раздвигать окно
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);//чтобы окно создавалось по середине экрана
    }

    private void initComponents() {
        //Основная панель
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(3, 0, 3, 0);

        //Заголовок поля ввода типа
        label_type.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label_type, gbc);

        //Поле ввода типа
        enter_type.setMinimumSize(new Dimension(200, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(enter_type, gbc);

        //Заголовок поля ввода количества дней
        label_count.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(label_count, gbc);

        //Поле ввода количества дней
        enter_count.setMinimumSize(new Dimension(200, 30));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(enter_count, gbc);

        ok.setPreferredSize(new Dimension(300, 30));
        ok.setHorizontalAlignment(JLabel.CENTER);
        ok.addActionListener(this::addType);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(ok, gbc);

        getContentPane().add(panel);//сделали панель основной
    }

    private void addType(ActionEvent e) {
        if ("Создать".equals(attrib)) {
            if (enter_type.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "Название типа пустое");

            } else if ((int) enter_count.getValue() == 0) {
                JOptionPane.showMessageDialog(this, "Количество дней очень мало");
            } else {
                typeModel.Add(new TypeBook(-1, enter_type.getText(), (int) enter_count.getValue()));
                JOptionPane.showMessageDialog(this, "Тип добавлен");
                dispose();
            }
        }else{
            if (enter_type.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "Название типа пустое");

            } else if ((int) enter_count.getValue() == 0) {
                JOptionPane.showMessageDialog(this, "Количество дней очень мало");
            } else {
                this.type.setName(enter_type.getText());
                this.type.setCount((int)enter_count.getValue());
                typeModel.Update(this.type);

                JOptionPane.showMessageDialog(this, "Тип обновлен");
                dispose();
            }
        }

    }

}
