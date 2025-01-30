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

    private PrimeFinderThread pft[];
    private final Object lock = new Object();
    
    private Control() {
        super();
        this.pft = new  PrimeFinderThread[NTHREADS];

        for(int i = 0;i < NTHREADS - 1; i++) {
            pft[i] = new PrimeFinderThread(i*NDATA, (i+1)*NDATA, lock);
        }
        pft[NTHREADS - 1] = new PrimeFinderThread((NTHREADS - 1)*NDATA, MAXVALUE + 1, lock);
    }
    
    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {
        for(PrimeFinderThread thread : pft) {
            thread.start();
        }
        while (true) {
            try{
                Thread.sleep(TMILISECONDS);
                pauseThreads();
                showResults();
                waitForEnter();
                resumeThreads();
            }catch(InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            }
        }

        private void pauseThreads() {
            for (PrimeFinderThread thread : pft) {
                thread.pauseThread();
            }
        }

        private void resumeThreads() {
            synchronized (lock) {
                for (PrimeFinderThread thread : pft) {
                    thread.resumeThread();
                }
            }
        }

        private void showResults() {
            System.out.println("\nPrimos encontrados hasta el momento:");
            for (int i = 0; i < NTHREADS; i++) {
                System.out.println("Hilo " + (i + 1) + ": " + pft[i].getPrimes().size() + " primos.");
            }
        }

        private void waitForEnter() {
            System.out.println("\nPresione ENTER para continuar...");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        }
    }

