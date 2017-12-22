/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import UI.fr_chat;
import UI.fr_reg;
import UI.fr_Login;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 *
 * @author duong
 */
public class Chat_client {
    public static int socket_port = 8012;
    public static String ip_server = "localhost";
    //public static String ip_server = "185.118.165.242";
    //public static String ip_server = "27.3.125.216";
    public static Socket socket = null;
    public static boolean login = false;
    public static int my_ID;
    public static ImageIcon icon = null;
    public static String full_name = null;
    public static boolean connected = false;
    public static fr_Login fr_login = null;
    public static fr_chat fr_chat = null;
    public static fr_reg fr_reg = null;
    public static boolean calling = false;
    
    public static void main(String[] args) {
        Chat_client chat_client = new Chat_client();
        chat_client.init();
    }
    public void init(){
        Chat_client.fr_login = new fr_Login();
        Chat_client.fr_chat = new fr_chat();
        
        Chat_client.fr_login.setVisible(true);
        try {
            Chat_client.socket = new Socket(Chat_client.ip_server, Chat_client.socket_port);
            Thread receive = new receive(Chat_client.socket);
            receive.start();
            Chat_client.connected = true;
            System.out.println("conected");
        } catch (IOException ex) {
            Logger.getLogger(fr_Login.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "socket error");
        }
        
    }
}
