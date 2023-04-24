
package com.mycompany.trabajointegrador_v3;

public class ReglaPuntosExtraFase extends ReglasPuntosExtras {
    private int numeroFase;
    private String nombreEquipo;

    public ReglaPuntosExtraFase(Participante participante, int numeroFase, String nombreEquipo) {
        super(participante);
        this.numeroFase = numeroFase;
        this.nombreEquipo = nombreEquipo;
    }

    private boolean controlTodosAciertos(){
        boolean controlTodosAciertos = true;
        
            for (Pronostico pronostico : this.participante.getPronosticos())
                if (((pronostico.getPartido().getEquipo1().getNombre() == null ? this.nombreEquipo == null : pronostico.getPartido().getEquipo1().getNombre().equals(this.nombreEquipo)) || (pronostico.getPartido().getEquipo2().getNombre() == null ? this.nombreEquipo == null : pronostico.getPartido().getEquipo2().getNombre().equals(this.nombreEquipo))) && pronostico.getPartido().getNumeroFase() == this.numeroFase) {
                    if (pronostico.calcularAciertoPronostico() == 0 ) {
                        controlTodosAciertos = false;
                    }
                }
        return controlTodosAciertos;
    }
    
    @Override
    public int calcularPuntosExtras() {
        int puntosExtra = 0;
        
        if (controlTodosAciertos()){
            return puntosExtra = 1;
        }
        return puntosExtra = 0;
        
    }

    
    
}
