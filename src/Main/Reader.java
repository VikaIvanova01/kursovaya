package Main;

import java.sql.Date;


public class Reader {
    
    private String Fio;
    private String mobile;
    private Date birthday;
    private int reader_id = -1;
    
    
    public Reader(String fio, int id, String mobile, Date birthday){
        this.Fio = fio;
        this.reader_id = id;
        this.mobile = mobile;
        this.birthday = birthday;
    }
    
    public Reader(String fio, String mobile, Date birthday){
        this.Fio = fio;
        this.mobile = mobile;
        this.birthday = birthday;
    }
    
    public void setId(int id){
        this.reader_id = id;
    }
    
    public int getId(){
        return this.reader_id;
    }
    
    
    public String getFIO(){
        return this.Fio;
    }
    
    public void setFIO(String fio){
        this.Fio = fio;
    }
    
    public String getModile(){
        return this.mobile;
    }
    
    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    
    public Date getBirthday(){
        return this.birthday;
    }
    
    public void setBirthday(Date birthday){
        this.birthday = birthday;
    }
    
}
