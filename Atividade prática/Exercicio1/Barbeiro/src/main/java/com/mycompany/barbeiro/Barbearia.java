/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.barbeiro;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Joseph
 */

public class Barbearia {

    private final BlockingQueue<Cliente> fila;
    private final int capacidade;

    public Barbearia(int capacidade) {
        this.capacidade = capacidade;
        this.fila = new ArrayBlockingQueue<>(capacidade);
    }

    public boolean receberCliente(Cliente cliente) {
        boolean entrou = fila.offer(cliente);

        if (entrou) {
            System.out.printf(
                "Cliente %d entrou na fila. Clientes esperando: %d/%d%n",
                cliente.getIdCliente(),
                fila.size(),
                capacidade
            );
        } else {
            System.out.printf(
                "Cliente %d foi embora. Fila cheia!%n",
                cliente.getIdCliente()
            );
        }

        return entrou;
    }

    public Cliente chamarProximoCliente() throws InterruptedException {
        return fila.take();
    }
}