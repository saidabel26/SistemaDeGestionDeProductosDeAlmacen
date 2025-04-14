package modelo;

public class Usuario {
    private String usuario;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String contrasena;

    public Usuario(String usuario, String nombre, String apellido, String telefono, String correo, String contrasena) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    // Getters
    public String getUsuario() { return usuario; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getContrasena() { return contrasena; }
}