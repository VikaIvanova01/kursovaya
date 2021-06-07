package Main.TableModels;

import Main.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;


public class TableReadersModel extends AbstractTableModel {
    

    ArrayList<Reader> items2 = new ArrayList<>();

    public Connection conn;
    
    
    public TableReadersModel(Connection con){
        conn = con;
    }
        
    public void GetAll(ResultSet result){
        try{
            while (result.next()){
                items2.add(new Reader(result.getString("fio"),result.getInt("id_reader"), result.getString("mobile"), result.getDate("birthday") ));
            }
        }
        catch(SQLException e){
            System.out.println("Не удалось получить данные");
        }
        
        this.fireTableDataChanged();
    }
    
    
     public Reader[] getItems() {
        return items2.stream().toArray(Reader[]::new);
    }
     
    public Reader getItem(int i){
        return items2.get(i);
    }
     
     
    public void Add(Reader reader) {
       String generatedColumns[] = { "id_reader" };
        try (PreparedStatement statement = this.conn.prepareStatement(
            "INSERT INTO readers(`fio`, `mobile`, `birthday`) VALUES(?,?,?)", generatedColumns)) {
            statement.setString(1, reader.getFIO());
            statement.setString(2, reader.getModile());
            statement.setDate(3, reader.getBirthday());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){
                reader.setId(rs.getInt(1));
            }
            
        } catch (SQLException e) {
                e.printStackTrace();
        }
        items2.add(reader);
        this.fireTableDataChanged();
    }
    
    
    public void Update(Reader reader){

        try (PreparedStatement statement = this.conn.prepareStatement(
            "UPDATE readers SET fio=?, mobile=?, birthday=? WHERE id_reader=?")) {
            statement.setString(1, reader.getFIO());
            statement.setString(2, reader.getModile());
            statement.setDate(3, reader.getBirthday());
            statement.setInt(4, reader.getId());
            statement.execute();

            for(Reader b: items2){
                if (b.getId() == reader.getId()){
                    b.setFIO(reader.getFIO());
                    b.setMobile(reader.getModile());
                    b.setBirthday(reader.getBirthday());
                }
            }
 
        }catch (SQLException e){
            e.printStackTrace();
        }
        
        this.fireTableDataChanged();
    }
    
    
    public void Remove(Reader reader){
        try (PreparedStatement statement = this.conn.prepareStatement(
            "DELETE FROM readers WHERE id_reader=?")) {
            statement.setInt(1, reader.getId());
            statement.execute();
            
            int index=0;
            for(Reader b: items2){
                if (b.getId() == reader.getId()){
                    break;
                }
                index++;
            }
            
            items2.remove(index);
            JOptionPane.showMessageDialog(null, "Читатель удален!");
 
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Читатель не был удален!");
        }
        
        this.fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return getItems().length;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }
    
    @Override
    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:
                return this.getItems()[r].getId();
            case 1:
                return this.getItems()[r].getFIO();
            case 2:
                return this.getItems()[r].getModile();
            case 3:
                return this.getItems()[r].getBirthday();
        }
        return "";
    }
    
    @Override
    public String getColumnName(int c) {
        switch (c) {
            case 0:
                return "id";

            case 1:
                return "ФИО читателя";

            case 2:
                return "Номер телефона";

            case 3:
                return "Дата рождения";
            default:
                return "";
        }
    }
}
