/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasocketvoice;

import chat.data_socket;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author duong
 */
public class client {
    public Socket socket = null;
    public int ID;
    ObjectOutputStream dout = null;
    public int int_status;

    public client(Socket sk,int id) {
        this.socket = sk;
        this.ID = id;
    }
    
    public void sendtoclient(data_socket data)
    {
        try {
            dout.writeObject(data);
        } catch (IOException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
