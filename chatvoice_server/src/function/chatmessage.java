/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import chat.data_socket;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javasocketvoice.Javasocketvoice;
import javasocketvoice.connectdatabase;
import javasocketvoice.serverprocess;

/**
 *
 * @author pc
 */
public class chatmessage {
    
    //luu tin nhan len database
    public static void chat(String[] data){
        int size = Javasocketvoice.arr_client.size();
        data_socket dtsk = new data_socket();
        dtsk.action = "chat";
        dtsk.data = data;
        
        for(int i = 0; i < size; i ++){
            Javasocketvoice.arr_client.get(i).sendtoclient(dtsk);
        }
    }
}
