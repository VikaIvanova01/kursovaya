package Main.Info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;




public class InfoReader extends javax.swing.JFrame {

    private final Connection conn;
    private final JFrame frame;

    public InfoReader(Connection con, int id, JFrame frame) {
        this.conn = con;
        this.frame = frame;
        frame.setEnabled(false);
        initComponents();
        checkUser(id);
    }

    
    @Override
    public void dispose() {
    frame.setEnabled(true);
    super.dispose();
}
    
    private void checkUser(int id_reader){
        try (PreparedStatement statement = this.conn.prepareStatement(
                "SELECT * FROM readers LEFT JOIN getBook ON getBook.id_reader = readers.id_reader LEFT JOIN books ON getBook.id_book = books.id_book WHERE readers.id_reader=?"
                        )) {
            statement.setInt(1, id_reader);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            
            
            String info = "";
            while (rs.next()) {
                
                this.label_fio.setText("ФИО: "+rs.getString(2));
                this.label_num.setText("Уникальный номер: "+rs.getString(1));
                this.mobile.setText("Телефон: "+rs.getString(3));
                this.birth.setText("День рождения: "+rs.getString(4));
                if (rs.getString(9) == null && rs.getString(5) != null){
                info = "";
                info += "Название: "+rs.getString(12)+"\n";
                info += "Автор: "+rs.getString(13)+"\n";
                info += "Дата взятия: "+rs.getString(8)+"\n";
                info += "------------------------------"+"\n\n";
                }
            }
            if (info.length() == 0)
                info = "Книги отсутствуют";
            
            this.foundBooks.setText(info);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_fio = new javax.swing.JLabel();
        label_num = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        foundBooks = new javax.swing.JTextArea();
        mobile = new javax.swing.JLabel();
        birth = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Информация о читателе");

        label_fio.setText("ФИО: ");

        label_num.setText("Уникальный номер:");

        jLabel3.setText("Взятые книги");

        foundBooks.setEditable(false);
        foundBooks.setColumns(20);
        foundBooks.setRows(5);
        jScrollPane1.setViewportView(foundBooks);

        mobile.setText("Телефон:");

        birth.setText("День рождения:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(birth)
                    .addComponent(mobile)
                    .addComponent(label_fio)
                    .addComponent(jLabel3)
                    .addComponent(label_num)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(label_num)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_fio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mobile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(birth)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel birth;
    private javax.swing.JTextArea foundBooks;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_fio;
    private javax.swing.JLabel label_num;
    private javax.swing.JLabel mobile;
    // End of variables declaration//GEN-END:variables
}
