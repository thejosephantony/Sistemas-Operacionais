/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.main;

/**
 *
 * @author Joseph
 */
class Contador {
    int valor = 0;

    synchronized void incrementar() {
        valor++;
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Contador c = new Contador();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                c.incrementar();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                c.incrementar();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(c.valor);
    }
}