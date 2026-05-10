/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.barbeiro;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Joseph
 */

public class Barbeiro implements Runnable {

    private final int idBarbeiro;
    private final Barbearia barbearia;

    public Barbeiro(int idBarbeiro, Barbearia barbearia) {
        this.idBarbeiro = idBarbeiro;
        this.barbearia = barbearia;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.printf(
                    "Barbeiro %d está dormindo/esperando cliente.%n",
                    idBarbeiro
                );

                Cliente cliente = barbearia.chamarProximoCliente();

                int tempoCorte = ThreadLocalRandom.current().nextInt(5, 16);

                System.out.printf(
                    "Barbeiro %d começou a cortar o cabelo do cliente %d. Tempo estimado: %d segundos.%n",
                    idBarbeiro,
                    cliente.getIdCliente(),
                    tempoCorte
                );

                Thread.sleep(tempoCorte * 1000L);

                System.out.printf(
                    "Barbeiro %d terminou o corte do cliente %d.%n",
                    idBarbeiro,
                    cliente.getIdCliente()
                );

                cliente.finalizarCorte();
            }
        } catch (InterruptedException e) {
            System.out.printf(
                "Barbeiro %d encerrou o expediente.%n",
                idBarbeiro
            );
        }
    }
}