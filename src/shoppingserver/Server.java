/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoppingserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Farid omar
 */
public class Server  extends Thread{
    
    protected Socket server; 
   
   public Server(Socket clientSocket) throws IOException
   {
  
     this.server = clientSocket;
   //   serverSocket.setSoTimeout(100000);
   }
   public static Scanner in=new Scanner(System.in);

    Server() {
        this.server=null;
    }

   public void run()
   {
      while(true)
      {
         try
         {
            Data d=new Data();
            System.out.println("Just connected to "
                  + server.getRemoteSocketAddress());
            DataInputStream in =
                  new DataInputStream(server.getInputStream());
          //  System.out.println(in.readUTF().toString());
          //  System.out.println("tested");
            final String value=in.readUTF().toString();
            String[]split=value.split("\\s+");
            System.out.println(split.length);
            //  FXMLDocumentController.msg="";
            if(split.length==1)
            {
            
            DataOutputStream out =
                 new DataOutputStream(server.getOutputStream());
            out.writeUTF(d.getPrice(value));
            //out.writeUTF("Thank you for connecting to "
             // + server.getLocalSocketAddress() + "\nGoodbye!");
            }
         //   else
           /* {
                String[]firstSplit=value.split("\\n");
                for(int i=0;i<firstSplit.length;i++)
                {
                   String[] seccondSplit=firstSplit[i].split("\\s+");
                   d.InsertDC(seccondSplit[0],seccondSplit[1],seccondSplit[2],Integer.parseInt(seccondSplit[3]));
                }*/
          //  }
            else if(split.length>1)
            {
             Data data=new Data();
             FXMLDocumentController.confirm=value;
             String [] firstSplit=value.split("\\n");
             for(int i=0;i<firstSplit.length;i++)
             {
                 String[] secondSplit=firstSplit[i].split("\\s+");
                 FXMLDocumentController.msg+=data.getName(secondSplit[1])+"  "+secondSplit[3]+"\n";
             }
             Platform.runLater(new Runnable()
            {
               @Override
                public void run() {
                   try {
                       ShoppingServer.popup(FXMLDocumentController.msg);
                   } catch (IOException ex) {
                       Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                   }
              }
             });
            }
            else
            {
                System.out.println("error");
            }
            server.close();
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
          
            e.printStackTrace();
            break;
         } catch (ClassNotFoundException ex) {
              Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
          } catch (SQLException ex) {
              Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
   }
   public static String getIPAddress() 
    {
       Vector<String> values=new Vector<String>();
       Enumeration<NetworkInterface> nis = null;
                try {
                    nis = NetworkInterface.getNetworkInterfaces();
                } catch (SocketException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                NetworkInterface ni;
                while (nis.hasMoreElements()) {
                    ni = nis.nextElement();
                    
                    try {
                        if (!ni.isLoopback() && ni.isUp()) {
                            for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
                                //filter for ipv4/ipv6
                                if (ia.getAddress().getAddress().length == 4) {
                                    //4 for ipv4, 16 for ipv6
                                    String  value=ia.getAddress().toString();
                                    values.add(value);
                                }
                            }
                        }
                    } catch (SocketException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
       return values.firstElement();
   }
    
    public  void listen() throws IOException
    {
       ServerSocket serverSocket=null;
       Socket socket=null;
       System.out.println(shoppingserver.FXMLDocumentController.GetInfo());
        int port = Integer.parseInt(shoppingserver.FXMLDocumentController.getPorText());
         try 
         {
           serverSocket = new ServerSocket(port);
         } catch (IOException e) 
         {
           e.printStackTrace();

        }
          while (true) 
          {
            try 
            {
            System.out.println("Waiting for client on port " +
            serverSocket.getLocalPort() + "...");
             socket = serverSocket.accept();
            } catch (IOException e) 
            {
                System.out.println("I/O error: " + e);
            }
            // new threa for a client
            new Server(socket).start();
        }
    }
   /* public static Vector<String> SplitString(String imput)
    {
     Vector<String> _return= new Vector<String>();
     String line="";
     for(int i=0;i<imput.length();i++)
     {
         line+=imput.charAt(i);
         if(imput.charAt(i)==' '|| i==imput.length()-1)
         {
             _return.add(line);
             line="";
         }
     }
     return _return;
    }
    public Vector<String> FirstSplit(String imput)
    {
        String line;
        Vector<String>_lines=new Vector<String>();
        line="";
         for(int i=0;i<imput.length();i++)
         {
           line+=imput.charAt(i);
           if(imput.charAt(i)=='\n'|| i==imput.length()-1)
            {
             _lines.add(line);
             line="";
            }
        }
         return _lines;
    }*/
}
