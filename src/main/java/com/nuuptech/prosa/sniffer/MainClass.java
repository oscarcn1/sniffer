/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nuuptech.prosa.sniffer;

/**
 *
 * @author oscar
 */
public class MainClass {
    
    public static void main(String[] args) {
        try{
            Sniffer sniffer = new Sniffer();
            sniffer.getStreamFromClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
