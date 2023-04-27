
package com.mycompany.trabajointegrador_v3;

public abstract class ReglasPuntosExtras {
    protected Participante participante;

    public ReglasPuntosExtras(Participante participante) {
        this.participante = participante;
    }
    
                   
    public abstract int calcularPuntosExtras();
        
   
    
}
