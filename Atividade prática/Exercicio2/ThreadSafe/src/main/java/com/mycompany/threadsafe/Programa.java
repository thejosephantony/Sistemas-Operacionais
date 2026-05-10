/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.threadsafe;

/**
 *
 * @author Joseph
 */
public class Programa {

    public static void main(String[] args) throws InterruptedException {
        ThreadSafe<Integer> lista = new ThreadSafe<>();

        Thread inseridor1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                lista.add(i);
            }
        });

        Thread inseridor2 = new Thread(() -> {
            for (int i = 1000; i < 2000; i++) {
                lista.add(i);
            }
        });

        Thread leitor = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Tamanho atual: " + lista.size());

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("Leitor interrompido.");
                }
            }
        });

        inseridor1.start();
        inseridor2.start();
        leitor.start();

        inseridor1.join();
        inseridor2.join();
        leitor.join();

        System.out.println("Tamanho final: " + lista.size());
    }
}