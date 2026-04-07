package com.ejemplo.servlet;

import com.ejemplo.model.CarritoItem;
import com.ejemplo.model.Producto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet({"/carrito", "/confirmacion"})
public class CarritoServlet extends HttpServlet {

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
        String uri = request.getRequestURI();
        if (uri.endsWith("/confirmacion")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/confirmacion.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Map<Integer, CarritoItem> carrito = obtenerCarrito(request.getSession());
        double total = carrito.values().stream().mapToDouble(CarritoItem::getSubtotal).sum();
        request.setAttribute("items", carrito.values());
        request.setAttribute("total", total);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/carrito.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();
        Map<Integer, CarritoItem> carrito = obtenerCarrito(session);

        if ("agregar".equals(accion)) {
            int id = parseIntSeguro(request.getParameter("id"));
            Producto producto = buscarProducto(id);
            if (producto != null) {
                carrito.compute(id, (k, item) -> {
                    if (item == null) {
                        return new CarritoItem(producto, 1);
                    }
                    item.aumentar(1);
                    return item;
                });
            }
            response.sendRedirect(request.getContextPath() + "/catalogo");
            return;
        }

        if ("eliminar".equals(accion)) {
            int id = parseIntSeguro(request.getParameter("id"));
            carrito.remove(id);
            response.sendRedirect(request.getContextPath() + "/carrito");
            return;
        }

        if ("vaciar".equals(accion)) {
            carrito.clear();
            response.sendRedirect(request.getContextPath() + "/carrito");
            return;
        }

        if ("confirmar".equals(accion)) {
            double total = carrito.values().stream().mapToDouble(CarritoItem::getSubtotal).sum();
            session.setAttribute("ultimoTotal", total);
            carrito.clear();
            response.sendRedirect(request.getContextPath() + "/confirmacion");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/carrito");
    }

    @SuppressWarnings("unchecked")
    private Map<Integer, CarritoItem> obtenerCarrito(HttpSession session) {
        Object data = session.getAttribute("carrito");
        if (data instanceof Map<?, ?> map) {
            return (Map<Integer, CarritoItem>) map;
        }
        Map<Integer, CarritoItem> nuevo = new LinkedHashMap<>();
        session.setAttribute("carrito", nuevo);
        return nuevo;
    }

    private Producto buscarProducto(int id) {
        return productos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    private int parseIntSeguro(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
}
