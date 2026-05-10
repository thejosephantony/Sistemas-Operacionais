/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.barbeiro;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;



/**
 *
 * @author Joseph
 */
public class Programa {

    public static void main(String[] args) throws InterruptedException {
        Barbearia barbearia = new Barbearia(10);

        Thread barbeiro1 = new Thread(new Barbeiro(1, barbearia));
        Thread barbeiro2 = new Thread(new Barbeiro(2, barbearia));

        barbeiro1.start();
        barbeiro2.start();

        Thread[] clientes = new Thread[10];

        for (int i = 0; i < clientes.length; i++) {
            clientes[i] = new Thread(new Cliente(i + 1, barbearia));
            clientes[i].start();

            int tempoChegada = ThreadLocalRandom.current().nextInt(4, 7);

            System.out.printf(
                "Próximo cliente chegará em %d segundos.%n",
                tempoChegada
            );

            Thread.sleep(tempoChegada * 1000L);
        }

        for (Thread cliente : clientes) {
            cliente.join();
        }

        barbeiro1.interrupt();
        barbeiro2.interrupt();

        barbeiro1.join();
        barbeiro2.join();

        System.out.println("Barbearia fechou.");
    }
}