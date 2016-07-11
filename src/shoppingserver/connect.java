/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoppingserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Farid omar
 */
public class connect
    {
        private static connect _instance;
        private String url;
        private String driver;
        Connection _connection;

        // Constructor is 'protected'
        protected connect() throws ClassNotFoundException, SQLException
        {
            driver="com.mysql.jdbc.Driver";
            Class.forName(driver);
            url="jdbc:mysql://localhost/Shop";
            _connection= DriverManager.getConnection(url, "root", "froal1372");
        }
        public Connection getConnection()
        {
            return this._connection;
        }
      /*  public void  open()
        {
            _connection.
        }**/
        public void close() throws SQLException
        {
            _connection.close();
        }

        public static connect Instance() throws ClassNotFoundException, SQLException
        {
            // Uses lazy initialization.
            // Note: this is not thread safe.
            if (_instance == null)
            {
                _instance = new connect();
            }

            return _instance;
        }
    }
