/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.threadsafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Joseph
 */

public class ThreadSafe<T> {

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

    public void add(int indice, T elemento) {
        lock.writeLock().lock();

        try {
            lista.add(indice, elemento);
        } finally {
            lock.writeLock().unlock();
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

    public boolean remove(T elemento) {
        lock.writeLock().lock();

        try {
            return lista.remove(elemento);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public T get(int indice) {
        lock.readLock().lock();

        try {
            return lista.get(indice);
        } finally {
            lock.readLock().unlock();
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

    public boolean isEmpty() {
        lock.readLock().lock();

        try {
            return lista.isEmpty();
        } finally {
            lock.readLock().unlock();
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

    public List<T> snapshot() {
        lock.readLock().lock();

        try {
            return new ArrayList<>(lista);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public String toString() {
        lock.readLock().lock();

        try {
            return lista.toString();
        } finally {
            lock.readLock().unlock();
        }
    }
}