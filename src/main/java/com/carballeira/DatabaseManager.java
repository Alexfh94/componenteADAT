package com.carballeira;

import java.sql.*;
import java.util.function.Consumer;

/**
 * Clase para gestionar la conexión y ejecución de consultas en una base de datos.
 */
public class DatabaseManager {
    private String connectionString;
    private int timeout;
    private Connection connection;

    private Consumer<String> onConnected;
    private Consumer<String> onQueryExecuted;
    private Consumer<String> onError;

    /**
     * Constructor de la clase DatabaseManager.
     *
     * @param connectionString URL de conexión a la base de datos.
     * @param timeout Tiempo de espera en segundos.
     */
    public DatabaseManager(String connectionString, int timeout) {
        this.connectionString = connectionString;
        this.timeout = timeout;
    }

    /**
     * Establece un callback que se ejecuta cuando la conexión es exitosa.
     *
     * @param onConnected Función a ejecutar al conectar con éxito.
     */
    public void setOnConnected(Consumer<String> onConnected) {
        this.onConnected = onConnected;
    }

    /**
     * Establece un callback que se ejecuta cuando una consulta ha sido ejecutada.
     *
     * @param onQueryExecuted Función a ejecutar tras la ejecución de una consulta.
     */
    public void setOnQueryExecuted(Consumer<String> onQueryExecuted) {
        this.onQueryExecuted = onQueryExecuted;
    }

    /**
     * Establece un callback que se ejecuta cuando ocurre un error.
     *
     * @param onError Función a ejecutar en caso de error.
     */
    public void setOnError(Consumer<String> onError) {
        this.onError = onError;
    }

    /**
     * Intenta establecer una conexión con la base de datos.
     */
    public void connect() {
        try {
            connection = DriverManager.getConnection(connectionString);
            if (onConnected != null) onConnected.accept("Conexión establecida con éxito.");
        } catch (SQLException e) {
            if (onError != null) onError.accept("Error al conectar: " + e.getMessage());
        }
    }

    /**
     * Ejecuta una consulta SQL en la base de datos.
     *
     * @param query Consulta SQL en formato String.
     * @return ResultSet con los resultados de la consulta o null en caso de error.
     */
    public ResultSet executeQuery(String query) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.connection.createStatement();
            rs = stmt.executeQuery(query);

            if (this.onQueryExecuted != null) {
                this.onQueryExecuted.accept("Consulta ejecutada: " + query);
            }

            return rs;
        } catch (SQLException e) {
            if (this.onError != null) {
                this.onError.accept("Error en la consulta: " + e.getMessage());
            }
            return null;
        }
        // No se cierra rs ni stmt aquí para evitar que se cierre automáticamente
    }


    /**
     * Ejecuta una consulta de actualización (INSERT, UPDATE, DELETE) en la base de datos.
     *
     * @param query Consulta SQL en formato String.
     * @return El número de filas afectadas por la consulta.
     */
    public int executeUpdate(String query) {
        Statement stmt = null;
        try {
            stmt = this.connection.createStatement();
            int rowsAffected = stmt.executeUpdate(query);

            if (this.onQueryExecuted != null) {
                this.onQueryExecuted.accept("Consulta ejecutada: " + query + " (" + rowsAffected + " filas afectadas)");
            }

            return rowsAffected;
        } catch (SQLException e) {
            if (this.onError != null) {
                this.onError.accept("Error en la consulta: " + e.getMessage());
            }

            return 0;
        } finally {
            // Asegúrate de cerrar el Statement manualmente
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    if (this.onError != null) {
                        this.onError.accept("Error al cerrar Statement: " + e.getMessage());
                    }
                }
            }
        }
    }


    /**
     * Cierra la conexión con la base de datos si está abierta.
     */
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            if (onError != null) onError.accept("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
