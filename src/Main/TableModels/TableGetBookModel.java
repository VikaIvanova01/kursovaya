package Main.TableModels;

import Main.GetBook;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.AbstractTableModel;


public class TableGetBookModel extends AbstractTableModel {
    
    
    ArrayList<GetBook> items2 = new ArrayList<>();
    public Connection conn;
    public boolean onlyDeb = false;
    
    
    public TableGetBookModel(Connection con){
        this.conn = con;
        this.GetAll();
    }
        
    public void GetAll(){
        
        this.items2.clear();
        
        try (PreparedStatement statement = this.conn.prepareStatement(
            "SELECT id_getBook, getBook.id_reader, getBook.id_book, startDate, endDate, countDay, books.name, readers.fio FROM getBook LEFT JOIN books ON getBook.id_book = books.id_book LEFT JOIN readers ON getBook.id_reader = readers.id_reader")) {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()){
                if (this.onlyDeb){
                    if (rs.getDate(5) == null){
                        items2.add(new GetBook(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4), rs.getDate(5), rs.getInt(6), rs.getString(7), rs.getString(8)));
                    }   
                }else{
                    items2.add(new GetBook(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4), rs.getDate(5), rs.getInt(6), rs.getString(7), rs.getString(8)));
                }
                    
            
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
 
        this.fireTableDataChanged();
    }
   
    
    
     public GetBook[] getItems() {
        return items2.stream().toArray(GetBook[]::new);
    }
     
    public GetBook getItem(int i){
        return items2.get(i);
    }
    
    public void changeDeb(){
        this.onlyDeb = !this.onlyDeb;
        this.GetAll();
    }
     
     
    public void Add(GetBook getbook) {
       String generatedColumns[] = { "id_getBook" };
        try (PreparedStatement statement = this.conn.prepareStatement(
            "INSERT INTO getBook(`id_reader`, `id_book`, `startDate`, `countDay`) VALUES(?,?,?,?)", generatedColumns)) {
            statement.setInt(1, getbook.getReaderId());
            statement.setInt(2, getbook.getBookId());
            statement.setDate(3, getbook.getStartDate());
            statement.setInt(4, getbook.getCount());
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){
                getbook.setId(rs.getInt(1));
            }
            
        } catch (SQLException e) {
                e.printStackTrace();
        }
        items2.add(getbook);
        this.fireTableDataChanged();
    }
    
    
    public void Update(GetBook getbook){

        try (PreparedStatement statement = this.conn.prepareStatement(
            "UPDATE getBook SET id_reader=?, id_book=?, startDate=?, endDate=?, countDay=? WHERE id_getBook=?")) {
            statement.setInt(1, getbook.getReaderId());
            statement.setInt(2, getbook.getBookId());
            statement.setDate(3, getbook.getStartDate());
            statement.setDate(4, getbook.getEndDate());
            statement.setInt(5, getbook.getCount());
            statement.setInt(6, getbook.getGetBookId());
            statement.execute();
            

            for(GetBook b: items2){
                if (b.getGetBookId() == getbook.getGetBookId()){
                    b.setCount(getbook.getCount());
                    b.setStartDate(getbook.getStartDate());
                    b.setEndDate(getbook.getEndDate());
                }

            }
 
        }catch (SQLException e){
            e.printStackTrace();
        }
        
        this.fireTableDataChanged();
    }
    
    
    @Override
    public int getRowCount() {
        return getItems().length;
    }

    @Override
    public int getColumnCount() {
        return 7;
    }
    
    @Override
    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:
                return this.getItems()[r].getGetBookId();
            case 1:
                return this.getItems()[r].getReaderName();
            case 2:
                return this.getItems()[r].getBookName();
            case 3:
                return this.getItems()[r].getStartDate();
            case 4:
                return this.getItems()[r].getEndDate();
            case 5:
                return this.getItems()[r].getCount();
            case 6:
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(this.getItems()[r].getStartDate().getTime());
                cal.add(Calendar.DATE, this.getItems()[r].getCount());
                Date date = cal.getTime();
                return new SimpleDateFormat("yyyy-MM-dd").format(date);
        }
        return "";
    }
    
    @Override
    public String getColumnName(int c) {
        switch (c) {
            case 0:
                return "Номер выдачи";

            case 1:
                return "ФИО читателя";

            case 2:
                return "Название книги";

            case 3:
                return "Дата взятия";

            case 4:
                return "Дата возврата";

            case 5:
                return "Кол. дней";

            case 6:
                return "Примерная дата возврата";
            default:
                return "";

        }
    }
}
