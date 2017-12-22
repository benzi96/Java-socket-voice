/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasocketvoice;

import UI.serverlogfr;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author pc
 */
public class serverconnect extends Thread{
    public int portnumber = 8012;
    public ServerSocket server_socket = null;
    public Socket socket = null;
    public DatagramSocket datagramsocket = null;
    
    @Override
    public void run(){
        try {
            this.server_socket = new ServerSocket(portnumber);
            datagramsocket = new DatagramSocket(portnumber);
            voice v=new voice();
            v.init_audio();
            servervoiceprocess svp = new servervoiceprocess(datagramsocket, v);
            svp.start();
            //servervoiceprocess svp = new servervoiceprocess(datagramsocket);
            //svp.start();
            servervoicechat svc = new servervoicechat(datagramsocket, v);
            svc.start();
            serverlogfr.txt_log.append("server running at port " +this.portnumber+ " \n");
            serverlogfr.startserverbtn.setEnabled(false);
        } catch (IOException ex) {
            Logger.getLogger(serverconnect.class.getName()).log(Level.SEVERE, null, ex);
            serverlogfr.txt_log.append("server error \n");
            return;
        }
        try {
            while(true){
            try {
                this.socket = server_socket.accept();
                serverlogfr.txt_log.append("new client: "+this.socket.getPort()+"\n");
                serverprocess sp = new serverprocess(this.socket);
                sp.start();
            }catch (IOException ex) {
                    Logger.getLogger(serverconnect.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SecurityException ex) {
            Logger.getLogger(serverconnect.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}
