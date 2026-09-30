package com.example;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== BENVENUTI ALL'IPPODROMO JAVA ===");
        
        // Input del numero di cavalli
        int numeroCavalli = 0;
        while (numeroCavalli <= 0) {
            System.out.print("Inserisci il numero di cavalli partecipanti (minimo 1): ");
            if (scanner.hasNextInt()) {
                numeroCavalli = scanner.nextInt();
                if (numeroCavalli <= 0) {
                    System.out.println("Inserisci un numero valido maggiore di 0.");
                }
            } else {
                System.out.println("Per favore, inserisci un numero intero.");
                scanner.next(); // Pulisce l'input errato
            }
        }

        // Configurazione della gara
        int lunghezzaPercorso = 50; // Lunghezza fissata a 50 metri per non sovraccaricare la console
        AtomicBoolean garaFondata = new AtomicBoolean(false); // Flag atomico per il vincitore
        Thread[] threadCavalli = new Thread[numeroCavalli];

        System.out.println("\nLa gara sta per iniziare! Distanza totale: " + lunghezzaPercorso + " metri.");
        System.out.println("Pronti... Partenza... VIA!\n-----------------------------------");

        // Istanziazione e avvio dei thread
        for (int i = 0; i < numeroCavalli; i++) {
            String nomeCavallo = "Cavallo-" + (i + 1);
            Cavallo cavallo = new Cavallo(nomeCavallo, lunghezzaPercorso, garaFondata);
            
            threadCavalli[i] = new Thread(cavallo);
            threadCavalli[i].start(); // Avvia il thread in modo asincrono
        }

        // Il main attende che tutti i cavalli abbiano completato la corsa prima di chiudersi
        for (int i = 0; i < numeroCavalli; i++) {
            try {
                threadCavalli[i].join();
            } catch (InterruptedException e) {
                System.out.println("Il tracciamento della gara è stato interrotto.");
            }
        }

        System.out.println("-----------------------------------");
        System.out.println("Gara terminata! Tutti i cavalli sono arrivati al traguardo.");
        scanner.close();
    }
}
