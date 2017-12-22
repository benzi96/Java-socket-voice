/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javasocketvoice.connectdatabase;
import chat.data_socket;
import javasocketvoice.serverprocess;

/**
 *
 * @author pc
 */
public class register {
    public static data_socket reg(String username,String full_name,String pass){
        String[] data = new String[1];
        data_socket dtsk = new data_socket();
        dtsk.action = "reg";
        String query_check = "SELECT `user_name` FROM `info_user` WHERE `user_name` = ?";
        String query = "INSERT INTO `info_user` (`ID`, `user_name`, `pass`, `fullname`, `status`) VALUES (NULL, ?, ?, ?, 'OFFLINE')";
        try {
            PreparedStatement preparedStatement1 = connectdatabase.connect.prepareStatement(query_check);
            preparedStatement1.setString(1, username);
            System.out.println(preparedStatement1.toString());
            ResultSet rs = preparedStatement1.executeQuery();
            rs.last();
            if(rs.getRow() != 0){
                //user_name exist;
                data[0] = "false";
                dtsk.data = data;
                return dtsk;
            }
            PreparedStatement preparedStatement2 = connectdatabase.connect.prepareStatement(query);
            preparedStatement2.setString(1, username);
            preparedStatement2.setString(2, pass);
            preparedStatement2.setString(3, full_name);
            preparedStatement2.executeUpdate();
            data[0] = "true";
            dtsk.data = data;
            
        } catch (SQLException ex) {
            Logger.getLogger(serverprocess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtsk;
    }
}
