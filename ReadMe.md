# Componente ADAT

Este proyecto es un componente desarrollado en Java que facilita la interacción con bases de datos y proporciona funcionalidades específicas para la gestión de datos en aplicaciones.

## Características

- Conexión sencilla a bases de datos.
- Operaciones CRUD básicas.
- Integración con JDBC.
- Manejo de excepciones optimizado.

## Requisitos

- Java 11 o superior
- Maven
- Base de datos compatible con JDBC

## Instalación

1. Clona el repositorio:
   ```sh
   git clone https://github.com/Alexfh94/componenteADAT.git
   ```
2. Entra en la carpeta del proyecto:
   ```sh
   cd componenteADAT
   ```
3. Compila el proyecto con Maven:
   ```sh
   mvn clean install
   ```

## Uso

### Ejemplo de conexión a la base de datos
```java
DatabaseConnector connector = new DatabaseConnector("jdbc:mysql://localhost:3306/miBase", "usuario", "contraseña");
Connection conn = connector.getConnection();
```

### Ejemplo de consulta
```java
ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM usuarios");
while (rs.next()) {
    System.out.println("Usuario: " + rs.getString("nombre"));
}
```

## Contribución

Si deseas contribuir al proyecto, por favor sigue estos pasos:
1. Haz un fork del repositorio.
2. Crea una nueva rama (`feature-nueva-funcionalidad`).
3. Realiza tus cambios y haz un commit (`git commit -m 'Añadir nueva funcionalidad'`).
4. Sube los cambios a tu fork y abre un Pull Request.

## Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.

## Autor

- **Alejandro De La Fuente Heras**  
  - GitHub: [Alexfh94](https://github.com/Alexfh94)
