package Main;
public class Book {
    
    private String Name;
    private String Author;
    private int CountAvail = 0;
    private int Count = 0;
    private int book_id = -1;
    private int type = -1;

    //Конструктор для обычных книг
    public Book(String Name, String author, int book_id, int countAvail, int count, int type ){
        this.Name = Name;
        this.Author = author;
        this.CountAvail = countAvail;
        this.Count = count;
        this.book_id = book_id;
        this.type = type;
    }

    //Конструктор для пустых книг
    public Book(int book_id, String Name, String author, int count, int type){
        this.book_id = book_id;
        this.Name = Name;
        this.Author = author;
        this.Count = count;
        this.type = type;
    }
    
    public void setName(String name){
        this.Name = name;
    }
    
    public String getName(){
        return this.Name;
    }
    
    public void setAuthor(String desc){
        this.Author = desc;
    }
    
    public String getAuthor(){
        return this.Author;
    }
    
    public void setId(int id){
        this.book_id = id;
    }
    
    public int getId(){
        return this.book_id;
    }
    
    public void setCountAvail(int count){
       this.CountAvail = count;
    }
    
    public int getCountAvail(){
        return this.CountAvail;
    }
    
    public void setCount(int count){
        this.Count = count;
    }
    
    public int getCount(){
        return this.Count;
    }
    
    public int getType(){
        return this.type;
    }
    
    public void setType(int type){
        this.type = type;
    }
}
