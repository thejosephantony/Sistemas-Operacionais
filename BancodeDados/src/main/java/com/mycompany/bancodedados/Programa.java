/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bancodedados;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 *
 * @author Joseph
 */
public class Programa {

    public static void main(String[] args) throws InterruptedException {
        BancodeDados banco = new BancodeDados();

        System.out.println("Inicializando banco de dados...");

        for (int i = 1; i <= 5; i++) {
            banco.create(i, "Registro " + i);
        }

        System.out.println();
        System.out.println("=== INICIANDO TESTE COM CONSULTAS E ESCRITAS ===");
        System.out.println();

        List<Thread> threads = new ArrayList<>();

        for (int i = 1; i <= 15; i++) {
            Thread consulta = new Thread(() -> {
                try {
                    int id = ThreadLocalRandom.current().nextInt(1, 6);
                    banco.read(id);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Consulta-" + i);

            threads.add(consulta);
            consulta.start();
        }

        Thread.sleep(500);

        Thread update = new Thread(() -> {
            try {
                banco.update(3, "Registro 3 atualizado");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Update");

        threads.add(update);
        update.start();

        Thread.sleep(500);

        for (int i = 16; i <= 25; i++) {
            Thread consulta = new Thread(() -> {
                try {
                    int id = ThreadLocalRandom.current().nextInt(1, 6);
                    banco.read(id);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Consulta-" + i);

            threads.add(consulta);
            consulta.start();
        }

        Thread create = new Thread(() -> {
            try {
                banco.create(6, "Registro 6");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Create");

        Thread delete = new Thread(() -> {
            try {
                banco.delete(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Delete");

        threads.add(create);
        threads.add(delete);

        create.start();
        delete.start();

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println();
        System.out.println("=== TESTE FINALIZADO ===");
    }
}