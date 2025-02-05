/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

import java.util.Scanner;

/**
 *
 */
public class Control extends Thread {
    
    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000; 
    private final static int TMILISECONDS = 5000;

    private final int NDATA = MAXVALUE / NTHREADS;

    // Lista de hilos
    private PrimeFinderThread pft[];

    private volatile boolean paused = false;

    /*
     * Constructor de la clase Control
     */
    private Control() {
        super(); // Crea un nuevo hilo
        this.pft = new  PrimeFinderThread[NTHREADS]; // Crea un arreglo de hilos

        int i;
        for(i = 0;i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i*NDATA, (i+1)*NDATA, this); // Crea un hilo
            pft[i] = elem;
        }
        
        pft[i] = new PrimeFinderThread(i*NDATA, MAXVALUE + 1, this); // Crea un hilo
    }

    /* 
     * Crea una nueva instancia de la clase Control
     */
    public static Control newControl() {
        return new Control();
    }

    /*
     * Inicia los hilos
     */
    @Override
    public void run() {
        for(int i = 0;i < NTHREADS;i++ ) {
            pft[i].start();
        }
        while (true) {
            try {
                Thread.sleep(TMILISECONDS); // Pausa el hilo actual
                pauseThreads(); // Pausa los hilos
                showPrimesFound(); // Muestra los primos encontrados
                waitForUserInput(); // Espera a que el usuario presione enter
                resumeThreads(); // Reanuda los hilos
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
    public synchronized void pauseThreads(){
        paused = true;
        System.out.println("Hilos pausados");
    }

    public synchronized void resumeThreads(){
        paused = false;
        notifyAll();
        System.out.println("Hilos reanudados");
    }

    // Espera a que los hilos esten pausados
    public synchronized void waitIfPaused(){
        while(paused){
            try {
                wait(); // Pausa el hilo actual
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Interrumpe el hilo actual
            }
        }
    }

    // Muestra los primos encontrados
    private void showPrimesFound(){
        int totalPrimes = 0;
        for(PrimeFinderThread thread : pft){
            totalPrimes += thread.getPrimes().size();
        }
        System.out.println("Total de primos encontrados: " + totalPrimes);
    }

    private void waitForUserInput(){
        System.out.println("Presione enter para continuar");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
