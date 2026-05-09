/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.thread;

/**
 *
 * @author Joseph
 */
public class Programa {

    public static void main(String[] args) {
        MinhaThreadRunnable t1 = new MinhaThreadRunnable("Thread A - ");
        MinhaThreadRunnable t2 = new MinhaThreadRunnable("Thread B - ");
        MinhaThreadRunnable t3 = new MinhaThreadRunnable("Thread C - ");
        
        new Thread(t1).start();
        new Thread(t2).start();
        new Thread(t3).start();
    }
}
