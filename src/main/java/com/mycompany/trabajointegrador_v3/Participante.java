package com.mycompany.trabajointegrador_v3;

import java.util.ArrayList;

public class Participante {

    private String nombre;
    private int puntaje;
    private int cantidadAciertos;
    private ArrayList<Pronostico> pronosticos = new ArrayList<>();
    public static int PUNTAJE_EXTRA;
    
    public Participante(String nombre) {
        this.nombre = nombre;
        //this.pronosticos = pronosticos;
    }

    public String getNombre() {
        return nombre;
    }
    
    public int setPuntajeExtra(int puntajeExtra){
        return PUNTAJE_EXTRA = puntajeExtra;
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
    //Realiza el calculo por ronda y los puntos extra por acertar todos los partidos de ronda y los suma al puntaje total
    public int calcularPuntaje() {
        int puntajeFinal = 0;
        for (int j = 1; j <= obtenerCantidadDeFaseTotales(); j++) {
            int puntajePorFase = 0;
            for (int i = 1; i <= obtenerCantidadDeRondasTotales(); i++) {
                int puntajePorRonda = 0;
                for (Pronostico pronostico : this.pronosticos) {
                    if (pronostico.getPartido().getNumeroRonda() == i) {
                        puntajePorRonda += pronostico.getPuntajePronostico();
                    }
                }
                puntajePorFase += puntajePorRonda;
                System.out.println("El puntaje de " + this.getNombre() + " en la ronda " + i + " es: " + puntajePorRonda);
            }
            puntajeFinal += puntajePorFase;
            System.out.println("El puntaje de " + this.getNombre() + " en la fase " + j + " es: " + puntajePorFase);
        }

        return this.puntaje = puntajeFinal + this.puntosExtraObtenidos();
    }
    //Toma los aciertos de cada pronotico para calcular el total de aciertos del participante.
    public int calcularAciertos() {
        int aciertosFinal = 0;
        for (Pronostico pronostico : this.pronosticos) {
            aciertosFinal += pronostico.getPuntajePronostico();
        }
        return this.cantidadAciertos = aciertosFinal;
    }

    public void agregarPronostico(Pronostico pronostico) {
        this.pronosticos.add(pronostico);
    }
    //obtiene cantidad de rondas totales
    public int obtenerCantidadDeRondasTotales(){
        int cantidadDeRondasTotales = 0;
        for (Pronostico pronostico : this.getPronosticos()){
            if (pronostico.getPartido().getNumeroRonda() > cantidadDeRondasTotales ){
                cantidadDeRondasTotales = pronostico.getPartido().getNumeroRonda();
            }
        }
        return cantidadDeRondasTotales;
    }
    
    //Obtiene un listado de equipo para luego buscar el puntaje extra por fase de cada equipo.
    public ArrayList<String> obtenerListadoEquipos(){
        ArrayList<String> listadoEquipos = new ArrayList<>();
        for (Pronostico pronostico : this.pronosticos){
            if (!listadoEquipos.contains(pronostico.getPartido().getEquipo1().getNombre())){
                listadoEquipos.add(pronostico.getPartido().getEquipo1().getNombre());
            }
            if (!listadoEquipos.contains(pronostico.getPartido().getEquipo2().getNombre())){
                listadoEquipos.add(pronostico.getPartido().getEquipo2().getNombre());
            }
        }
        return listadoEquipos;
    }
    
    public int obtenerCantidadDeFaseTotales(){
        int cantidadDeFasesTotales = 0;
        for (Pronostico pronostico : this.getPronosticos()){
            if (pronostico.getPartido().getNumeroFase() > cantidadDeFasesTotales ){
                cantidadDeFasesTotales = pronostico.getPartido().getNumeroFase();
            }
        }
        return cantidadDeFasesTotales;
    }

    //Creamos ArrayList con los métodos
    public int puntosExtraObtenidos() {

        int puntajeExtraObtenidos = 0;
        //Configuración ReglaPuntosExtraFase
        for (int i = 1; i <= this.obtenerCantidadDeFaseTotales(); i++){
            for (String equipo : this.obtenerListadoEquipos()){
                ReglaPuntosExtraFase puntosExtraFase = new ReglaPuntosExtraFase(this, i,equipo);
                puntajeExtraObtenidos += puntosExtraFase.calcularPuntosExtras();
            }
            
        }
        //Configuración ReglapuntosExtraRonda
        for (int i = 1; i <= this.obtenerCantidadDeRondasTotales(); i++){
            ReglaPuntosExtraRonda puntosExtraRonda = new ReglaPuntosExtraRonda(this, i);
            puntajeExtraObtenidos += puntosExtraRonda.calcularPuntosExtras();
        }
        return puntajeExtraObtenidos * PUNTAJE_EXTRA;
    }
}

