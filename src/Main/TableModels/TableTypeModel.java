package Main.TableModels;

import Main.TypeBook;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;


public final class TableTypeModel extends AbstractTableModel {

    ArrayList<TypeBook> items = new ArrayList<>();
    public Connection conn;
    
    public TableTypeModel(Connection con){
        conn = con;
        GetAll();
    }
        
    public void GetAll(){
        try (PreparedStatement statement = this.conn.prepareStatement(
            "SELECT * FROM `typeBook`")) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            items.clear();
            while (rs.next()){
                items.add(new TypeBook(rs.getInt("id_typeBook"), rs.getString("name"), rs.getInt("count")));
            }
            
        } catch (SQLException e) {
               System.out.println("Не удалось получить данные");
        }
        
        this.fireTableDataChanged();
    }
    
     public TypeBook[] getItems() {
        return items.stream().toArray(TypeBook[]::new);
    }
     
     public TypeBook getItemById(int id){
         for(TypeBook type: this.items){
             if (type.getTypeBookId() == id)
                 return type;
         }
         return null;
     }
     
    public TypeBook getItem(int i){
        return items.get(i);
    }
     
     
    public void Add(TypeBook type) {
       String generatedColumns[] = { "id_typeBook" };
        try (PreparedStatement statement = this.conn.prepareStatement(
            "INSERT INTO typeBook(`name`, `count`) VALUES(?,?)", generatedColumns)) {
            statement.setString(1, type.getName());
            statement.setInt(2, type.getCount());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){
                type.setTypeBookId(rs.getInt(1));
            }
            
        } catch (SQLException e) {
                e.printStackTrace();
        }
        items.add(type);
        this.fireTableDataChanged();
    }
    
    
    public void Update(TypeBook type){

        try (PreparedStatement statement = this.conn.prepareStatement(
            "UPDATE typeBook SET name=?, count=? WHERE id_typeBook=?")) {
            statement.setString(1, type.getName());
            statement.setInt(2, type.getCount());
            statement.setInt(3, type.getTypeBookId());
            statement.execute();

            for(TypeBook b: items){
                if (b.getTypeBookId() == type.getTypeBookId()){
                    b.setName(type.getName());
                    b.setTypeBookId(type.getTypeBookId());
                    b.setCount(type.getCount());
                }
            }
 
        }catch (SQLException e){
            e.printStackTrace();
        }
        
        this.fireTableDataChanged();
    }
    
    
    public void Remove(int numRow){
        try (PreparedStatement statement = this.conn.prepareStatement(
            "DELETE FROM typeBook WHERE id_typeBook=?")) {
            statement.setInt(1, this.getItem(numRow).getTypeBookId());
            statement.execute();
            
            items.remove(numRow);
            
            JOptionPane.showMessageDialog(null, "Тип удален!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Тип не был удален!");
        }
        
        this.fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return getItems().length;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }
    
    @Override
    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:
                return this.getItems()[r].getTypeBookId();
            case 1:
                return this.getItems()[r].getName();
            case 2:
                return this.getItems()[r].getCount();
                
        }
        return "";
    }
    
    @Override
    public String getColumnName(int c) {
        
        switch (c) {
            case 0:
                return "Номер типа";
            case 1:
                return "Тип";
            case 2:
                return "Дней на возврат";
            default:
                return "";
        }
        
    }
    
}
