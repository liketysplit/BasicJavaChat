import java.net.*;
import java.io.*;
import java.util.*;

public class MyServer{
	public static void main(String[] args){
		try{
			ServerSocket servSock = new ServerSocket(7000);
			System.out.println("Server started at "+ new Date() + '\n');
			
			//Listen for a connection request
			Socket sock = servSock.accept();
			
			//create data input and data output streams
			DataInputStream input4mclient = new DataInputStream(sock.getInputStream());
			DataOutputStream output2client = new DataOutputStream(sock.getOutputStream());

				//receive data from client
				double number = input4mclient.readDouble();
				// do calculation
				double sqRoot = Math.sqrt(number);
				//send result back to client
				output2client.writeDouble(sqRoot);
				System.out.println("Data received from client: " + number);
				System.out.println("Calculated result: " + sqRoot);
				System.out.println("Connection is over.\n");
				sock.close();
			
		 } catch(IOException ioe){
				System.err.println(ioe);
			}

	}//End-of-main
}//End-of-class