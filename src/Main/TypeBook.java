package Main;


public class TypeBook {
    
    private int id_typeBook;
    private String name;
    private int count;
    
    public TypeBook(int id, String name, int count){
        this.id_typeBook = id;
        this.name = name;
        this.count = count;
    }
    
    public int getTypeBookId(){
        return this.id_typeBook;
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getCount(){
        return this.count;
    }
    
    
    public void setTypeBookId(int id){
        this.id_typeBook = id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setCount(int count){
        this.count = count;
    }
}
