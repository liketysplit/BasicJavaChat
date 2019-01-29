import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private static final long serialVersionUID = -5399605122490343339L;
    
    private String msg;
    private Date date;
    private String sender = "";
    private String reciever = "";
    private int id;
    
    public Message(int id, String sender, String reciever,  String msg ){
        date = new Date();
        this.msg = msg;
        this.sender= sender;
        this.reciever = reciever;
        this.id = id;
    }

    public Date getDate(){
        return date;
    }

    public String getMessage(){
        return msg;
    }

    public String getSender(){
        return sender;
    }

    public String getReciever(){
        return reciever;
    }

    public int getID(){
        return id;
    }
    




      
}