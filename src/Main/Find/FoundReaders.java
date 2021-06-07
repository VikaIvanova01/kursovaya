package Main.Find;


import static Main.Main.panel_readers;
import Main.Reader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

public class FoundReaders extends JDialog {

    private JPanel panel;
    private final JLabel label_up = new JLabel("Найденные читатели"); 
    private JTextArea result;
    private final JButton ok = new JButton("ок");
    private final ArrayList<Reader> readers;

    public FoundReaders(ArrayList<Reader> readers) {
        super((JFrame) SwingUtilities.getRoot(panel_readers), "Результат поиска", Dialog.ModalityType.DOCUMENT_MODAL);
        this.readers = readers;
        this.initComponents();
        this.pack(); //для того, чтобы окно создавалось под размер всех панелей
        this.setResizable(false);//нельзя раздвигать окно
        this.setSize(350, 300);
        this.setLocationRelativeTo(null);//чтобы окно создавалось по середине экрана
    }

    private void initComponents() {
        //Основная панель
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(3, 0, 3, 0);

        //Заголовок 
        label_up.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label_up, gbc);

        //Результат
        result = new JTextArea(10, 27);
        result.setLineWrap(true);
        result.setWrapStyleWord(true);
        result.setEditable(false);
        JScrollPane scroll = new JScrollPane(result,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        
        String info = "";
        for(Reader reader: readers){
            info = "";
            info += "Номер: "+reader.getId()+"\n";
            info += "ФИО: "+reader.getFIO()+"\n";
            info += "Номер телефона: "+reader.getModile()+"\n";
            info += "Дата рождения: "+reader.getBirthday().toString()+"\n";
            info += "------------------------------"+"\n\n";
            
            result.setText(result.getText()+info);
        }
        panel.add(scroll, gbc);

        //Кнопка "ОК"
        ok.setHorizontalAlignment(JLabel.CENTER);
        ok.addActionListener(this::close);
        ok.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(ok, gbc);

        getContentPane().add(panel);//сделали основную панель основной
    }

    private void close(ActionEvent e) {
        dispose();
    }

}
