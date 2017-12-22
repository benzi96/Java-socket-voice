/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javasocketvoice.connectdatabase;
import chat.data_socket;
import javasocketvoice.serverprocess;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javasocketvoice.Javasocketvoice;

/**
 *
 * @author pc
 */
public class login extends serverfunction{
    public static data_socket login(String name,String pass, int port){
        String query = "SELECT `ID`,`fullname`,`status` FROM `info_user` WHERE `user_name`= ? AND `pass` = ?";
        ResultSet result = null;
        String[] data = new String[2];
        data_socket dtsk = new data_socket();
        try {
            PreparedStatement prepstmt = connectdatabase.connect.prepareStatement(query);
            prepstmt.setString(1, name);
            prepstmt.setString(2, pass);
            result = prepstmt.executeQuery();
            result.last();
            dtsk.action = "login";
            if(result.getRow() != 0){
                data[0] = "true";
                data[1] = String.valueOf(result.getInt("ID"));
                append_txt("client: "+ port + " Login");
            }else{
                append_txt("client: "+ port +" try to login");
                data[0] = "false";
            }
            dtsk.data = data;
            return dtsk;
        } catch (SQLException ex) {
            Logger.getLogger(serverprocess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtsk;
    }
}
