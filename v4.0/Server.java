//Tyler Angelier
//Rick Boles
//Michael Montgomery
//Course Number:(CS4345)
//Semester-Year:(Spring 2018)
//Assignment:(Program 2)

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.Channel;
import java.util.ArrayList;

public class Server {

    private static final int PORT = 3000;
    private static ArrayList<User> users = new ArrayList<User>();
    private static ArrayList<ChatChannel> channels = new ArrayList<ChatChannel>();
    private static ArrayList<String> names = new ArrayList<String>();
    private static ArrayList<String> groups = new ArrayList<String>();
    private static ArrayList<ObjectOutputStream> oStreams = new ArrayList<ObjectOutputStream>();

    public static void main(String[] args) throws Exception, UnknownHostException, IOException, ClassNotFoundException {
        ChatChannel gen = new ChatChannel("General");
        ChatChannel offt = new ChatChannel("Off-Topic");

        channels.add(gen);
        channels.add(offt);

        System.out.println("Server is running on port: " + PORT);
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }

    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;


        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(socket.getInputStream());

                //oStreams.add(os);
                while (true) {
                    User newUser = new User("Server", "General");
                    try{
                     newUser= (User)is.readObject();
        
                    }catch (ClassNotFoundException cnfe){

                    }

                    synchronized (users) {
                        boolean found = false;
                        for (User uN : users) { 
                            if(uN.getName().equals(newUser.getName()))
                                found = true;
                        }

                        if (!found) {
                            newUser.setOis(socket);
                            newUser.setOos(socket);
                            users.add(newUser);
                            name=newUser.getName();

                            for (User u : users) { 

                                u.getOos().writeObject(new Message(0, u.getName(), "server", u.getName() + " has joined the chat!")); 
                                u.getOos().flush();       
                                u.getOos().writeObject(new Message(3, "All", "server", u.getName()));
                                u.getOos().flush();

                                for (ChatChannel channel : channels) {
                                        u.getOos().writeObject(new Message(2, "All", "server", channel.getChannelName()));
                                        u.getOos().flush();
                                }
                            }
                            System.out.println(name + " has joined the chat!");
                            break;
                        }
                        
                    }
                }
                

                while (true) {
                    Message input = new Message(0,"","","");
                    try{
                    input = (Message)is.readObject();
                    }catch(ClassNotFoundException cnfe){

                    }
                    if (input == null) {
                        return;
                    }else if (input.getID()==1) {
                        String tx = "";
                        String rx = "";
                        for (User u : users) {
                            if (u.getName().equals(input.getReciever())){
                                rx = u.getName();
                            }
                            if (u.getName().equals(input.getSender())){
                                tx = u.getName();
                            }
                        }
                        if(!tx.equals(rx)){
                            for (User u : users) {
                                if (u.getName().equals(input.getReciever())){
                                    u.getOos().writeObject(new Message(1, input.getReciever(), input.getSender(), input.getMessage()));
                                }
                                if (u.getName().equals(input.getSender())){
                                    u.getOos().writeObject(new Message(1, input.getReciever(), input.getSender(), input.getMessage()));
                                }
                            }
                        }
                        else if(tx.equals(rx)){
                            for (User u : users) {
                                if (u.getName().equals(tx)){
                                    u.getOos().writeObject(new Message(0, "Server", "Server", "Talking to yourself again?"));
                                }
                            }

                        }
                        else{
                            for (User u : users) {
                                if (u.getName().equals(tx)){
                                    u.getOos().writeObject(new Message(0, "Server", "Server", "User is not online!"));
                                }
                            }
                        }
                    } else{
                        
                        for (User u : users) {
                                u.getOos().writeObject(new Message(1, input.getSender(), "All", input.getMessage()));
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                if (name != null) {
                    int loc= -1;
                    for(int i =0; i< users.size();i++){
                        if(users.get(i).getName().equals(name))
                            loc = i;
                    }
                    users.remove(loc);

                    for (User u : users) {
                        //u.getOos().writeObject(new Message(4, "Server", "All", name + " has left the chat!"));
                    }
                    System.out.println(name + " has left the chat!");
                }
                if (out != null) {
                    
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}