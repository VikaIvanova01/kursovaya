package Main;

import static Main.Main.BookModel;
import Main.TableModels.TableGetBookModel;
import Main.WorkWIthBooks.DistrBook;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SeeDebetors extends JFrame {

    public static TableGetBookModel GetBookModel;
    private final Connection conn;
    private final JFrame frame;

    public SeeDebetors(Connection conn, JFrame frame) {
        this.conn = conn;
        GetBookModel = new TableGetBookModel(conn);
        frame.setEnabled(false);
        this.frame = frame;
        initComponents();
    }

    @Override
    public void dispose() {
        frame.setEnabled(true);
        super.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_debetors = new javax.swing.JPanel();
        onlyDebetors = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDebetors = new javax.swing.JTable();
        distr = new javax.swing.JButton();
        returnBook = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Список всех выдач");
        setMinimumSize(new java.awt.Dimension(666, 501));
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));

        onlyDebetors.setText("только должники");
        onlyDebetors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlyDebetorsActionPerformed(evt);
            }
        });

        tableDebetors.setModel(GetBookModel);
        jScrollPane1.setViewportView(tableDebetors);

        distr.setText("Выдать");
        distr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                distrActionPerformed(evt);
            }
        });

        returnBook.setText("Вернуть");
        returnBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnBookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_debetorsLayout = new javax.swing.GroupLayout(panel_debetors);
        panel_debetors.setLayout(panel_debetorsLayout);
        panel_debetorsLayout.setHorizontalGroup(
            panel_debetorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_debetorsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_debetorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_debetorsLayout.createSequentialGroup()
                        .addComponent(onlyDebetors)
                        .addGap(227, 227, 227)
                        .addComponent(distr, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(returnBook, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 644, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_debetorsLayout.setVerticalGroup(
            panel_debetorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_debetorsLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panel_debetorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(onlyDebetors)
                    .addComponent(distr)
                    .addComponent(returnBook))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel_debetors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel_debetors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void distrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_distrActionPerformed
        DistrBook dist = new DistrBook(this.conn);
        dist.setVisible(true);

    }//GEN-LAST:event_distrActionPerformed

    private void returnBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnBookActionPerformed
        Calendar cal = Calendar.getInstance();
        Date dat = cal.getTime();
        if (tableDebetors.getSelectedRow() >= 0 && this.GetBookModel.getItem(tableDebetors.getSelectedRow()).getEndDate() == null) {
            try (PreparedStatement statement = this.conn.prepareStatement(
                    "UPDATE getBook SET endDate=? WHERE id_getBook=?")) {
                System.out.println(this.GetBookModel.getItem(tableDebetors.getSelectedRow()).getBookId());
                statement.setDate(1, new java.sql.Date(dat.getTime()));
                statement.setInt(2, this.GetBookModel.getItem(tableDebetors.getSelectedRow()).getGetBookId());
                statement.execute();
                
                PreparedStatement statement2 = this.conn.prepareStatement("UPDATE `books` SET countAvail=countAvail+1 WHERE id_book=?") ;
                statement2.setInt(1, this.GetBookModel.getItem(tableDebetors.getSelectedRow()).getBookId());
                statement2.execute();
                BookModel.Refresh();
                this.GetBookModel.GetAll();
                
                JOptionPane.showMessageDialog(this, "Книга возвращена");
            } catch (Exception ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(this, "При возврате книги произошла ошибка");
            }
        }else{
            JOptionPane.showMessageDialog(this, "Книга уже была возвращена");
        }
    }//GEN-LAST:event_returnBookActionPerformed

    private void onlyDebetorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onlyDebetorsActionPerformed
        this.GetBookModel.changeDeb();
    }//GEN-LAST:event_onlyDebetorsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton distr;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox onlyDebetors;
    public static javax.swing.JPanel panel_debetors;
    private javax.swing.JButton returnBook;
    private javax.swing.JTable tableDebetors;
    // End of variables declaration//GEN-END:variables
}
