/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasocketvoice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pc
 */
public class servervoicesend extends Thread{
    DatagramSocket datagramsocket = null;
    byte byte_buff[] = new byte[512];
    
    public servervoicesend(DatagramSocket socket, byte buf[]){
        this.datagramsocket = socket;
        byte_buff = buf;
    }
    @Override
    public void run(){
            try {
                int size=Javasocketvoice.arr_clientvoice.size();
                for(int i = 0; i < size ; i++)
                {
                    InetAddress a = Javasocketvoice.arr_clientvoice.get(i).address;
                    int p = Javasocketvoice.arr_clientvoice.get(i).port;
                    DatagramPacket packet;
                    packet = new DatagramPacket(byte_buff, byte_buff.length, a, p);
                    datagramsocket.send(packet);
                }
                
            } catch (IOException ex) {
                Logger.getLogger(servervoiceprocess.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
