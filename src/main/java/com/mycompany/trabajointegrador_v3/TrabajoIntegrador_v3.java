package com.mycompany.trabajointegrador_v3;

import com.google.gson.Gson;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TrabajoIntegrador_v3 {

    public static void main(String[] args) throws IOException {
        //Lectura del archivo resultado.csv
        final String archivoResultados = "resultados.csv";
        final String separadorComa = ";";
        //Creo los arraylist para partidos y rondas    
        ArrayList<Partido> partidos = new ArrayList<>();
        ArrayList<Ronda> rondas = new ArrayList<>();

        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        int cantidadDeRondas = 0;
        String nombreParticipante = "";

        try {
            fileReader = new FileReader(archivoResultados);
            bufferedReader = new BufferedReader(fileReader);
            String linea;
            int numeroRonda = 0;

            while ((linea = bufferedReader.readLine()) != null) {

                String[] partidoComoArreglo = linea.split(separadorComa);

                Equipo equipo1 = new Equipo(partidoComoArreglo[2]);
                Equipo equipo2 = new Equipo(partidoComoArreglo[5]);
                //Hace la verificación si los datos estan completos
                if (partidoComoArreglo.length != 6) {
                    System.out.println("Error de lectura: Faltan datos en el archivo resultados.csv");
                    return;
                }

                int numeroFase = 1;
                int numeroDeLaRonda = 0;
                int golesEquipo1 = 0;
                int golesEquipo2 = 0;
                try {
                    numeroDeLaRonda = Integer.valueOf(partidoComoArreglo[1]);
                    golesEquipo1 = Integer.valueOf(partidoComoArreglo[3]);
                    golesEquipo2 = Integer.valueOf(partidoComoArreglo[4]);
                //Verifica si los goles no son nros enteros
                } catch (NumberFormatException e) {
                    System.out.println("El o los campos número de ronda o goles no son números enteros.");
                }
                //Ingresa el valor cantidadDeRondas
                if (numeroDeLaRonda > cantidadDeRondas) {
                    cantidadDeRondas = numeroDeLaRonda;
                }
                //Agrega cada objeto Partido con sus atributos
                partidos.add(new Partido(numeroDeLaRonda, equipo1, golesEquipo1, golesEquipo2, equipo2, numeroFase));
                
                //Agrega las rondas leyendo el número de rondas
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
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("Excepción cerrando: " + e.getMessage());
            }

        }

        //Creación del ArrayList<Partido> en la clase Ronda.
        for (Partido partido : partidos) {
            for (Ronda ronda : rondas) {
                if (partido.getNumeroRonda() == ronda.getNumeroRonda()) {
                    ronda.agregarPartido(partido);
                }
            }

        }

        //Lectura del Archivo de configuración json.
        String url_Connection = "";
        String user = "";
        String pass = "";
        int puntajePorAcierto = 0;
        int puntajeExtra = 0;
        
        FileReader archivo;
        
        try {
            archivo = new FileReader("configuracion.json");
            bufferedReader = new BufferedReader(archivo);
            String linea;
            StringBuilder stringBuilder = new StringBuilder();
            while ((linea = bufferedReader.readLine()) != null) {
                stringBuilder.append(linea);
            }
            bufferedReader.close();

            String contenidoJson = stringBuilder.toString();
            
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(contenidoJson, JsonObject.class);
            url_Connection = jsonObject.get("url_connection").getAsString();
            user = jsonObject.get("user").getAsString();
            pass = jsonObject.get("password").getAsString();
            puntajePorAcierto = jsonObject.get("PUNTAJE_POR_ACIERTO").getAsInt();
            puntajeExtra = jsonObject.get("PUNTAJE_EXTRA").getAsInt();
            
            

        } catch (IOException e) {
        }

        //Lectura de la Base de Datos pronosticos_deportivos.
        ArrayList<Pronostico> pronosticos = new ArrayList<>();
        ArrayList<Participante> participantes = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url_Connection, user, pass);
            Statement stmt = con.createStatement();

            ResultSet resultConsulta = stmt.executeQuery("SELECT * FROM pronosticos;");
            //Setea como deafult el resultado EMPATE y analiza si la x se encuentra en la posición 4 define el resultado como GANA_EQUIPO_1 o si la x se encuentra en la posición 6 define el resultado como GANA_EQUIPO_2
            while (resultConsulta.next()) {
                ResultadoEnum resultado = ResultadoEnum.EMPATE;
                Partido partido = BuscarPartidoPorNombreEquipos(partidos, resultConsulta.getString(3), resultConsulta.getString(7));

                if ("X".equals(resultConsulta.getString(4))) {
                    resultado = ResultadoEnum.GANA_EQUIPO_1;

                } else if ("X".equals(resultConsulta.getString(6))) {
                    resultado = ResultadoEnum.GANA_EQUIPO_2;
                }

                pronosticos.add(new Pronostico(partido, resultado, resultConsulta.getString(2)));
                
                if (resultConsulta.getString(2) == null ? nombreParticipante != null : !resultConsulta.getString(2).equals(nombreParticipante)) {
                    nombreParticipante = resultConsulta.getString(2);
                    participantes.add(new Participante(nombreParticipante));
                }
                
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);

        }

        //Creación del ArrayList<Pronostico> pronosticos de cada participante en la clase Participante.
        for (Pronostico pronostico : pronosticos) {
            //Agrega el puntaje de cada pronóstico.
            pronostico.setPuntajePronostico(puntajePorAcierto);
            for (Participante participante : participantes) {
                //Agrega el puntaje Extra de cada pronóstico.
                participante.setPuntajeExtra(puntajeExtra);
                //Agerga el pronóstico que corresponde a cada participante.
                if (pronostico.getPersona() == null ? participante.getNombre() == null : pronostico.getPersona().equals(participante.getNombre())) {
                    participante.agregarPronostico(pronostico);
                }
            }

        }

        //Creacción del ArrayList<Pronosticos> pronosticos de cada ronda haciendo que busque y coincida con cada partido del ArrayList<Partidos> por cada ronda.
        for (Ronda ronda : rondas) {
            for (Partido partido : ronda.getPartidos()) {
                for (Participante participante : participantes) {
                    ronda.agregarPronostico(BuscarPronosticoPorPartido(participante.getPronosticos(), partido));
                }
            }

        }

        //Impresión en consola   
        for (Participante participante : participantes) {
            participante.calcularAciertos();
            participante.calcularPuntaje();
            System.out.println("El participante " + participante.getNombre() + " obtuvo " + participante.puntosExtraObtenidos() + " puntos extra.");
            System.out.println("Participante: " + participante.getNombre() + " - Puntaje: " + participante.getPuntaje() + " - Aciertos: " + participante.getCantidadAciertos());
            System.out.println("-------------------------------------------------------");
        }

    }

    public static Partido BuscarPartidoPorNombreEquipos(ArrayList<Partido> partidos, String nombreEquipo1, String nombreEquipo2) {
        Partido partidoEncontrado = partidos.stream().filter(partido -> partido.getEquipo1().getNombre().equals(nombreEquipo1) && partido.getEquipo2().getNombre().equals(nombreEquipo2)).findAny().orElse(null);
        return partidoEncontrado;
    }

    public static Pronostico BuscarPronosticoPorPartido(ArrayList<Pronostico> pronosticos, Partido partido) {
        Pronostico pronosticoEncontrado = pronosticos.stream().filter(pronostico -> pronostico.getPartido().equals(partido)).findAny().orElse(null);
        return pronosticoEncontrado;

    }

}
