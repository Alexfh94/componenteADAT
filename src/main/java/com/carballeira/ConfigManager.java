package com.carballeira;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Clase para gestionar la configuración de la base de datos mediante un archivo JSON.
 */
public class ConfigManager {
    private static final String CONFIG_FILE = "db_config.json"; // Nombre del archivo de configuración

    /**
     * Guarda la configuración en un archivo JSON.
     *
     * @param connectionString Cadena de conexión a la base de datos.
     * @param timeout          Tiempo de espera en segundos.
     */
    public static void saveConfig(String connectionString, int timeout) {
        JSONObject config = new JSONObject();
        config.put("connectionString", connectionString);
        config.put("timeout", timeout);

        try (FileWriter file = new FileWriter(CONFIG_FILE)) {
            file.write(config.toString(4)); // Guarda el JSON con formato indentado
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga la configuración desde un archivo JSON.
     *
     * @return Un objeto JSONObject con la configuración cargada, o null si ocurre un error.
     */
    public static JSONObject loadConfig() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(CONFIG_FILE)));
            return new JSONObject(content);
        } catch (IOException e) {
            return null;
        }
    }
}
