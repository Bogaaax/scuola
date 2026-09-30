package com.example;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Cavallo implements Runnable {
    private final String nome;
    private final int lunghezzaPercorso;
    private int distanzaPercorsa;
    private final Random random;
    
    // Riferimento condiviso per determinare se c'è già un vincitore
    private final AtomicBoolean garaFondata;

    public Cavallo(String nome, int lunghezzaPercorso, AtomicBoolean garaFondata) {
        this.nome = nome;
        this.lunghezzaPercorso = lunghezzaPercorso;
        this.distanzaPercorsa = 0;
        this.random = new Random();
        this.garaFondata = garaFondata;
    }

    @Override
    public void run() {
        while (distanzaPercorsa < lunghezzaPercorso) {
            distanzaPercorsa++;
            
            System.out.println("[" + nome + "] ha percorso " + distanzaPercorsa + " metri.");

            // Controllo se il cavallo ha appena tagliato il traguardo
            if (distanzaPercorsa >= lunghezzaPercorso) {
                // compareAndSet imposta a 'true' solo se il valore precedente era 'false' (operazione atomica)
                if (garaFondata.compareAndSet(false, true)) {
                    System.out.println("\n*** IL VINCITORE È: " + nome.toUpperCase() + "! ***\n");
                }
                break; // Il cavallo ha terminato la corsa
            }

            try {
                int tempoSleep = random.nextInt(401) + 400;
                Thread.sleep(tempoSleep);
            } catch (InterruptedException e) {
                System.out.println("[" + nome + "] è stato disturbato durante la corsa!");
                Thread.currentThread().interrupt(); 
                break;
            }
        }
    }
}
