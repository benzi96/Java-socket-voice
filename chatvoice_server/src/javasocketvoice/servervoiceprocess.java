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
public class servervoiceprocess extends Thread{
    DatagramSocket datagramsocket = null;
    Boolean is_running = true;
    voice v=null;
    public servervoiceprocess(DatagramSocket socket, voice v){
        this.datagramsocket = socket;
        this.v=v;
    }
    public servervoiceprocess(DatagramSocket socket){
        this.datagramsocket = socket;
    }
    @Override
    public void run(){
        
        while(is_running){
            try {
                byte[] buf = new byte[512];
                
                //nhan voice tu client
                DatagramPacket receivedpacket = new DatagramPacket(buf, buf.length);
                datagramsocket.receive(receivedpacket);
                
                InetAddress address = receivedpacket.getAddress();
                int port = receivedpacket.getPort();
                
                addcv(address, port);
                //System.out.println(port + " " + address.toString());
                //gui du lieu toi clients
                buf = receivedpacket.getData();
                v.audio_out.write(buf, 0, buf.length);
                
                int size=Javasocketvoice.arr_clientvoice.size();
                for(int i = 0; i < size ; i++)
                {
                    if(Javasocketvoice.arr_clientvoice.get(i).port != port)
                    {
                    InetAddress a = Javasocketvoice.arr_clientvoice.get(i).address;
                    int p = Javasocketvoice.arr_clientvoice.get(i).port;
                    DatagramPacket packet;
                    packet = new DatagramPacket(buf, buf.length, a, p);
                    datagramsocket.send(packet);
                    }
                }
                
                //servervoicesend svs=new servervoicesend(datagramsocket, buf);
                //svs.start();
            } catch (IOException ex) {
                Logger.getLogger(servervoiceprocess.class.getName()).log(Level.SEVERE, null, ex);
            }
 
        }
    }
    
    public void addcv(InetAddress a, int p)
    {
    int size=Javasocketvoice.arr_clientvoice.size();
    if(size < 10)
    {
        int dem = 0;
        for(int i = 0; i < size ; i++)
        {
            if(Javasocketvoice.arr_clientvoice.get(i).port == p) 
            {
                dem++;
                break;
            }
        }

        if(dem==0)
        {
            clientvoice cv = new clientvoice(a, p);
            Javasocketvoice.arr_clientvoice.add(cv);
        }
    }   
    }
}
