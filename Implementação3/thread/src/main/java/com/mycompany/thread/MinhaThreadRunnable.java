/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.thread;

/**
 *
 * @author Joseph
 */
public class MinhaThreadRunnable implements Runnable {
    private String nome;
    
    public MinhaThreadRunnable(String nome){
        this.nome = nome;
    }
    @Override
    public void run(){
        for(int i = 0; i < 10; i++){
            System.out.printf("%s %d%n", this.nome, i);
        }
        System.out.printf("FIM %s%n", this.nome);
    }
    
}
