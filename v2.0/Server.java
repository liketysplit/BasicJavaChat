//Tyler Angelier
//Rick Boles
//Michael Montgomery
//Course Number:(CS4345)
//Semester-Year:(Spring 2018)
//Assignment:(Program 2)

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static final int PORT = 3000;
    private static ArrayList<String> names = new ArrayList<String>();
    private static ArrayList<String> groups = new ArrayList<String>();
    private static ArrayList<PrintWriter> writers = new ArrayList<PrintWriter>();

    public static void main(String[] args) throws Exception {
        groups.add("General");
        groups.add("Off-Topic");
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
                
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                writers.add(out);
                while (true) {
                    out.println("REQ");
                    name = in.readLine();
                    if (name == null) {
                        return;
                    }
                    synchronized (names) {
                        if (!names.contains(name)) {
                            names.add(name);
                            for (PrintWriter writer : writers) {
                                writer.println("TX " + name + " has joined the chat!");
                                for (String name : names) {
                                    writer.println("US " + name);
                                }
                                for (String group : groups) {
                                    writer.println("GP " + group);
                                }
                            }
                            System.out.println(name + " has joined the chat!");
                            break;
                        }
                    }
                }

                out.println("RES");
                

                while (true) {
                    String input = in.readLine();
                    System.out.println(input);
                    if (input == null) {
                        return;
                    }else if (input.contains("+/w")) {
                        String tName = input.substring(0,input.indexOf("+"));
                        int tc=0;
                        int al=-1;
                        int sender=-2;
                        String temp = "";
                        String mInput = input.substring(input.indexOf("+")+1, input.length());
                        String oSet = "";
                        String rec = "";
                        for (String name : names) {
                            temp = "/w " + name;
                            if(mInput.startsWith(temp)){
                                al=tc;
                                oSet = temp;
                                rec = name;
                            }
                            if(name.equals(tName)){
                                sender=tc;
                            }
                            tc++;
                        }
                        if(al!=-1){
                            if(tName.equals(rec)){
                                writers.get(sender).println("TX " + "Talking to youself again?!");
                            }else{
                            writers.get(al).println("TX " + tName + "(" + tName + "-->" + rec + "):"  + mInput.substring(oSet.length()));
                            writers.get(sender).println("TX " + tName + "(" + tName + "-->" + rec + "):" +  mInput.substring(oSet.length()));
                            }
                        }else{
                            writers.get(sender).println("TX " + "User is not online!");
                        }
                    } else{
                        for (PrintWriter writer : writers) {
                            writer.println("TX " + name + ": " + input);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                if (name != null) {
                    names.remove(name);
                    for (PrintWriter writer : writers) {
                        writer.println("TX " + name + " has left the chat!");
                        writer.println("RM " + name);
                    }
                    System.out.println(name + " has left the chat!");
                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}