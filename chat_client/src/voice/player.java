/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voice;

import chat.Chat_client;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author duong
 */
public class player extends Thread{
    public DatagramSocket din;
    public SourceDataLine audio_out;
    byte[] buffer = new byte[512];
        @Override
        public void run(){
        try {
            
            System.out.println("Server socket created. Waiting for incoming data...");
            while(Chat_client.calling){
                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
                din.receive(incoming);
                buffer = incoming.getData();
                audio_out.write(buffer, 0, buffer.length);
            }
            
            System.out.println("call in player: player is stop");
            audio_out.drain();
            audio_out.close();
            System.out.println("call in player: audio is drain and close");
        } catch (SocketException ex) {
            Logger.getLogger(player.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(player.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
}
