package com.ejemplo.servlet;

import com.ejemplo.model.Producto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {
    private final List<Producto> productos = Arrays.asList(
            new Producto(1, "Laptop Dell", "Computadores", 2500000),
            new Producto(2, "Mouse Logitech", "Periféricos", 85000),
            new Producto(3, "Teclado Mecánico","Periféricos", 220000),
            new Producto(4, "Monitor 24", "Computadores", 650000),
            new Producto(5, "Audífonos Sony", "Audio", 180000),
            new Producto(6, "Webcam HD", "Periféricos", 95000)
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String buscar = normalizar(request.getParameter("buscar"));
        String categoria = normalizar(request.getParameter("categoria"));

        List<Producto> filtrados = productos.stream()
                .filter(p -> buscar.isEmpty() || p.getNombre().toLowerCase(Locale.ROOT).contains(buscar))
                .filter(p -> categoria.isEmpty() || p.getCategoria().toLowerCase(Locale.ROOT).equals(categoria))
                .collect(Collectors.toList());

        request.setAttribute("productos", filtrados);
        request.setAttribute("buscar", request.getParameter("buscar") == null ? "" : request.getParameter("buscar"));
        request.setAttribute("categoria", request.getParameter("categoria") == null ? "" : request.getParameter("categoria"));
        request.setAttribute("categorias", productos.stream().map(Producto::getCategoria).distinct().collect(Collectors.toList()));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/catalogo.jsp");
        dispatcher.forward(request, response);
    }

    private String normalizar(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.trim().toLowerCase(Locale.ROOT);
    }
}
