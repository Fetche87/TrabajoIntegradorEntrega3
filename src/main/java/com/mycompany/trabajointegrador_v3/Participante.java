package com.mycompany.trabajointegrador_v3;

import java.util.ArrayList;

public class Participante {

    private String nombre;
    private int puntaje;
    private int cantidadAciertos;
    private ArrayList<Pronostico> pronosticos = new ArrayList<>();

    public Participante(String nombre) {
        this.nombre = nombre;
        //this.pronosticos = pronosticos;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Pronostico> getPronosticos() {
        return pronosticos;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getCantidadAciertos() {
        return cantidadAciertos;
    }

    public int calcularPuntaje() {
        int puntajeFinal = 0;
        for (Pronostico pronostico : this.pronosticos) {
            puntajeFinal += pronostico.calcularPuntajePronostico();
        }
        return this.puntaje = puntajeFinal + this.puntosExtraObtenidos();
    }

    public int calcularAciertos() {
        int aciertosFinal = 0;
        for (Pronostico pronostico : this.pronosticos) {
            aciertosFinal += pronostico.calcularPuntajePronostico();
        }
        return this.cantidadAciertos = aciertosFinal;
    }

    public void agregarPronostico(Pronostico pronostico) {
        this.pronosticos.add(pronostico);
    }

    //Creamos ArrayList con los métodos
    public int puntosExtraObtenidos() {

        int puntajeExtraObtenidos = 0;
        //Configuración ReglaPuntosExtraFase
        ReglaPuntosExtraFase puntosExtraFase = new ReglaPuntosExtraFase();
        puntosExtraFase.setParticipante(this);
        puntosExtraFase.setNombreEquipo("Argentina");
        puntosExtraFase.setNumeroFase(1);
        puntajeExtraObtenidos += puntosExtraFase.calcularPuntosExtras();
        //Configuración ReglapuntosExtraRonda
        ReglaPuntosExtraRonda puntosExtraRonda = new ReglaPuntosExtraRonda();
        puntosExtraRonda.setRonda(1);
        puntosExtraRonda.setParticipante(this);
        puntajeExtraObtenidos += puntosExtraFase.calcularPuntosExtras();
        return puntajeExtraObtenidos;
    }
}

