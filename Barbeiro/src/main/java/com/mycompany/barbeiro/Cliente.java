/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.barbeiro;

import java.util.concurrent.Semaphore;
/**
 *
 * @author Joseph
 */
public class Cliente implements Runnable {

    private final int idCliente;
    private final Barbearia barbearia;
    private final Semaphore corteFinalizado = new Semaphore(0);

    public Cliente(int idCliente, Barbearia barbearia) {
        this.idCliente = idCliente;
        this.barbearia = barbearia;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void finalizarCorte() {
        corteFinalizado.release();
    }

    @Override
    public void run() {
        try {
            System.out.printf(
                "Cliente %d chegou na barbearia.%n",
                idCliente
            );

            boolean conseguiuEntrar = barbearia.receberCliente(this);

            if (!conseguiuEntrar) {
                return;
            }

            corteFinalizado.acquire();

            System.out.printf(
                "Cliente %d saiu da barbearia.%n",
                idCliente
            );

        } catch (InterruptedException e) {
            System.out.printf(
                "Cliente %d foi interrompido.%n",
                idCliente
            );
        }
    }
}