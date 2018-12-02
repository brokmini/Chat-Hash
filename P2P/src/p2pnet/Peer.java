package p2pnet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Set;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Enumeration;
import java.util.Random;
import java.io.*;
import p2pnet.Hash;

public class Peer
{
  public static void main(String[] args) throws IOException
  {
          Scanner input= new Scanner(System.in);
          while(true)
          {  
          String n= input.nextLine();
          Set<String> keys = Hash.hashtable.keySet();
          Iterator<String> itr = keys.iterator();
          Integer port = 9000;
          Integer connect_port=3000;
       	if(itr.next()!=null)
       	   {
       		Listener  listener= new Listener(port);
            listener.start();   
            Hash.writeHash(n,port);
            System.out.println(Hash.readHash());
       	   }
       	   else 
       	   {   
               Listener listener= new Listener(port);
        	   listener.start();
       		   connect(connect_port,port);
       		   System.out.println(Hash.readHash());
       		   Hash.writeHash(n,connect_port);
               
       	   }
          } 
          }      
  
   

    /*else
    {
      Integer listen_port = 8000;
      Integer connect_port = 9000;
      Listener  listener= new Listener(listen_port);
      listener.start();
      connect(connect_port,listen_port);
    } */

  //**********************************************************************************************************************************************
	//function that is called when a new node connects to existing node in the network
			public static void connect(Integer port,Integer myPort)
			{
				try
				{
				Socket socket = new Socket("localhost", port);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.write(String.valueOf(myPort)+" Connect \n");
				out.flush();
				//System.out.println("I am trying to connect: "+socket);

				MessageListener p = new MessageListener(socket);
				p.start();
				}
				catch (IOException e)
				{
					System.out.println("Connect Failed"); System.exit(-1);
				}
			}
} // End of class Peer

//**********************************************************************************************************************************************
//This thread actively listens for any nodes connecting to it
class Listener extends Thread
{
			Integer port;
			public Listener(Integer p)
			{
				port = p;
			}
			public void run()
			{
						ServerSocket serverSocket = null;
						Socket clientSocket = null;
						try
						{
							serverSocket = new ServerSocket(port);
						}
						catch (IOException e)
						{
							System.out.println("Could not listen on port"); System.exit(-1);
						}

						while(true)
						{
									try
									{
										System.out.println("Listening...");
										clientSocket = serverSocket.accept();
										MessageListener l=new MessageListener(clientSocket);
										l.start();

                    //To advertise about self
										PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
										out.write(String.valueOf(port)+" Init\n");
										out.flush();


									}
									catch (IOException e)
									{
										System.out.println("Accept failed"); System.exit(-1);
									}
						}
			}

} //End of Listener class
//***********************************************************************************************************************************************

class MessageListener extends Thread
{

			Socket socket;
			public MessageListener(Socket s)
				{
				socket=s;
				}
			public void run()
			{
				while(true)
				{
					try
					{
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						String recdData=in.readLine();
            System.out.println("MESSAGE RECEIVED IS : \n");
            System.out.println(recdData);
          } //End of Try
          catch(IOException e)
  				{
          }
        } //End of while

      } // End of run
} // End of MessageListener Class
