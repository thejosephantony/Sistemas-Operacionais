/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.threadsafearraylist;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Joseph
 */

public class ThreadSafeArrayList<T> {

    private final ArrayList<T> lista = new ArrayList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void add(T elemento) {
        lock.writeLock().lock();

        try {
            lista.add(elemento);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean contains(T elemento) {
        lock.readLock().lock();

        try {
            return lista.contains(elemento);
        } finally {
            lock.readLock().unlock();
        }
    }

    public T remove(int indice) {
        lock.writeLock().lock();

        try {
            return lista.remove(indice);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public T removeRandom() {
        lock.writeLock().lock();

        try {
            if (lista.isEmpty()) {
                return null;
            }

            int indice = ThreadLocalRandom.current().nextInt(lista.size());
            return lista.remove(indice);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int size() {
        lock.readLock().lock();

        try {
            return lista.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void clear() {
        lock.writeLock().lock();

        try {
            lista.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }
}