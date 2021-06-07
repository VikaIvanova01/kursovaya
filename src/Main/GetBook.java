package Main;

import java.sql.Date;





public class GetBook {
    
    private String book_name, reader_name;
    private int book_id, reader_id, getbook_id, count;
    private Date startDate, endDate;
    
    //Конструктор 
    public GetBook(int id_getBook, int id_reader, int id_book, Date startDate, Date endDay, int countDay, String bookName, String readerName){
        this.getbook_id = id_getBook;
        this.reader_id = id_reader;
        this.book_id = id_book;
        this.startDate = startDate;
        this.endDate = endDay;
        this.count = countDay;
        this.book_name = bookName;
        this.reader_name = readerName;
    }
    
    public int getBookId(){
        return this.book_id;
    }
    
    public int getReaderId(){
        return this.reader_id;
    }
    
    public int getGetBookId(){
        return this.getbook_id;
    }
    
    public String getBookName(){
        return this.book_name;
    }
    
    public String getReaderName(){
        return this.reader_name;
    }
    
    public Date getStartDate(){
        return this.startDate;
    }
    
    public Date getEndDate(){
        return this.endDate;
    }
    
    public int getCount(){
        return this.count;
    }
    
    public void setId(int id){
        this.getbook_id = id;
    }
    
    public void setCount(int count){
        this.count = count;
    }
    
    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }
    
    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }
}
