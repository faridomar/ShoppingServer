/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoppingserver;

import java.io.IOException;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Farid omar
 */
public class ShoppingServer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        final Server server=new Server();
       /* FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        loader.setController(new FXMLDocumentController(path));*/
        Parent root =FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        shoppingserver.FXMLDocumentController.setIPText(shoppingserver.FXMLDocumentController.getIPAddress());
        shoppingserver.FXMLDocumentController.setPorText(shoppingserver.FXMLDocumentController.GetInfo());
        new Thread(new Runnable() 
	                {
	                    @Override
	                    public void run()
                            {
                                try {
                                    server.listen();
                                } catch (IOException ex) {
                                    Logger.getLogger(ShoppingServer.class.getName()).log(Level.SEVERE, null, ex);
                                }
	                    }	             
	                }).start();
    }
     public static void popup(String value) throws IOException
      {
          System.out.println(value);
         Stage stage; 
         Parent root;
         stage = new Stage();
         root = FXMLLoader.load(ShoppingServer.class.getResource("FXML.fxml"));
         stage.setScene(new Scene(root));
         stage.setTitle("Order");
          shoppingserver.FXMLDocumentController.setArea(value);
         stage.showAndWait();
      }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
