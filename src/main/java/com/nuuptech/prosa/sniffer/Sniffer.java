/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nuuptech.prosa.sniffer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 *
 * @author oscar
 */
public class Sniffer {
    
    private Socket clientSocket;
    //System end of line
    private static String EOL = System.getProperty("line.separator");

    public void getStreamFromClient() throws IOException  {
        ServerSocket server = new ServerSocket(22222);
        clientSocket = server.accept();
        byte[] messageLength = new byte[2];
        int bytesReaded = 0;
        boolean end = false;
        short length = 0;
        ByteBuffer byteBuffer = null;
        DataInputStream in = new DataInputStream(clientSocket.getInputStream());
        System.out.print("Reading length bytes ");
        while(!end) {
            System.out.print(".");
            if(bytesReaded < 2) {
                bytesReaded += in.read(messageLength);
            } else if (bytesReaded == 2) {
                byteBuffer = ByteBuffer.wrap(messageLength); // big-endian by default
                length = byteBuffer.getShort();
                end = true;
            }
        }
        System.out.println(EOL + "Lenght in bytes is: ");
        for (byte b : messageLength) {
            System.out.format("0x%x ", b);
        }
        System.out.println(EOL + "Message length is " + length);
        byte[] message = new byte[length];
        end  = false;
        System.out.print("Reading message bytes ");
        while(!end) {
            System.out.print(".");
            bytesReaded += in.read(message);
            if(bytesReaded == length) {
                end = true;
            }
        }
        System.out.println(EOL + "Message in bytes is: ");
        for (byte b : message) {
            System.out.format("0x%x ", b);
        }
    }
}