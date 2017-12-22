/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasocketvoice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author pc
 */
public class connectdatabase {
    public static Connection connect = null; 
    private static final String hostname = "localhost";
    private static final String databasename = "test";
    private static final String databaseusername = "root";
    private static final String databasepassword = "";
    public static void connectdatabasemysql()
    {
        try{  
            Class.forName("com.mysql.jdbc.Driver");
            connectdatabase.connect = DriverManager.getConnection("jdbc:mysql://"+ hostname +":3306/"+ databasename +"?useUnicode=true&characterEncoding=UTF-8",databaseusername,databasepassword);
                System.out.println("connected mysql");
            }catch(ClassNotFoundException | SQLException e){ 
                JOptionPane.showMessageDialog(null, "database notfound");
                return;
        }
    }
}
