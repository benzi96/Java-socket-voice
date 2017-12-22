/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasocketvoice;

import java.net.InetAddress;

/**
 *
 * @author pc
 */
public class clientvoice {
    InetAddress address;
    int port;
    public clientvoice(InetAddress a, int p)
    {
        address = a;
        port = p;
    }
}
