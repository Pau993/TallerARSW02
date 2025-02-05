package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{

	private final Control control;
	int start,end;
	
	private List<Integer> primes;
	
    /**
     * Constructor de la clase PrimeFinderThread
     * @param a
     * @param b
     */
	public PrimeFinderThread(int start, int end, Control control){
		super();
        this.primes = new LinkedList<>(); // Crea una lista enlazada
		this.start = start;
		this.end = end;
        this.control = control;
	}

    /* 
     * Inicia el hilo, sobre escritura del método caracteristico de los hilos, la acción que realiza el hilo cuando se inicie
     */
        @Override
	public void run(){
            for (int i= start;i < end;i++){		
                control.waitIfPaused();			
                if (isPrime(i)){
                    primes.add(i);
                    //System.out.println(i);
                }
            }
	}
	
    /**
     * Verifica si un número es primo
     * @param n
     * @return
     */
	boolean isPrime(int n) {
	    boolean ans;
            if (n > 2) { 
                ans = n%2 != 0;
                for(int i = 3;ans && i*i <= n; i+=2 ) {
                    ans = n % i != 0;
                }
            } else {
                ans = n == 2;
            }
	    return ans;
	}

    /**
     * Retorna la lista de números primos
     * @return
     */
	public List<Integer> getPrimes() {
		return primes;
	}
	
}
