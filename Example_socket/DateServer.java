import java.net.*;
import java.io.*;
import java.util.*;

public class DateServer{
	public static void main(String[] args){
		try{
			ServerSocket sock = new ServerSocket(6013);
			System.out.println("Server started at "+ new Date() + '\n');
			int con=0;
			// now listen for connection
			while(true){
				con++;
				Socket client = sock.accept();
				System.out.println("Initiating connection " + con);
				PrintWriter pout = new PrintWriter(client.getOutputStream(), true);

			//write the data to the socket
			pout.println(new java.util.Date().toString());

			//close the socket and resume listening for connections
			client.close();
			System.out.println("Connection " + con + " is over.\n");
			}
		 } catch(IOException ioe){
				System.err.println(ioe);
			}

	}//End-of-main
}//End-of-class