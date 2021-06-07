package Main.TableModels;

import Main.Book;
import static Main.Main.typeModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;


public class TableBooksModel extends AbstractTableModel {

    ArrayList<Book> items = new ArrayList<>();
    public Connection conn;
    
    public TableBooksModel(Connection con){
        conn = con;
    }
        
    public void GetAll(ResultSet result){
        try{
            while (result.next()){
                items.add(new Book(result.getString("name"), result.getString("author"), result.getInt("id_book"), result.getInt("countAvail"), result.getInt("count"), result.getInt("id_typeBook")));
            }
        }
        catch(SQLException e){
            System.out.println("Не удалось получить данные");
        }
        
        this.fireTableDataChanged();
    }
    
    public void Refresh(){
         try (PreparedStatement statement = this.conn.prepareStatement(
            "SELECT * FROM `books`")) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            items.clear();
            while (rs.next()){
                items.add(new Book(rs.getString("name"), rs.getString("author"), rs.getInt("id_book"), rs.getInt("countAvail"), rs.getInt("count"), rs.getInt("id_typeBook")));
            }
            
        } catch (SQLException e) {
                e.printStackTrace();
        }
        
        this.fireTableDataChanged();
    }
    
    
     public Book[] getItems() {
        return items.stream().toArray(Book[]::new);
    }
     
    public Book getItem(int i){
        return items.get(i);
    }
     
     
    public void Add(Book book) {
       String generatedColumns[] = { "id_book" };
        try (PreparedStatement statement = this.conn.prepareStatement(
            "INSERT INTO books(`name`, `author`, `countAvail`, `count`, `id_typeBook`) VALUES(?,?,?,?,?)", generatedColumns)) {
            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getCount());
            statement.setInt(4, book.getCount());
            statement.setInt(5, book.getType());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){
                book.setId(rs.getInt(1));
            }
            
        } catch (SQLException e) {
                e.printStackTrace();
        }
        items.add(book);
        this.fireTableDataChanged();
    }
    
    
    public void Update(Book book){

        try (PreparedStatement statement = this.conn.prepareStatement(
            "UPDATE books SET name=?, author=?, countAvail=?, count=?, id_typeBook=? WHERE id_book=?")) {
            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getCountAvail());
            statement.setInt(4, book.getCount());
            statement.setInt(5, book.getType());
            statement.setInt(6, book.getId());
            statement.execute();

            for(Book b: items){
                if (b.getId() == book.getId()){
                    b.setName(book.getName());
                    b.setAuthor(book.getAuthor());
                    b.setCount(book.getCount());
                    b.setCountAvail(book.getCountAvail());
                    b.setType(book.getType());
                }
            }
 
        }catch (SQLException e){
            e.printStackTrace();
        }
        
        this.fireTableDataChanged();
    }
    
    
    public void Remove(int numRow){
        try (PreparedStatement statement = this.conn.prepareStatement(
            "DELETE FROM books WHERE id_book=?")) {
            statement.setInt(1, this.getItem(numRow).getId());
            statement.execute();
            
            items.remove(numRow);
            
            JOptionPane.showMessageDialog(null, "Книга удалена!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Книга не была удалена!");
        }
        
        this.fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return getItems().length;
    }

    @Override
    public int getColumnCount() {
        return 6;
    }
    
    @Override
    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:
                return this.getItems()[r].getId();
            case 1:
                return this.getItems()[r].getName();
            case 2:
                return this.getItems()[r].getAuthor();

            case 3:
                return this.getItems()[r].getCountAvail();
            case 4:
                return this.getItems()[r].getCount();
            case 5:
                return typeModel.getItemById(this.getItems()[r].getType()).getName();
        }
        return "";
    }
    
    @Override
    public String getColumnName(int c) {
        
        switch (c) {
            case 0:
                return "id";
            case 1:
                return "Название";
            case 2:
                return "Автор";
            case 3:
                return "Доступное количество";
            case 4:
                return "Общее количество";
            case 5:
                return "Тип книги";
            default:
                return "";
        }
        
    }
    
}
