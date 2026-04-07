package com.ejemplo.model;

public class CarritoItem {
    private Producto producto;
    private int cantidad;

    public CarritoItem(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void aumentar(int delta) {
        this.cantidad += delta;
    }

    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }
}
