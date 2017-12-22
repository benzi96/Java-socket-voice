/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasocketvoice;

import function.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import chat.data_socket;

/**
 *
 * @author pc
 */
public class serverprocess extends Thread{
    private Socket socket = null;
    private int ID;
    private int int_status;
    ObjectInputStream in = null;
    ObjectOutputStream out = null;
    Boolean is_running = true;
    public serverprocess(Socket socket){
        this.socket = socket;
        try {
            this.out = new ObjectOutputStream(this.socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(serverprocess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run(){
        data_socket msg = null;
        data_socket data = new data_socket();
        data_socket dtsk = new data_socket();
        try {
            while(is_running){
                    in  = new ObjectInputStream(this.socket.getInputStream());
                    msg = (data_socket) in.readObject();
                    System.out.println(msg.action);
                    switch(msg.action){
                        case "login":              
                        {
                            dtsk = login.login(msg.data[0], msg.data[1], this.socket.getPort());
                            this.user_login(Integer.parseInt(dtsk.data[1]));
                            this.int_status = 1;
                            if(data.action != null) this.sendtoclient(data);
                            data.action = null;
                            if(dtsk.action != null) this.sendtoclient(dtsk);
                            break;
                        } 
                        case "logout":            this.user_disconnect(); break;
                        
                        case "chat":              chatmessage.chat(msg.data); break;
                        
                        case "reg":               data = register.reg(msg.data[0], msg.data[1], msg.data[2]); break;
                        
                        default: System.out.println("unknow action");
                    }
                    if(data.action != null) this.sendtoclient(data);
            }
        } catch (IOException | ClassNotFoundException ex) {
            this.user_disconnect();
            System.out.println("user disconnect 1");
            Logger.getLogger(serverprocess.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
                this.user_disconnect();
                System.out.println("user disconnect 2");
            } catch (IOException ex) {
                Logger.getLogger(serverprocess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //gui du lieu toi client
    private void sendtoclient(data_socket dtsk){
        try {
            out.writeObject(dtsk);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(serverprocess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void user_login(int ID){
        int size = Javasocketvoice.arr_client.size();
        for(int i = 0; i < size ; i++){
            if(Javasocketvoice.arr_client.get(i).ID == ID){
                Javasocketvoice.arr_client.get(i).dout = out;
                // trường hợp đăng nhập ở nhiều máy
                return;
            }
        }
        this.int_status = 1; // 1 = online
        client cl = new client(socket, ID);
        cl.dout = out;
        cl.int_status = 1;
        Javasocketvoice.arr_client.add(cl);
    }
    
    public void user_disconnect(){
        this.int_status = 2; // 2 = offline
        for(int i = 0; i< Javasocketvoice.arr_client.size();i++){
            if(Javasocketvoice.arr_client.get(i).ID == this.ID){
                Javasocketvoice.arr_client.remove(i); // remove form online list
            }
        }
        this.is_running = false; // terminal thread
    }
    
}
