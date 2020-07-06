package Clases;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author AA2020
 */
public class LectorCSV {
    public ArrayList<ArrayList<String>> leer(String ruta){
        ArrayList<ArrayList<String>> datos = new ArrayList<>();
        Path filePath = Paths.get(ruta);
        try {
            BufferedReader br = Files.newBufferedReader(filePath);
            String linea;
            while((linea = br.readLine()) != null){
                String[] datosLinea = linea.split(";");
                ArrayList<String> temporal = new ArrayList<>();
                temporal.addAll(Arrays.asList(datosLinea));
                datos.add(temporal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datos;
    }
}
