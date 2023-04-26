package com.mycompany.trabajointegrador_v3;

import java.util.ArrayList;

public class Pronostico {

    private Partido partido;
    private ResultadoEnum resultado;
    private int puntaje;
    private String persona;
    public int puntajePronostico;
    private int numeroRonda;

    public Pronostico(Partido partido, ResultadoEnum resultado, String persona) {
        this.partido = partido;
        this.puntaje = 0;
        this.resultado = resultado;
        this.persona = persona;
    }
    //revisar para qué se usa??????
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getPersona() {
        return persona;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setNumeroRonda(int numeroRonda) {
        this.numeroRonda = numeroRonda;
    }

    public int calcularPuntajePronostico() {
        
        ResultadoEnum resultadoReal = this.partido.decidirResultado();
        if (resultadoReal == this.resultado) {
            return puntajePronostico;
        }

        return 0;

    }
    
    public int setPuntajePronostico(int puntajeCadaPronostico){
        return puntajePronostico = puntajeCadaPronostico;
    }
    //Compara el resultado del partido con el pronostico y otorga 1 punto por acierto.
    public int calcularAciertoPronostico() {
        ResultadoEnum resultadoReal = this.partido.decidirResultado();
        if (resultadoReal == this.resultado) {
            return 1;
        }
        return 0;
    }
    
     
        
    

}
