
package com.mycompany.trabajointegrador_v3;

public abstract class ReglasPuntosExtras {
    protected Participante participante;

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }
    
    
                
    public abstract int calcularPuntosExtras();
        
   
    
}
