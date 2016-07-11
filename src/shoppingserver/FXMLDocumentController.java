/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoppingserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;
import javafx.stage.PopupBuilder;
import javafx.stage.Stage;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;

/**
 *
 * @author Farid omar
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private Label label;
    @FXML private TextField txtid;
    @FXML private TextField txtCode;
    @FXML private TextField txtPath;
    @FXML private TextField txtName;
    @FXML private TextField txtPrice;
    @FXML private TextField txtbarcode;
    @FXML private TextField txtcount;
    @FXML private TextField txtcountcommodity;
    @FXML private TextField txtiddevice;
    @FXML private TextField txtbarcodecommodity;
    @FXML private TextField txtnamecommodity;
    @FXML private TextField txtpricecommodity;
    @FXML private static TextArea txtshop;
    @FXML private TextArea txtcommoditylist;
    @FXML private TextArea txtdevicelist;
    @FXML private static TextField txtport;
    @FXML private Button btnAddDevice;
    @FXML private Button btnConfirm;
    @FXML private static TextField txtip;
    @FXML private Button btnSetPort;
    @FXML private Button btnGenerate;
    @FXML private Button btnshow;
    @FXML private Button btnAddCommodity;
    @FXML private Button btncancel;
    @FXML private Button btndone;
    @FXML private Button btnbrowse;
    @FXML private Button btndeletedevice;
    @FXML private Button btndeletecommodity;
    @FXML private Button btneditcommodity;

    public FXMLDocumentController() {
    }

    @FXML private MenuItem mnuchangeportip;
    @FXML private ImageView imgqr;
    public static String confirm="";
    public static String msg="";

    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    @FXML
    private void btngenerateAction(ActionEvent event) {
        String st=txtCode.getText().toString();
        st=st.replaceAll("\\s+","");
        QR.generateqr(st, txtPath.getText().toString());
        String _path=txtPath.getText().toString()+st+".PNG";
        File file = new File(_path);
        Image image = new Image(file.toURI().toString());
        imgqr.setImage(image);
    }
    @FXML
    private void btndoneActoin(ActionEvent event)
    {
         String _path=txtPath.getText().toString()+txtCode.getText().toString()+".PNG";
        File file = new File(_path);
        Image image = new Image(file.toURI().toString());
         BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
         printImage(bufferedImage);
         Stage stage = (Stage) btndone.getScene().getWindow();
         stage.close();
    }
    @FXML
    private void btncancelAction(ActionEvent event)
    {
         Stage stage = (Stage) btncancel.getScene().getWindow();
         stage.close();
    }
    @FXML
    private void btnbrowseAction(ActionEvent event)
    {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("JavaFX Projects");
        File defaultDirectory = new File("d:");
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(btnbrowse.getScene().getWindow());
        txtPath.setText(selectedDirectory.getAbsolutePath());
    }
    @FXML
    private void mnuchangeportAction(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException {
        btnSetPort.setVisible(true);
        txtport.setDisable(false); 
    }
    @FXML
    private void btnSetPortAction(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException
    {
        String Port="";
        Port=txtport.getText().toString();
        SetInfo(Port);
        btnSetPort.setVisible(false);
        txtport.setDisable(true);
    }
    @FXML
    private void btnAddCommodityAction(ActionEvent event) throws IOException
    {
        Data data=new Data();
        int price=Integer.parseInt(txtPrice.getText().toString());
        int count=Integer.parseInt(txtcount.getText().toString());
        try {
            data.InsertCommodity(txtbarcode.getText().toString(),txtName.getText().toString(), price,count);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     @FXML
     private void btnAddAction(ActionEvent event) throws IOException
     {
         Data data=new Data();
        try {
            data.InsertDevice(txtid.getText().toString());
          
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     @FXML
     private void mnugenerateqrAction(ActionEvent event) throws IOException
     {
         Stage stage; 
         Parent root;
         stage = new Stage();
         root = FXMLLoader.load(getClass().getResource("QR.fxml"));
         stage.setScene(new Scene(root));
         stage.setTitle("QR");
         stage.showAndWait();
     }
     @FXML
     private void mnuidAction(ActionEvent event) throws IOException, ClassNotFoundException, SQLException
     {
         Stage stage; 
         Parent root;
         stage = new Stage();
         root = FXMLLoader.load(getClass().getResource("EDIT.fxml"));
         stage.setScene(new Scene(root));
         stage.setTitle("Edit");
         stage.showAndWait();
     }
     @FXML
     private void btnshowAction(ActionEvent event)
     {
           Data d=new Data();
        try {
            txtcommoditylist.setText(d.geBarcode_Name());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            txtdevicelist.setText(d.getDevice());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     @FXML
     private void btnsearchAction(ActionEvent event) throws SQLException, ClassNotFoundException
     {
         String barcode=txtbarcodecommodity.getText().toString();
         Data d=new Data();
         String[] result=d.getCommodity(barcode);
         txtnamecommodity.setText(result[1]);
         txtpricecommodity.setText(result[2]);
         txtcountcommodity.setText(result[3]);     
     }
     @FXML
     private void btndeletedeviceAction(ActionEvent event) throws ClassNotFoundException, SQLException
     {
         Data d=new Data();
         if(txtiddevice.getText()!="")
         {
         d.DeleteDevice(txtiddevice.getText().toString());
         }
          pressed();
     }
     @FXML
     private void btndeletecommodityAction(ActionEvent event) throws ClassNotFoundException, SQLException
     {
         Data d=new Data();
         if(txtbarcodecommodity.getText()!="")
         {
             d.DeleteCommodity(txtbarcodecommodity.getText().toString());
         }
          pressed();
     }
     @FXML
     private void btneditcommodityAction(ActionEvent event) throws ClassNotFoundException, SQLException
     {
         Data d=new Data();
         int _count=Integer.parseInt(txtcountcommodity.getText().toString());
         float _price=Float.parseFloat(txtpricecommodity.getText().toString());
         d.UpdateCommodity(txtbarcodecommodity.getText().toString(), txtnamecommodity.getText().toString(),_price , _count);
          pressed();
     }
     @FXML
     private void btnConfirmAction(ActionEvent event) throws IOException
     {
         final Data data=new Data();
         final String[]firstSplit=confirm.split("\\n");
         System.out.println(firstSplit.length);
           for(int i=0;i<firstSplit.length;i++)
           {
               final int k=i;
               Thread t1=new Thread(new Runnable() {
                   @Override
                   public void run() {
                       String[] seccondSplit=firstSplit[k].split("\\s+");
                       System.out.println(seccondSplit[3]);
                       int _Count=Integer.parseInt(seccondSplit[3]);
                       try {
                           data.setCount(_Count, seccondSplit[1]);
                           data.InsertDC(seccondSplit[0],seccondSplit[1],seccondSplit[2],_Count);
                       } catch (ClassNotFoundException ex) {
                           Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                       } catch (SQLException ex) {
                           Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                       }
                     /*  finally
                       {
                          Alert alert = new Alert(AlertType.INFORMATION);
                           alert.setTitle("Information Dialog");
                           alert.setHeaderText(null);
                           alert.setContentText("DONE");

                           alert.showAndWait();/
                       }*/
                   }
               });
               t1.start();
            }
                confirm="";
                msg="";
                txtshop.setText("");
     }
     public  static void setIPText(String txt)
     {
         txtip.setText(txt);
     }
      public  static void setPorText(String txt)
     {
         txtport.setText(txt);
     }
      public static void setArea(String txt)
      {
          txtshop.setText(txt);
      }
      public  static String getPorText()
     {
         return txtport.getText().toString();
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
      private void popup() throws IOException
      {
         Stage stage; 
         Parent root;
         stage = new Stage();
         root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
         stage.setScene(new Scene(root));
         stage.setTitle("Order");
         stage.show();
      }
      
       private void printImage(final BufferedImage image) {
    PrinterJob printJob = PrinterJob.getPrinterJob();
    printJob.setPrintable(new Printable() {
      @Override
      public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        // Get the upper left corner that it printable
        int x = (int) Math.ceil(pageFormat.getImageableX());
        int y = (int) Math.ceil(pageFormat.getImageableY());
        if (pageIndex != 0) {
          return NO_SUCH_PAGE;
        }
        graphics.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
        return PAGE_EXISTS;
      }
    });
    try {
      printJob.print();
    } catch (PrinterException e1) {
      e1.printStackTrace();
    }
  }
       public void pressed() throws ClassNotFoundException, SQLException
       {
           Data d=new Data();
           txtcommoditylist.setText(d.geBarcode_Name());
           txtdevicelist.setText(d.getDevice());
           txtiddevice.setText("");
           txtbarcodecommodity.setText("");
           txtcountcommodity.setText("");
           txtnamecommodity.setText("");
           txtpricecommodity.setText("");
       }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    public static String GetInfo()
	{
		String fileName = "temp.txt";
		String line=null;
	    String info = "";
	    String error=null;
	    try 
	     {
	         // FileReader reads text files in the default encoding.
	         FileReader fileReader = new FileReader(fileName);

	         // Always wrap FileReader in BufferedReader.
	         BufferedReader bufferedReader = new BufferedReader(fileReader);

	         while((line = bufferedReader.readLine()) != null){
	        	 info+=line+"\n";
	         }   

	         // Always close files.
	         bufferedReader.close();         
	     }
	     catch(FileNotFoundException ex1){
	    	// toast = Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
	           error=  "Unable to open file   "+fileName;
	           info=null;
	          //   fileName + "'");                
	     }
	     catch(IOException ex)
	     {
	         error=  "Error reading file "+fileName;
	         info=null;
	         // Or we could just do this: 
	         // ex.printStackTrace();
	         }
	    return info;
	}
	public void SetInfo(String port) throws FileNotFoundException, UnsupportedEncodingException
	{
		String fileName = "temp.txt";
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");

        try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            bufferedWriter.write(port);

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            // Or we could just do this:
            // ex.printStackTrace();
        }
	    }
    
}
