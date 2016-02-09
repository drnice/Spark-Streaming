package DRSpark.streaming;

import java.net.*;
import java.io.*;

public class ClientApp {
	public static void main(String[] args) {
		/* This program opens a client soc awaiting Spark Stream
		 * to connect.  Start this client applicaiton first then start
		 * Spark Stream.  Once Spark Stream is started the client app 
		 * will await for StdInput from user.  Anything written into 
		 * stdin is written to the socket that spark is listening on **/
		try{
		    System.out.println("Defining new Socket");
		    ServerSocket soc = new ServerSocket(9087);
		    System.out.println("Waiting for Incoming Connection");
		    Socket clientSocket = soc.accept();

		    System.out.println("Connection Received");
		    OutputStream outputStream = clientSocket.getOutputStream();
		    /* Keep Reading the data in a Infinite loop and 
		     * send it over to the Socket.*/
		    while(true){
		        PrintWriter out = new PrintWriter(outputStream, true);
		        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		        System.out.println("Waiting for user to input some data");
		        String data = read.readLine();
		        System.out.println("Data received and now writing it to Socket");
		        out.println(data);

		    }

		}catch(Exception e ){
		    e.printStackTrace();
		}
	}
}