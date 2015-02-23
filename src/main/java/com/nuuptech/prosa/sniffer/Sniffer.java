/**
* Copyright 2015 Nuuptech
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
**/
package com.nuuptech.prosa.sniffer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Reads byte array from socket
 * @author <a href="mailto:oscar.castillo@nuuptech.com">Oscar Castillo</a> 
 */
public class Sniffer {
    
    //Socket
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
        int indexReaded = 0;
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
        System.out.println(EOL + "Reading message bytes ");
        bytesReaded = 0;
        while(!end) {
            bytesReaded += in.read(message);
            System.out.println("bytesReaded = " + bytesReaded);
            while (indexReaded < bytesReaded) {
                System.out.print(EOL + "Byte" + indexReaded + " is : ");
                System.out.format("0x%x ", message[indexReaded]);
                indexReaded++;
            }
            if(bytesReaded == length) {
                end = true;
            }
        }
        System.out.println(EOL + "Done.");
    }
}