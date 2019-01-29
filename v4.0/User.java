import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.io.Serializable;

public class User implements Serializable{

    private static final long serialVersionUID = -7873671506940428052L;

    private String name;
    private String channel;
    ObjectOutputStream os;
    ObjectInputStream is;

    public User(String n, String c){
        name =n;
        channel=c;
    }
    public String getChannel(){
        return channel;
    }
    public String getName(){
        return name;
    }

    public void setName(String s){
        name = s;
    }
    public void setChannel(String s){
        channel=s;
    }

    public void setOos(Socket s){
        try{
        os = new ObjectOutputStream(s.getOutputStream());;
        }catch(IOException ioe){

        }
    }
    public void setOis(Socket s){
        try{
        is = new ObjectInputStream(s.getInputStream());
    }catch(IOException ioe){

    }
    }

    public ObjectOutputStream getOos(){
        return os;
    }
    public ObjectInputStream getOis(){
        return is;
    }

}