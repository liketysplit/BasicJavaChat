import java.util.*;
import java.io.Serializable;

public class ChatChannel implements Serializable{
    
    private static final long serialVersionUID = -7873671506940428052L;
    private ArrayList<Message> channelHistory;
    private String channelName;

    public ChatChannel(String s){
        this.channelName =s;
        //this.channelHistory = channelHistory;
        channelHistory = new ArrayList<Message>();
        //channelHistory.add("Testing HS");
    }

    public String getChannelName(){
        return channelName;
    }
    public ArrayList<Message> getChannelHistory(){
        return channelHistory;
    }

    public void setChannelName(String s){
        channelName = s;
    }
    public void addToChannelHistory(Message m){
		channelHistory.add(m);
    }
}