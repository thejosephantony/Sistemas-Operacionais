/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bancodedados;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Joseph
 */

public class BancodeDados {

    private final Map<Integer, String> dados = new HashMap<>();

    private final Semaphore limiteConsultas = new Semaphore(10, true);

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    private int consultasAtivas = 0;
    private int escritasAtivas = 0;

    public void create(int id, String valor) throws InterruptedException {
        lock.writeLock().lock();

        try {
            escritasAtivas++;

            System.out.printf(
                    "[CREATE] INICIO | id=%d | escritasAtivas=%d | consultasAtivas=%d%n",
                    id,
                    escritasAtivas,
                    consultasAtivas
            );

            simularTempoDeOperacao(1000, 2500);

            dados.put(id, valor);

            System.out.printf(
                    "[CREATE] FIM    | id=%d | valor=%s%n",
                    id,
                    valor
            );

        } finally {
            escritasAtivas--;
            lock.writeLock().unlock();
        }
    }

    public String read(int id) throws InterruptedException {
        System.out.printf(
                "[READ] Cliente tentando consultar id=%d%n",
                id
        );

        limiteConsultas.acquire();

        lock.readLock().lock();

        try {
            consultasAtivas++;

            System.out.printf(
                    "[READ] INICIO  | id=%d | consultasAtivas=%d/10 | escritasAtivas=%d%n",
                    id,
                    consultasAtivas,
                    escritasAtivas
            );

            simularTempoDeOperacao(2000, 4000);

            String valor = dados.get(id);

            System.out.printf(
                    "[READ] FIM     | id=%d | resultado=%s | consultasAtivas=%d/10%n",
                    id,
                    valor,
                    consultasAtivas
            );

            return valor;

        } finally {
            consultasAtivas--;
            lock.readLock().unlock();
            limiteConsultas.release();
        }
    }

    public void update(int id, String novoValor) throws InterruptedException {
        lock.writeLock().lock();

        try {
            escritasAtivas++;

            System.out.printf(
                    "[UPDATE] INICIO | id=%d | escritasAtivas=%d | consultasAtivas=%d%n",
                    id,
                    escritasAtivas,
                    consultasAtivas
            );

            simularTempoDeOperacao(1000, 2500);

            if (dados.containsKey(id)) {
                dados.put(id, novoValor);

                System.out.printf(
                        "[UPDATE] FIM    | id=%d | novoValor=%s%n",
                        id,
                        novoValor
                );
            } else {
                System.out.printf(
                        "[UPDATE] FIM    | id=%d nao encontrado%n",
                        id
                );
            }

        } finally {
            escritasAtivas--;
            lock.writeLock().unlock();
        }
    }

    public void delete(int id) throws InterruptedException {
        lock.writeLock().lock();

        try {
            escritasAtivas++;

            System.out.printf(
                    "[DELETE] INICIO | id=%d | escritasAtivas=%d | consultasAtivas=%d%n",
                    id,
                    escritasAtivas,
                    consultasAtivas
            );

            simularTempoDeOperacao(1000, 2500);

            String removido = dados.remove(id);

            System.out.printf(
                    "[DELETE] FIM    | id=%d | removido=%s%n",
                    id,
                    removido
            );

        } finally {
            escritasAtivas--;
            lock.writeLock().unlock();
        }
    }

    private void simularTempoDeOperacao(int minimoMs, int maximoMs) throws InterruptedException {
        int tempo = ThreadLocalRandom.current().nextInt(minimoMs, maximoMs + 1);
        Thread.sleep(tempo);
    }
}