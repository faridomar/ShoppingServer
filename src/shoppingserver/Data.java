/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoppingserver;

import java.util.Calendar;
import java.sql.*;

/**
 *
 * @author Farid omar
 */
public class Data {
    public void InsertDevice(String id) throws ClassNotFoundException, SQLException
    {
        Connection con=connect.Instance().getConnection();
        String Query="insert into Device(ID) values("+id+")";
        PreparedStatement p=con.prepareStatement(Query);
        p.execute();
    }
    public void InsertCommodity(String barcode,String name,float price,int count) throws ClassNotFoundException, SQLException
    {
         Connection con=connect.Instance().getConnection();
         String Query="insert into Commodity (Barcode,Name,Price,Count) values('"+barcode+"','"+name+"',"+price+","+count+")";
         PreparedStatement p=con.prepareStatement(Query);
        p.execute();
    }
     public void UpdateCommodity(String barcode,String name,float price,int count) throws ClassNotFoundException, SQLException
    {
         Connection con=connect.Instance().getConnection();
         String Query="update Commodity set Name='"+name+"',Price="+price+",Count="+count+" where Barcode='"+barcode+"'";
         PreparedStatement p=con.prepareStatement(Query);
        p.execute();
    }
     public void DeleteCommodity(String barcode) throws ClassNotFoundException, SQLException
    {
         Connection con=connect.Instance().getConnection();
         String Query="Delete from Commodity where Barcode='"+barcode+"'";
         PreparedStatement p=con.prepareStatement(Query);
        p.execute();
    }
      public void DeleteDevice(String id) throws ClassNotFoundException, SQLException
    {
         Connection con=connect.Instance().getConnection();
         String Query="Delete from Device where ID='"+id+"'";
         PreparedStatement p=con.prepareStatement(Query);
        p.execute();
    }
    public void InsertDC(String id,String barcode,String date,int count) throws ClassNotFoundException, SQLException
    {
         Connection con=connect.Instance().getConnection();
         String Query="insert into DC(ID,Barcode,Date,Count) values('"+id+"','"+barcode+"','"+date+"',"+ count+")";
         PreparedStatement p=con.prepareStatement(Query);
        p.execute();
    }
    public String getPrice(String barcode) throws ClassNotFoundException, SQLException
    {
        Connection con=connect.Instance().getConnection();
        String query = "SELECT Price FROM Commodity where Barcode='"+barcode+"'";
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(query);
          String _Price="";
          while (rs.next())
          {
              _Price=rs.getString("Price");
          }
          return _Price;
    }
     public String getName(String barcode) throws ClassNotFoundException, SQLException
    {
        Connection con=connect.Instance().getConnection();
        String query = "SELECT Name FROM Commodity where Barcode='"+barcode+"'";
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(query);
          String _Name="";
          while (rs.next())
          {
              _Name=rs.getString("Name");
          }
          return _Name;
    }
     public String getDevice() throws ClassNotFoundException, SQLException
     {
         Connection con=connect.Instance().getConnection();
         String query = "SELECT * FROM Device";
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(query);
         String _id="";
          while (rs.next())
          {
              _id+=rs.getString("ID")+"\n";
          }
          System.out.println(_id+"...");
          return _id;
     }
     public String[] getCommodity(String barcode) throws SQLException, ClassNotFoundException
     {
         Connection con=connect.Instance().getConnection();
         String query = "SELECT * FROM Commodity where Barcode='"+barcode+"'";
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(query);
         String[] _return=new String[4];
          while (rs.next())
          {
              _return[0]=rs.getString("Barcode");
              _return[1]=rs.getString("Name");
              _return[2]=rs.getString("Price");
              _return[3]=rs.getString("Count");
          }
          return _return;
     }
      public String geBarcode_Name() throws ClassNotFoundException, SQLException
     {
         Connection con=connect.Instance().getConnection();
         String query = "SELECT * FROM Commodity";
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(query);
         String _return="";
          while (rs.next())
          {
              _return+=rs.getString("Name")+"   "+rs.getString("Barcode")+"\n";
          }
          return _return;
     }
     public void setCount(int count,String barcode) throws ClassNotFoundException, SQLException
     {
         Connection con=connect.Instance().getConnection();
        String query = "SELECT Count FROM Commodity where Barcode='"+barcode+"'";
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(query);
         int _Count=0;
         while (rs.next())
          {
              _Count=rs.getInt("Count");
          }
         _Count-=count;
         String query2="update Commodity set Count="+_Count+" where Barcode ='"+barcode+"'";
        PreparedStatement p=con.prepareStatement(query2);
        p.execute();
     }
    /* public String getdevicebuy(String id) throws ClassNotFoundException, SQLException
     {
         Connection con=connect.Instance().getConnection();
         String query = "SELECT Barcode FROM DC where ID='"+id+"'";
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(query);
         String _barcode="";
         String _return="";
          while (rs.next())
          {
              _barcode=rs.getString("Barcode");
              
          }
     }*/
}
