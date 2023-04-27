
package com.mycompany.trabajointegrador_v3;


public class ReglaPuntosExtraRonda extends ReglasPuntosExtras {

    private int numeroRonda;

    public ReglaPuntosExtraRonda(Participante participante, int numeroRonda) {
        super(participante);
        this.numeroRonda = numeroRonda;
    }
    //Se asigna un booleano true, toma los pronostico con el mismo número de ronda y si no acertó algún pronostico lo cambia a false por lo que no suma puntos extra.
    private boolean controlTodosAciertos() {
        boolean controlTodosAciertos = true;
        for (Pronostico pronostico : this.participante.getPronosticos()) {
            if (pronostico.getPartido().getNumeroRonda() == this.numeroRonda) {
                if (pronostico.calcularAciertoPronostico() == 0) {
                    controlTodosAciertos = false;
                }
            }
            
        }
        return controlTodosAciertos;
    }
    
    //Según el resultado booleano devuelve 1 o 0.
    @Override
    public int calcularPuntosExtras() {
        int puntosExtra;
        
        if (controlTodosAciertos()){
            return puntosExtra = 1;
        }
        return puntosExtra = 0;
    }
}
