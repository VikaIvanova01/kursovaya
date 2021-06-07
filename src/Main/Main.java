package Main;

import Main.Find.FindAuth;
import Main.Find.FindBook;
import Main.Find.FindReader;
import Main.Info.InfoBook;
import Main.Info.InfoReader;
import Main.TableModels.TableBooksModel;
import Main.TableModels.TableReadersModel;
import Main.TableModels.TableTypeModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.*;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.Timer;




public class Main extends javax.swing.JFrame {
    
    
    public static final String DB_URL = "jdbc:h2:/"+ System.getProperty("user.dir");
  
    public static final String DB_DRIVER = "org.h2.Driver";
    public static Connection conn;
    private Statement state;
    public static TableTypeModel typeModel;
    public static TableBooksModel BookModel;
    public static TableReadersModel ReaderModel;

    private boolean singleClick  = true;
    private boolean singleClick2 = true;
    private int doubleClickDelay = 300;
    ActionListener MyListenBooks = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                if (!singleClick) {
                    doubleClickBooks(e);
                }
            }
        };
    
    ActionListener MyListenReaders = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer2.stop();
                if (!singleClick2) {
                    doubleClickReaders(e);
                }
            }
    };
    
    
    
    private Timer timer = new Timer(doubleClickDelay, MyListenBooks);
    private Timer timer2 = new Timer(doubleClickDelay, MyListenReaders);
    JPopupMenu popup = new JPopupMenu();
    JPopupMenu popup2 = new JPopupMenu();
    JPopupMenu popup3 = new JPopupMenu();

    public Main() {
        timer.setRepeats(false);

        try{
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_URL+"/db");//подключение бд
            state = conn.createStatement();
            state.execute(
                "CREATE TABLE IF NOT EXISTS `readers` (" +
                "	`id_reader` INT NOT NULL AUTO_INCREMENT," +
                "	`fio` TEXT NOT NULL," +
                "       `mobile` TEXT NOT NULL,"+
                "       `birthday` DATE NOT NULL,"+
                "	PRIMARY KEY (`id_reader`));" +
                "CREATE TABLE IF NOT EXISTS `books` (" +
                "	`id_book` INT NOT NULL AUTO_INCREMENT," +
                "	`name` TEXT NOT NULL," +
                "	`author` TEXT NOT NULL," +
                "       `countAvail` INT NOT NULL," +
                "       `count` INT NOT NULL, " +
                "       `id_typeBook` INT NOT NULL, " +       
                "	PRIMARY KEY (`id_book`));" +

                "CREATE TABLE IF NOT EXISTS `getBook` (" +
                "	`id_getBook` INT NOT NULL AUTO_INCREMENT," +
                "	`id_reader` INT NOT NULL," +
                "	`id_book` INT NOT NULL," +
                "	`startDate` DATE NOT NULL," +
                "	`endDate` DATE," +
                "       `countDay` INT NOT NULL," +
                "	PRIMARY KEY (`id_getBook`));" +
                        
                "CREATE TABLE IF NOT EXISTS `typeBook` (" +
                "	`id_typeBook` INT NOT NULL AUTO_INCREMENT," +
                "	`name` TEXT NOT NULL," +
                "	`count` INT NOT NULL," +
                "	PRIMARY KEY (`id_typeBook`));" +

                "ALTER TABLE `getBook` ADD CONSTRAINT `getBook_fk0` FOREIGN KEY (`id_reader`) REFERENCES `readers`(`id_reader`);" +
                "ALTER TABLE `books` ADD CONSTRAINT `typeBook_fk0` FOREIGN KEY (`id_typeBook`) REFERENCES `typeBook`(`id_typeBook`);" +
                "ALTER TABLE `getBook` ADD CONSTRAINT `getBook_fk1` FOREIGN KEY (`id_book`) REFERENCES `books`(`id_book`);");
            
            System.out.println("БД Подключена");
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }  
        catch(SQLException e){
            if (e.getMessage().contains("Ограничение \"GETBOOK_FK0\" уже существует")){
                System.out.println("БД Подключена");
            }else{
                e.printStackTrace();
            }
        }
        typeModel = new TableTypeModel(conn);
        BookModel = new TableBooksModel(conn);
        ReaderModel = new TableReadersModel(conn);

        
        try{
            ResultSet result = state.executeQuery("SELECT * FROM books");
            BookModel.GetAll(result);
            ResultSet res2 = state.executeQuery("SELECT * FROM readers");
            ReaderModel.GetAll(res2);
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Ошибка загрузки данных, приложение будет закрыто");
            System.exit(0);
        }
        initComponents();
        
        MouseListener popup1Listener = new PopupListener(popup, book_table);  
        MouseListener popup2Listener = new PopupListener(popup2, readers_table);  
        PopupMenuListener menuListen = new PopupMenuListener("Книга");
        PopupMenuListener menuListen2 = new PopupMenuListener("Читатель");
        
        JMenuItem change = new JMenuItem("Изменить");
        change.setActionCommand("change");
        change.addActionListener(menuListen);
        JMenuItem remove = new JMenuItem("Удалить");
        remove.setActionCommand("remove");
        remove.addActionListener(menuListen);
        popup.add(change);
        popup.add(remove);
        
        
        JMenuItem change2 = new JMenuItem("Изменить");
        change2.setActionCommand("change");
        change2.addActionListener(menuListen2);
        JMenuItem remove2 = new JMenuItem("Удалить");
        remove2.setActionCommand("remove");
        remove2.addActionListener(menuListen2);
        popup2.add(change2);
        popup2.add(remove2);
        

        book_table.addMouseListener(popup1Listener);
        readers_table.addMouseListener(popup2Listener);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        tabs = new javax.swing.JTabbedPane();
        panel_books = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        book_table = new javax.swing.JTable();
        addBook = new javax.swing.JButton();
        panel_readers = new javax.swing.JPanel();
        addReader = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        readers_table = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        debetorsMenu = new javax.swing.JMenu();
        debItems = new javax.swing.JMenuItem();
        findMenu = new javax.swing.JMenu();
        findReders = new javax.swing.JMenuItem();
        findBook = new javax.swing.JMenuItem();
        searchAuth = new javax.swing.JMenuItem();
        menuTypeBook = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Библиотека");
        setBackground(new java.awt.Color(204, 204, 204));
        setResizable(false);

        tabs.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        panel_books.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        book_table.setModel(BookModel);
        book_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                book_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(book_table);

        addBook.setText("Добавить книгу");
        addBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_booksLayout = new javax.swing.GroupLayout(panel_books);
        panel_books.setLayout(panel_booksLayout);
        panel_booksLayout.setHorizontalGroup(
            panel_booksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_booksLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panel_booksLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(addBook, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_booksLayout.setVerticalGroup(
            panel_booksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_booksLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addBook)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabs.addTab("Книги", panel_books);

        panel_readers.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(45, 45, 45), 1, true));

        addReader.setText("Добавить читателя");
        addReader.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addReaderActionPerformed(evt);
            }
        });

        readers_table.setModel(ReaderModel);
        readers_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                readers_tableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(readers_table);

        javax.swing.GroupLayout panel_readersLayout = new javax.swing.GroupLayout(panel_readers);
        panel_readers.setLayout(panel_readersLayout);
        panel_readersLayout.setHorizontalGroup(
            panel_readersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_readersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panel_readersLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(addReader)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_readersLayout.setVerticalGroup(
            panel_readersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_readersLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addReader)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabs.addTab("Читатели", panel_readers);

        debetorsMenu.setText("Выдачи");

        debItems.setText("Список всех выдач");
        debItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debItemsActionPerformed(evt);
            }
        });
        debetorsMenu.add(debItems);

        jMenuBar1.add(debetorsMenu);

        findMenu.setText("Найти");

        findReders.setLabel("Найти читателя");
        findReders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findRedersActionPerformed(evt);
            }
        });
        findMenu.add(findReders);

        findBook.setText("Найти книгу по названию");
        findBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findBookActionPerformed(evt);
            }
        });
        findMenu.add(findBook);

        searchAuth.setText("Найти книгу по автору");
        searchAuth.setToolTipText("");
        searchAuth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchAuthActionPerformed(evt);
            }
        });
        findMenu.add(searchAuth);

        jMenuBar1.add(findMenu);

        menuTypeBook.setText("Типы книг");

        jMenuItem3.setText("Показать типы книг");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        menuTypeBook.add(jMenuItem3);

        jMenuBar1.add(menuTypeBook);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addReaderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addReaderActionPerformed
        ChangeReader add = new ChangeReader(panel_readers, conn);
        add.setVisible(true);
    }//GEN-LAST:event_addReaderActionPerformed

    private void addBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBookActionPerformed
       if (typeModel.getItems().length == 0){
           JOptionPane.showMessageDialog(this, "Типы книг отсутствуют. Добавьте типы книг и попробуйте еще раз.");
       }else{
        ChangeBook add = new ChangeBook(panel_books, conn);
        add.setVisible(true);
       }

    }//GEN-LAST:event_addBookActionPerformed

    private void findBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findBookActionPerformed
        FindBook find = new FindBook(panel_readers, conn);
        find.setVisible(true);
        
    }//GEN-LAST:event_findBookActionPerformed

    private void findRedersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findRedersActionPerformed
        FindReader find = new FindReader(panel_readers, conn);
        find.setVisible(true);
    }//GEN-LAST:event_findRedersActionPerformed

    private void book_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_book_tableMouseClicked
        if (evt.getClickCount() == 1) {
            singleClick = true;
            timer.start();
        } else {
            singleClick = false;
        }
    }//GEN-LAST:event_book_tableMouseClicked

    private void debItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_debItemsActionPerformed
        SeeDebetors deb = new SeeDebetors( conn, this);
        deb.setVisible(true);
    }//GEN-LAST:event_debItemsActionPerformed

    private void readers_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_readers_tableMouseClicked
        if (evt.getClickCount() == 1) {
            singleClick2 = true;
            timer2.start();
        } else {
            singleClick2 = false;
        }
    }//GEN-LAST:event_readers_tableMouseClicked

    private void searchAuthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchAuthActionPerformed
        new FindAuth(panel_readers, conn).setVisible(true);
    }//GEN-LAST:event_searchAuthActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        new TypeBooks(this).setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed


    private void doubleClickBooks(ActionEvent e){
        
        InfoBook book = new InfoBook(conn, Integer.parseInt(book_table.getValueAt(book_table.getSelectedRow(), 0).toString()), this);
        book.setVisible(true);
    }
    
    private void doubleClickReaders(ActionEvent e){
        InfoReader reader = new InfoReader(conn, Integer.parseInt(readers_table.getValueAt(readers_table.getSelectedRow(), 0).toString()), this);
        reader.setVisible(true);
    }
    
    public static void main(String args[]) {
                new Main().setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBook;
    private javax.swing.JButton addReader;
    public static javax.swing.JTable book_table;
    private javax.swing.JMenuItem debItems;
    private javax.swing.JMenu debetorsMenu;
    private javax.swing.JMenuItem findBook;
    private javax.swing.JMenu findMenu;
    private javax.swing.JMenuItem findReders;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu menuTypeBook;
    public static javax.swing.JPanel panel_books;
    public static javax.swing.JPanel panel_readers;
    public static javax.swing.JTable readers_table;
    private javax.swing.JMenuItem searchAuth;
    private javax.swing.JTabbedPane tabs;
    // End of variables declaration//GEN-END:variables
}
