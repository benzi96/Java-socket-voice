/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import UI.serverlogfr;

/**
 *
 * @author pc
 */
public class serverfunction {
    public static void append_txt(String msg){
        serverlogfr.txt_log.append(msg+"\n");
        serverlogfr.txt_log.setCaretPosition(serverlogfr.txt_log.getDocument().getLength());
    }
}
