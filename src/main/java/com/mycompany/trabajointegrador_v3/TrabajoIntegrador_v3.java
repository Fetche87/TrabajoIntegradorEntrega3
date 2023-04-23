package com.mycompany.trabajointegrador_v3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Stream;


public class TrabajoIntegrador_v3 {

    public static void main(String[] args) {
        final String archivoResultados = "resultados.csv";
        final String separadorComa = ";";
        
    
        ArrayList <Partido> partidos = new ArrayList<>();
        ArrayList <Ronda> rondas = new ArrayList<>();
        ArrayList <Fase> fases = new ArrayList<>();
        
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        int cantidadDeRondas = 0;
        String nombreParticipante= "";
        

        try{
            fileReader = new FileReader(archivoResultados);
            bufferedReader = new BufferedReader (fileReader);
            String linea;
            int numeroRonda = 0;
            
            while((linea = bufferedReader.readLine()) !=null){
                
                String[] partidoComoArreglo = linea.split(separadorComa);
                    
                Equipo equipo1 = new Equipo(partidoComoArreglo[2]);
                Equipo equipo2 = new Equipo(partidoComoArreglo[5]);
                
                if (partidoComoArreglo.length != 6) {
                    System.out.println("Error de lectura: Faltan datos en el archivo resultados.csv");
                    return;
                }
                
                int numeroFase = 1;
                int numeroDeLaRonda = 0;
                int golesEquipo1 = 0;
                int golesEquipo2 = 0;
                try{
                    numeroDeLaRonda = Integer.valueOf(partidoComoArreglo[1]);
                    golesEquipo1 = Integer.valueOf(partidoComoArreglo[3]);
                    golesEquipo2 = Integer.valueOf(partidoComoArreglo[4]);
                    
                } catch (NumberFormatException e) {
                    System.out.println("El o los campos número de ronda o goles no son números enteros.");
                }
                if (numeroDeLaRonda > cantidadDeRondas){
                    cantidadDeRondas = numeroDeLaRonda;
                }
                
                partidos.add(new Partido(numeroDeLaRonda, equipo1, golesEquipo1, golesEquipo2, equipo2, numeroFase));
                
                
                
                if (Integer.valueOf(partidoComoArreglo[1]) != numeroRonda) {
                    numeroRonda = Integer.valueOf(partidoComoArreglo[1]);
                    rondas.add(new Ronda(numeroRonda));
                }
            }
        } catch (IOException e) {
            System.out.println("Excepción leyendo archivo: " + e.getMessage());
        } finally {
            try {
                    if (fileReader != null) {
                        fileReader.close();
                    }
                    if (bufferedReader != null){
                        bufferedReader.close();
                    }
            } catch (IOException e){
                System.out.println("Excepción cerrando: " + e.getMessage());
            }
            
        }
        
        
        
        for (Partido partido : partidos){
            for (Ronda ronda : rondas){
                if (partido.getNumeroRonda() == ronda.getNumeroRonda()){
                    ronda.agregarPartido(partido);
                }
            }
            
        }
        /*
        for (Ronda ronda : rondas){
            for (Fase fase : fases){
                if (ronda.getNumeroFase() == fase.getNumeroFase()){
                    fase.agregarRonda(ronda);
                }
            }
        }
        */
        
        final String archivoPronosticos = "pronostico.csv";
        ArrayList <Pronostico> pronosticos = new ArrayList<>();
        ArrayList<Participante> participantes = new ArrayList<>();
        
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pronosticos_deportivos", "root", "HetrTe232#jR33");
            Statement stmt = con.createStatement();
            
            ResultSet resultConsulta=stmt.executeQuery("SELECT * FROM pronosticos;");
            
            while(resultConsulta.next()){
                ResultadoEnum resultado = ResultadoEnum.EMPATE;
                Partido partido = BuscarPartidoPorNombreEquipos(partidos,resultConsulta.getString(3) ,resultConsulta.getString(7));
                
                
                if ("X".equals(resultConsulta.getString(4))) {
                    resultado = ResultadoEnum.GANA_EQUIPO_1;
                    
                } else if ("X".equals(resultConsulta.getString(6)) ) {
                    resultado = ResultadoEnum.GANA_EQUIPO_2;
                }
                
                pronosticos.add(new Pronostico(partido, resultado, resultConsulta.getString(2)));
                
                if (resultConsulta.getString(2) == null ? nombreParticipante != null : !resultConsulta.getString(2).equals(nombreParticipante)) {
                        nombreParticipante = resultConsulta.getString(2);
                        participantes.add(new Participante(nombreParticipante));
                    }
            }
        } catch (ClassNotFoundException | SQLException e){System.out.println(e);
        
        
        }
        /*
        try {
            fileReader = new FileReader(archivoPronosticos);
            bufferedReader = new BufferedReader(fileReader);
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                
                String[] pronosticoComoArreglo = linea.split(separadorComa);
                ResultadoEnum resultado = ResultadoEnum.EMPATE;
                Partido partido = BuscarPartidoPorNombreEquipos(partidos,pronosticoComoArreglo[1],pronosticoComoArreglo[5]);
                
                if (pronosticoComoArreglo.length != 6) {
                    System.out.println("Error de lectura: Faltan datos en el archivo resultados.csv");
                    return;
                }
                
                               
                if ("x".equals(pronosticoComoArreglo[2])) {
                    resultado = ResultadoEnum.GANA_EQUIPO_1;
                    
                } else if ("x".equals(pronosticoComoArreglo[4]) ) {
                    resultado = ResultadoEnum.GANA_EQUIPO_2;
                }
                
                pronosticos.add(new Pronostico(partido, resultado, pronosticoComoArreglo[0])); 
                
            }
        } catch (IOException e) {
            System.out.println("Excepción leyendo archivo: " + e.getMessage());
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("Excepción cerrando: " + e.getMessage());
            }
            
        }*/
        
         
        for (Pronostico pronostico : pronosticos){
            for (Participante participante : participantes){
                if (pronostico.getPersona() == null ? participante.getNombre() == null : pronostico.getPersona().equals(participante.getNombre())){
                    participante.agregarPronostico(pronostico);
                }
            }
            
        }
        
                
        for (Ronda ronda : rondas){
            for (Partido partido : ronda.getPartidos()) {
                for (Participante participante : participantes) {
                    ronda.agregarPronostico(BuscarPronosticoPorPartido(participante.getPronosticos(), partido));
                }
            }
            
            
        }
        
       
        //Impresión en consola
        
        
        
        
        for (Ronda ronda : rondas){
            ronda.calcularPuntajeRonda();
            System.out.println("El puntaje total de la ronda " + ronda.getNumeroRonda() + " es de :" + ronda.getPuntajeRonda());
        }
        
        for (Participante participante : participantes){
            participante.calcularAciertos();
            participante.calcularPuntaje();
            System.out.println("El participante " + participante.getNombre() + " obtuvo " + participante.puntosExtraObtenidos() + " puntos extra.");
            System.out.println("Participante: " + participante.getNombre() + " - Puntaje: " + participante.getPuntaje() + " - Aciertos: " + participante.getCantidadAciertos());
        }
        
        
    }

    

        
    public static Partido BuscarPartidoPorNombreEquipos(ArrayList<Partido> partidos, String nombreEquipo1, String nombreEquipo2){
        Partido partidoEncontrado = partidos.stream().filter(partido ->partido.getEquipo1().getNombre().equals(nombreEquipo1) && partido.getEquipo2().getNombre().equals(nombreEquipo2)).findAny().orElse(null);
                return partidoEncontrado;
    }
    
    public static Pronostico BuscarPronosticoPorPartido(ArrayList<Pronostico> pronosticos, Partido partido) {
        Pronostico pronosticoEncontrado = pronosticos.stream().filter(pronostico -> pronostico.getPartido().equals(partido)).findAny().orElse(null);
            return pronosticoEncontrado;
        
    }
}
