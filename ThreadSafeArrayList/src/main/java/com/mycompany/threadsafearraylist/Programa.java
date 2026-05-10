/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.threadsafearraylist;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;


/**
 *
 * @author Joseph
 */
public class Programa {

    private static final int[] TAMANHOS = {1600, 8000, 16000};
    private static final int THREADS = 16;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== TESTE COM 1 THREAD ===");
        System.out.println("Comparando ArrayList original com ThreadSafeArrayList");
        System.out.println();

        for (int tamanho : TAMANHOS) {
            testarArrayListUmaThread(tamanho);
            testarThreadSafeUmaThread(tamanho);
            System.out.println();
        }

        System.out.println();
        System.out.println("=== TESTE COM 16 THREADS ===");
        System.out.println("Comparando ThreadSafeArrayList com Vector");
        System.out.println();

        for (int tamanho : TAMANHOS) {
            testarThreadSafeDezesseisThreads(tamanho);
            testarVectorDezesseisThreads(tamanho);
            System.out.println();
        }
    }

    private static void testarArrayListUmaThread(int tamanho) {
        ArrayList<Integer> listaInsercao = new ArrayList<>();

        long tempoInsercao = medirTempo(() -> {
            for (int i = 0; i < tamanho; i++) {
                listaInsercao.add(i);
            }
        });

        imprimirResultado("ArrayList", "insercao", 1, tamanho, tamanho, tempoInsercao);

        ArrayList<Integer> listaBusca = criarArrayListPreenchido(tamanho);

        long tempoBusca = medirTempo(() -> {
            for (int i = 0; i < tamanho; i++) {
                int valor = ThreadLocalRandom.current().nextInt(tamanho);
                listaBusca.contains(valor);
            }
        });

        imprimirResultado("ArrayList", "busca", 1, tamanho, tamanho, tempoBusca);

        ArrayList<Integer> listaRemocao = criarArrayListPreenchido(tamanho);

        long tempoRemocao = medirTempo(() -> {
            for (int i = 0; i < tamanho; i++) {
                int indice = ThreadLocalRandom.current().nextInt(listaRemocao.size());
                listaRemocao.remove(indice);
            }
        });

        imprimirResultado("ArrayList", "remocao", 1, tamanho, tamanho, tempoRemocao);
    }

    private static void testarThreadSafeUmaThread(int tamanho) {
        ThreadSafeArrayList<Integer> listaInsercao = new ThreadSafeArrayList<>();

        long tempoInsercao = medirTempo(() -> {
            for (int i = 0; i < tamanho; i++) {
                listaInsercao.add(i);
            }
        });

        imprimirResultado("ThreadSafeArrayList", "insercao", 1, tamanho, tamanho, tempoInsercao);

        ThreadSafeArrayList<Integer> listaBusca = criarThreadSafePreenchido(tamanho);

        long tempoBusca = medirTempo(() -> {
            for (int i = 0; i < tamanho; i++) {
                int valor = ThreadLocalRandom.current().nextInt(tamanho);
                listaBusca.contains(valor);
            }
        });

        imprimirResultado("ThreadSafeArrayList", "busca", 1, tamanho, tamanho, tempoBusca);

        ThreadSafeArrayList<Integer> listaRemocao = criarThreadSafePreenchido(tamanho);

        long tempoRemocao = medirTempo(() -> {
            for (int i = 0; i < tamanho; i++) {
                listaRemocao.removeRandom();
            }
        });

        imprimirResultado("ThreadSafeArrayList", "remocao", 1, tamanho, tamanho, tempoRemocao);
    }

    private static void testarThreadSafeDezesseisThreads(int tamanho) throws InterruptedException {
        int operacoesPorThread = tamanho / THREADS;
        long totalOperacoes = (long) operacoesPorThread * THREADS;

        ThreadSafeArrayList<Integer> listaInsercao = new ThreadSafeArrayList<>();

        long tempoInsercao = medirTempoConcorrente(THREADS, () -> {
            for (int i = 0; i < operacoesPorThread; i++) {
                int valor = ThreadLocalRandom.current().nextInt();
                listaInsercao.add(valor);
            }
        });

        imprimirResultado("ThreadSafeArrayList", "insercao", THREADS, tamanho, totalOperacoes, tempoInsercao);

        ThreadSafeArrayList<Integer> listaBusca = criarThreadSafePreenchido(tamanho);

        long tempoBusca = medirTempoConcorrente(THREADS, () -> {
            for (int i = 0; i < operacoesPorThread; i++) {
                int valor = ThreadLocalRandom.current().nextInt(tamanho);
                listaBusca.contains(valor);
            }
        });

        imprimirResultado("ThreadSafeArrayList", "busca", THREADS, tamanho, totalOperacoes, tempoBusca);

        ThreadSafeArrayList<Integer> listaRemocao = criarThreadSafePreenchido(tamanho);

        long tempoRemocao = medirTempoConcorrente(THREADS, () -> {
            for (int i = 0; i < operacoesPorThread; i++) {
                listaRemocao.removeRandom();
            }
        });

        imprimirResultado("ThreadSafeArrayList", "remocao", THREADS, tamanho, totalOperacoes, tempoRemocao);
    }

    private static void testarVectorDezesseisThreads(int tamanho) throws InterruptedException {
        int operacoesPorThread = tamanho / THREADS;
        long totalOperacoes = (long) operacoesPorThread * THREADS;

        Vector<Integer> vectorInsercao = new Vector<>();

        long tempoInsercao = medirTempoConcorrente(THREADS, () -> {
            for (int i = 0; i < operacoesPorThread; i++) {
                int valor = ThreadLocalRandom.current().nextInt();
                vectorInsercao.add(valor);
            }
        });

        imprimirResultado("Vector", "insercao", THREADS, tamanho, totalOperacoes, tempoInsercao);

        Vector<Integer> vectorBusca = criarVectorPreenchido(tamanho);

        long tempoBusca = medirTempoConcorrente(THREADS, () -> {
            for (int i = 0; i < operacoesPorThread; i++) {
                int valor = ThreadLocalRandom.current().nextInt(tamanho);
                vectorBusca.contains(valor);
            }
        });

        imprimirResultado("Vector", "busca", THREADS, tamanho, totalOperacoes, tempoBusca);

        Vector<Integer> vectorRemocao = criarVectorPreenchido(tamanho);

        long tempoRemocao = medirTempoConcorrente(THREADS, () -> {
            for (int i = 0; i < operacoesPorThread; i++) {
                synchronized (vectorRemocao) {
                    if (!vectorRemocao.isEmpty()) {
                        int indice = ThreadLocalRandom.current().nextInt(vectorRemocao.size());
                        vectorRemocao.remove(indice);
                    }
                }
            }
        });

        imprimirResultado("Vector", "remocao", THREADS, tamanho, totalOperacoes, tempoRemocao);
    }

    private static ArrayList<Integer> criarArrayListPreenchido(int tamanho) {
        ArrayList<Integer> lista = new ArrayList<>();

        for (int i = 0; i < tamanho; i++) {
            lista.add(i);
        }

        return lista;
    }

    private static ThreadSafeArrayList<Integer> criarThreadSafePreenchido(int tamanho) {
        ThreadSafeArrayList<Integer> lista = new ThreadSafeArrayList<>();

        for (int i = 0; i < tamanho; i++) {
            lista.add(i);
        }

        return lista;
    }

    private static Vector<Integer> criarVectorPreenchido(int tamanho) {
        Vector<Integer> lista = new Vector<>();

        for (int i = 0; i < tamanho; i++) {
            lista.add(i);
        }

        return lista;
    }

    private static long medirTempo(Runnable tarefa) {
        long inicio = System.nanoTime();

        tarefa.run();

        long fim = System.nanoTime();

        return fim - inicio;
    }

    private static long medirTempoConcorrente(int quantidadeThreads, Runnable tarefa) throws InterruptedException {
        CountDownLatch inicio = new CountDownLatch(1);
        CountDownLatch fim = new CountDownLatch(quantidadeThreads);

        for (int i = 0; i < quantidadeThreads; i++) {
            Thread thread = new Thread(() -> {
                try {
                    inicio.await();
                    tarefa.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    fim.countDown();
                }
            });

            thread.start();
        }

        long tempoInicial = System.nanoTime();

        inicio.countDown();

        fim.await();

        long tempoFinal = System.nanoTime();

        return tempoFinal - tempoInicial;
    }

    private static void imprimirResultado(
            String estrutura,
            String operacao,
            int threads,
            int tamanho,
            long quantidadeOperacoes,
            long tempoNano
    ) {
        double tempoSegundos = tempoNano / 1_000_000_000.0;
        double operacoesPorSegundo = quantidadeOperacoes / tempoSegundos;

        System.out.printf(
                "%-22s | %-8s | threads: %2d | tamanho: %6d | tempo: %.6f s | ops/s: %.2f%n",
                estrutura,
                operacao,
                threads,
                tamanho,
                tempoSegundos,
                operacoesPorSegundo
        );
    }
}