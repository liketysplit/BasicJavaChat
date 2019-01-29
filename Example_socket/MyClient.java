import java.net.*;
import java.io.*;
import java.util.*;

public class MyClient{
	public static void main(String[] args){
		try{
			//create a socket to make connection to server socket
			Socket sock = new Socket("127.0.0.1", 7000);
			
			//create an output stream to send data to the server
			DataOutputStream data2server = new DataOutputStream(sock.getOutputStream());
			//create an input stream to receive data from server
			DataInputStream result4mserver = new DataInputStream(sock.getInputStream());

			Scanner inp = new Scanner(System.in);
			System.out.print("Enter a positive number: ");
			double n = inp.nextDouble();
			
			//send the data to the server
			data2server.writeDouble(n);
			data2server.flush();
			
			//receive the result from the server	
			double result = result4mserver.readDouble();
			System.out.println("Result received from the server: " + result);
			sock.close();
		 } catch(IOException ioe){
				System.err.println(ioe);
			}
	}//End-of-main
}//End-ofclass