/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.trabajointegrador_v3.Equipo;
import com.mycompany.trabajointegrador_v3.Partido;
import com.mycompany.trabajointegrador_v3.ResultadoEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author feder
 */
public class PartidoTest {
    
    public PartidoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void empate() {
        Equipo equipo1 = new Equipo("Real Madrid");
        Equipo equipo2 = new Equipo("Chelsea");
        Partido partido = new Partido(1, equipo1, 3, 3, equipo2);
        
        assertEquals(partido.decidirResultado(), ResultadoEnum.EMPATE);
        
    }
    
    @Test
    public void ganaEquipo2() {
        Equipo equipo1 = new Equipo("Real Madrid");
        Equipo equipo2 = new Equipo("Chelsea");
        Partido partido = new Partido(1, equipo1, 0, 2, equipo2);
        
        assertEquals(partido.decidirResultado(), ResultadoEnum.GANA_EQUIPO_2);
        
    }
    
    @Test
    public void ganaEquipo1() {
        Equipo equipo1 = new Equipo("Real Madrid");
        Equipo equipo2 = new Equipo("Chelsea");
        Partido partido = new Partido(1, equipo1, 3, 0, equipo2);
        
        assertEquals(partido.decidirResultado(), ResultadoEnum.GANA_EQUIPO_1);
        
    }
    
    @Test
    public void ganaEquipo2Fallido() {
        Equipo equipo1 = new Equipo("Real Madrid");
        Equipo equipo2 = new Equipo("Chelsea");
        Partido partido = new Partido(1, equipo1, 3, 0, equipo2);
        
        assertNotEquals(partido.decidirResultado(), ResultadoEnum.GANA_EQUIPO_2);
        
    }
}
