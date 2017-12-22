/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasocketvoice;

import UI.serverlogfr;
import java.util.ArrayList;

/**
 *
 * @author pc
 */
public class Javasocketvoice {
    public static serverlogfr svf = null;
    public static ArrayList<client> arr_client = new ArrayList<>();
    public static ArrayList<clientvoice> arr_clientvoice = new ArrayList<>();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        connectdatabase.connectdatabasemysql();
        svf = new serverlogfr();
        svf.setVisible(true);
    }
    
}
