# 📋 Sistema de Gestión de Almacén

- **Asignatura**: Programación l.

## 🛠 Tecnologías Utilizadas
- **Java 17** (JDK).
- **Swing** (Interfaz gráfica).
- **SQL Server** (Base de datos).
- **Patrones de Diseño**: Singleton, DAO.

## 🚀 Instalación y Ejecución

### Requisitos Previos:
- SQL Server instalado.
- Java 17 o superior.
- Librería [Microsoft JDBC Driver](https://learn.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver16) en `lib/`.

### Clonar el repositorio:
```bash
  git clone https://github.com/saidabel26/SistemaDeGestionDeProductosDeAlmacen.git
```

## 🔧 Configurar la Base de Datos
**Ejecuta en MySQL:**

    CREATE DATABASE AlmacenDB;
    GO

    USE AlmacenDB;
    GO

    CREATE TABLE usuarios (
    usuario VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    telefono VARCHAR(15),
    correo VARCHAR(100),
    contrasena VARCHAR(100)
    );
    GO

    CREATE TABLE productos (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100),
    marca VARCHAR(50),
    categoria VARCHAR(50),
    precio DECIMAL(10,2),
    cantidad INT
    );

## ▶️ Ejecución
1. **Importar**: En tu IDE, abre la carpeta `SistemaDeGestionDeProductosDeAlmacen`.
2. **Ejecutar**:
    - Clase principal: `src/Main.java`.
    - Dependencia: Asegúrate de tener `msssql-jdbc-(versión).jre11.jar` en `lib/`.

## 📂 Estructura del Proyecto
    SistemaDeGestionDeProductosDeAlmacen/
    ├── lib/
    │ └── msssql-jdbc-(versión).jre11.jar
    ├── src/
    │ ├── dao/
    │ │ ├── ProductoDAO.java
    │ │ └── UsuarioDAO.java
    │ ├── modelo/
    │ │ ├── Producto.java
    │ │ └── Usuario.java
    │ ├── vista/
    │ │ ├── GestionProductos.java
    │ │ ├── GestionUsuarios.java
    │ │ ├── LoginFrame.java
    │ │ ├── PrincipalFrame.java
    │ │ └── RegistroFrame.java
    │ ├── util/
    │ │ ├── DatabaseConnection.java
    │ │ └── TestConnection.java
    │ └── Main.java
    ├── .gitignore
    └── README.md

## 🛠 Guía de Soluciones a Problemas con la Conexión a SQL Server

    -- =============================================
    -- 1. CONFIGURACIÓN INICIAL DEL SERVIDOR SQL
    -- =============================================
    
    -- Verificar estado del puerto
    EXEC xp_readerrorlog 0, 1, N'Server is listening on';
    
    -- Ver información de la instancia
    SELECT @@SERVERNAME AS [Server Name], 
           @@SERVICENAME AS [Service Name];
    
    -- =============================================
    -- 2. SOLUCIÓN ERROR "TCP/IP CONNECTION FAILED"
    -- =============================================
    
    /* Pasos en SQL Server Configuration Manager:
    1. SQL Server Network Configuration → Protocols for [INSTANCIA]
    2. Habilitar TCP/IP
    3. IP Addresses → IPAll → TCP Port = 1433
    4. Reiniciar servicio SQL Server */
    
    -- =============================================
    -- 3. CONFIGURACIÓN DE FIREWALL (POWERSHELL ADMIN)
    -- =============================================
    
    New-NetFirewallRule -DisplayName "SQLServer-1433" `
        -Direction Inbound `
        -Protocol TCP `
        -LocalPort 1433 `
        -Action Allow;
    
    -- =============================================
    -- 4. SOLUCIÓN ERROR "LOGIN FAILED FOR USER"
    -- =============================================
    
    -- Crear usuario con permisos completos
    USE master;
    GO
    CREATE LOGIN app_user WITH PASSWORD = 'PasswordSegura123!';
    GO
    USE AlmacenDB;
    GO
    CREATE USER app_user FOR LOGIN app_user;
    GO
    GRANT CONTROL ON DATABASE::AlmacenDB TO app_user;
    GO
    
    -- =============================================
    -- 5. CADENA DE CONEXIÓN RECOMENDADA
    -- =============================================
    
    /*
    public class DatabaseConnection {
        // ...
        private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=AlmacenDB;encrypt=true;trustServerCertificate=true";
        private static final String USUARIO = "(TUCREDENCIAL)";
        private static final String CONTRASENA = "(TUCREDENCIAL)";
        // ...
    }
    */
    
    -- =============================================
    -- 6. COMANDOS ÚTILES PARA DIAGNÓSTICO
    -- =============================================
    
    -- Verificar conexión con telnet (CMD Admin)
    /*
    telnet 127.0.0.1 1433
    */
    
    -- Ver procesos usando el puerto
    /*
    netstat -ano | findstr 1433
    */
    
    -- Habilitar conexiones remotas en SQL Server
    /*
    EXEC sp_configure 'remote access', 1;
    RECONFIGURE;
    */