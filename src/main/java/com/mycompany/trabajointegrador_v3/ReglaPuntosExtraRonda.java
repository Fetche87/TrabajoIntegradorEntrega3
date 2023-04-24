
package com.mycompany.trabajointegrador_v3;


public class ReglaPuntosExtraRonda extends ReglasPuntosExtras {

    private int numeroRonda;

    public ReglaPuntosExtraRonda(Participante participante, int numeroRonda) {
        super(participante);
        this.numeroRonda = numeroRonda;
    }
  
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
        
    @Override
    public int calcularPuntosExtras() {
        int puntosExtra;
        
        if (controlTodosAciertos()){
            return puntosExtra = 1;
        }
        return puntosExtra = 0;
    }
}
