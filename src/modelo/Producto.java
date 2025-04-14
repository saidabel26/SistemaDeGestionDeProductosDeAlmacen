package modelo;

public class Producto {
    private int id;
    private String nombre;
    private String marca;
    private String categoria;
    private double precio;
    private int cantidad;

    public Producto(int id, String nombre, String marca, String categoria, double precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getMarca() { return marca; }
    public String getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }
}