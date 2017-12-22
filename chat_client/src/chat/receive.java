package chat;

import UI.fr_chat;
import java.awt.Frame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author duong
 */
public class receive  extends Thread{
    Socket socket = null;
    ObjectInputStream din = null;
    data_socket respon = null;
    Clip clip;
    public receive(Socket sk){
        this.socket = sk;
    }
    @Override
    public void run(){
        try {
            din = new ObjectInputStream(this.socket.getInputStream());
            while(true){
                respon = (data_socket)din.readObject();
                if(respon != null)
                {
                switch(respon.action){
                    case "login"             : this.check_login(); break;
                    case "chat"              :this.receive_msg(respon.data);break;
                    case "reg"               : this.respon_reg(respon);break;
                    default                  : System.out.println("unknow action");
                }
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.toString());
            System.out.println("Mất kết nối");
            JOptionPane.showMessageDialog(null, "Mất kết nối máy chủ, trương trình sẽ tự thoát");
            Logger.getLogger(receive.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);   
        }
    }
    
    public void receive_msg(String[] data){
        ImageIcon icon = null;
        int online = 0;
        
        Chat_client.fr_chat.fr_ID = Integer.valueOf(data[0]);
        
        if(data[3].equalsIgnoreCase("buzz")){
            Chat_client.fr_chat.vibrate();
            Chat_client.fr_chat.clip = this.playsound("Nudge.wav");
            Chat_client.fr_chat.clip.start();
            return;
        }
        Chat_client.fr_chat.cus_append2(data[3], 2, data[2]);
        Chat_client.fr_chat.clip = this.playsound("msg.wav");
        Chat_client.fr_chat.clip.start();
        
    }    
    public Clip playsound(String path){
        String file_path = "sound/"+path;
        try {
            URL yourFile = getClass().getClassLoader().getResource(file_path);
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            
            stream = AudioSystem.getAudioInputStream(yourFile);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            return clip;
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            Logger.getLogger(fr_chat.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    public void check_login(){
        if(respon.data[0].equalsIgnoreCase("true")){
            Chat_client.fr_login.setVisible(false);
            Chat_client.fr_chat.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(null, "username or password is not correct");
        }
    }
    public void respon_reg(data_socket respon){
        if(respon.data[0].equalsIgnoreCase("true")){
            JOptionPane.showMessageDialog(null, "Đăng ký thành công");
            Chat_client.fr_reg.setVisible(false);
            Chat_client.fr_login.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "Tên người dùng đã tồn tại");
        }
    }
}
