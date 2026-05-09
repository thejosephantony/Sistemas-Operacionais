/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.threads;

/**
 *
 * @author Joseph
 */
public class Programa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MinhaThread t1 = new MinhaThread("Thread A - ");
        MinhaThread t2 = new MinhaThread("Thread B - ");
        
        
        t1.start();
        t2.start();
    }
    
}
