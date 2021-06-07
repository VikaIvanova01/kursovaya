package Main;


import static Main.Main.ReaderModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.Date;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.text.MaskFormatter;



public class ChangeReader extends JDialog{
    
    private JPanel panel;
    //Фамилия
    private final JLabel label_fam = new JLabel("Введите фамилию читателя");
    private final JTextField enter_fam = new JTextField();
    
    //Имя
    private final JLabel label_name = new JLabel("Введите имя читателя");
    private final JTextField enter_name = new JTextField();
    
    //Отчетсво
    private final JLabel label_otch = new JLabel("Введите отчество читателя");
    private final JTextField enter_otch = new JTextField();
    
    //Мобильный телефон
    private final JLabel label_mobile = new JLabel("Введите номер телефона читателя");
    private JFormattedTextField enter_mobile = new JFormattedTextField();
    
    //Дата рождения
    private final JLabel label_date = new JLabel("Введите дату рождения читателя (yyyy-MM-dd)");
    
    private JFormattedTextField enter_date = new JFormattedTextField();

    private final JButton ok = new JButton("ок");
    private String attrib = "";
    private int id_reader = 0;


    public ChangeReader(JPanel mainFrame, Connection conn){
        super((JFrame) SwingUtilities.getRoot(mainFrame), "Добавить читателя", Dialog.ModalityType.DOCUMENT_MODAL);
        try{
            enter_date = new JFormattedTextField(new MaskFormatter("####-##-##"));
            enter_mobile = new JFormattedTextField(new MaskFormatter("###########"));
            this.attrib = "Добавить";
            this.panelsParameters();
            this.pack(); //для того, чтобы окно создавалось под размер всех панелей
            this.setResizable(false);//нельзя раздвигать окно
            this.setSize(330, 370);
            this.setLocationRelativeTo(null);//чтобы окно создавалось по середине экрана
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Ошибка, окно будет закрыто");
            dispose();
        }
    }
    
    public ChangeReader(JPanel mainFrame, Connection conn, Reader reader){
        super((JFrame) SwingUtilities.getRoot(mainFrame), "Изменить читателя", Dialog.ModalityType.DOCUMENT_MODAL);
        try{
            enter_date = new JFormattedTextField(new MaskFormatter("####-##-##"));
            enter_mobile = new JFormattedTextField(new MaskFormatter("###########")); 
            this.attrib = "Изменить";
            this.panelsParameters();
            enter_fam.setText(reader.getFIO().split(" ")[0]);
            enter_name.setText(reader.getFIO().split(" ")[1]);
            enter_otch.setText(reader.getFIO().split(" ")[2]);
            enter_mobile.setText(reader.getModile());
            enter_date.setText(reader.getBirthday().toString());
            this.id_reader = reader.getId();
            this.pack(); //для того, чтобы окно создавалось под размер всех панелей
            this.setResizable(false);//нельзя раздвигать окно
            this.setSize(330, 370);
            this.setLocationRelativeTo(null);//чтобы окно создавалось по середине экрана
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Ошибка, окно будет закрыто");
            dispose();
        }
    }
    
  
    private void panelsParameters() //параметры панелей
    {
        //Основная панель
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(3,0,3,0);

        
        //Заголовок поля названия
        label_fam.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label_fam, gbc);
        
        
        //Поле ввода названия
        enter_fam.setPreferredSize(new Dimension (300,30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(enter_fam, gbc);
        
        //Заголовок поля названия
        label_name.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(label_name, gbc);
        
        
        //Поле ввода названия
        enter_name.setPreferredSize(new Dimension (300,30));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(enter_name, gbc);
        
        
        //Заголовок поля названия
        label_otch.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(label_otch, gbc);
        
        
        //Поле ввода названия
        enter_otch.setPreferredSize(new Dimension (300,30));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(enter_otch, gbc);
        
        //Заголовок поля названия
        label_mobile.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(label_mobile, gbc);
        
        
        //Поле ввода названия
        enter_mobile.setPreferredSize(new Dimension (300,30));
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(enter_mobile, gbc);
        
        
        //Заголовок поля названия
        label_date.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(label_date, gbc);
        
        
        //Поле ввода названия
        enter_date.setPreferredSize(new Dimension (300,30));
        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(enter_date, gbc);


        //Кнопка "ОК"
        ok.setHorizontalAlignment(JLabel.CENTER);
        ok.addActionListener(this::addReader);
        ok.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 0;
        gbc.gridy = 10;
        panel.add(ok, gbc);

        getContentPane().add(panel);//сделали основную панель основной
    }
    
    
    private void addReader(ActionEvent e) {
        
        if (enter_fam.getText().length() == 0){
            JOptionPane.showMessageDialog(null, "Фамилия не заполнена");
        }else if (enter_name.getText().length() == 0){
            JOptionPane.showMessageDialog(null, "Имя не заполнено");
        }else if (enter_otch.getText().length() == 0){
            JOptionPane.showMessageDialog(null, "Отчество не заполнено");
        }else if (!Character.isDigit(enter_mobile.getText().charAt(2))){
            JOptionPane.showMessageDialog(null, "Номер телефона не заполнен или заполнен неправильно");
        }else if (!Character.isDigit(enter_date.getText().charAt(2))){
            JOptionPane.showMessageDialog(null, "Дата рождения не заполнена");
        }else
        if ("Добавить".equals(this.attrib)){
            try{
                ReaderModel.Add(new Reader(enter_fam.getText()+' '+enter_name.getText()+' '+enter_otch.getText(), -1,enter_mobile.getText(), Date.valueOf(enter_date.getText())));
                JOptionPane.showMessageDialog(null, "Читатель добавлен!");
                dispose();
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Читатель не был добавлен!");
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Введеная дата не корректна!");
            }
        }else if ("Изменить".equals(this.attrib)){
            try{
                ReaderModel.Update(new Reader(enter_fam.getText()+' '+enter_name.getText()+' '+enter_otch.getText(), this.id_reader, enter_mobile.getText(), Date.valueOf(enter_date.getText())));
                JOptionPane.showMessageDialog(null, "Читатель изменен!");
                dispose();
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Читатель не был изменен!");
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Введеная дата не корректна!");
            }
        }
        
        

    }
    
}
