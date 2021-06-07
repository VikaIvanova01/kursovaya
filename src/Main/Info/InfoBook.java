
package Main.Info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;

public class InfoBook extends javax.swing.JFrame {

    private final Connection conn;
    private final JFrame frame;

    public InfoBook(Connection conn, int id, JFrame frame) {
        this.conn = conn;
        this.frame = frame;
        frame.setEnabled(false);
        initComponents();
        checkBook(id);
    }
    
    
    @Override
    public void dispose() {
    frame.setEnabled(true);
    super.dispose();
}
    
    private void checkBook(int id_book){
        try (PreparedStatement statement = this.conn.prepareStatement(
                "SELECT * FROM books LEFT JOIN getBook ON getBook.id_book = books.id_book LEFT JOIN readers ON getBook.id_reader = readers.id_reader LEFT JOIN typeBook ON books.id_typeBook = typeBook.id_typeBook WHERE books.id_book=?"
                        )) {
            statement.setInt(1, id_book);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            String info = "";
            while (rs.next()) {
                this.label_name.setText("Название: "+rs.getString(2));
                this.label_opis.setText("Автор: "+rs.getString(3));
                this.type.setText("Тип: "+rs.getString(18));
                if ( rs.getString(11) == null && rs.getString(7) != null){
                info += "";
                info += "ФИО: "+rs.getString(14)+"\n";
                info += "Уникальный номер: "+rs.getString(13)+"\n";
                info += "Дата взятия: "+rs.getString(10)+"\n";
                info += "------------------------------"+"\n\n";
                }

            }
            if (info.length() == 0)
                info = "Читатели отсутствуют";
            
            this.foundReaders.setText(info);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_name = new javax.swing.JLabel();
        label_opis = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        foundReaders = new javax.swing.JTextArea();
        type = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Информация о книге");

        label_name.setText("ФИО:");

        label_opis.setText("Описание:");

        jLabel3.setText("Читатели с этой книгой");

        foundReaders.setEditable(false);
        foundReaders.setColumns(20);
        foundReaders.setRows(5);
        jScrollPane1.setViewportView(foundReaders);

        type.setText("Тип");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(type)
                    .addComponent(jLabel3)
                    .addComponent(label_opis)
                    .addComponent(label_name)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_name)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_opis)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(type)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea foundReaders;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_name;
    private javax.swing.JLabel label_opis;
    private javax.swing.JLabel type;
    // End of variables declaration//GEN-END:variables
}
