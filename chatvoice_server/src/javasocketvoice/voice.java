/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasocketvoice;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author pc
 */
public class voice {
    static Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
    public static AudioFormat getAudioFormat(){
   
        float sampleRate = 8000.0F;

        int sampleSizeInBits = 16;

        int channels = 2;

        boolean signed = true;

        boolean bigEndian = false;

        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,bigEndian);
    }
    public SourceDataLine audio_out;
    public TargetDataLine audio_in;
    public void init_audio(){
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info_in = new DataLine.Info(TargetDataLine.class, format);
            DataLine.Info info_out = new DataLine.Info(SourceDataLine.class, format);
            if (!AudioSystem.isLineSupported(info_in)) {
                System.out.println("Line for in not supported");
                System.exit(0);
            }
            if(!AudioSystem.isLineSupported(info_out)){
                System.out.println("Line for out not supported");
                System.exit(0);
            }
            audio_out = (SourceDataLine)AudioSystem.getLine(info_out);
            audio_out.open(format);
            audio_out.start();
            
            audio_in = (TargetDataLine) AudioSystem.getLine(info_in);
            audio_in.open(format);
            audio_in.start();            
        } catch (LineUnavailableException ex) {
            Logger.getLogger(voice.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
