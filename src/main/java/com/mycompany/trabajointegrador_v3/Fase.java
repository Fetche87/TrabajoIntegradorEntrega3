/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajointegrador_v3;

import java.util.ArrayList;

/**
 *
 * @author feder
 */
public class Fase {
    private int numeroFase;
    private ArrayList<Ronda> rondas = new ArrayList<>();
    private int puntajeFase;

    public Fase(int numeroFase) {
        this.numeroFase = numeroFase;
    }
    
    public void agregarRonda(Ronda ronda) {
        this.rondas.add(ronda);
    }

    public ArrayList<Ronda> getRondas() {
        return rondas;
    }

    public int getNumeroFase() {
        return numeroFase;
    }
    
    
    
    public int calcularPuntajeFase() {
        int puntajeFinal = 0;
        for (Ronda ronda : this.rondas) {
            puntajeFinal += ronda.getPuntajeRonda();
        }
        return this.puntajeFase = puntajeFinal;
    }
        
}
