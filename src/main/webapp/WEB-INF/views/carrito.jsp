<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Carrito</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css" />
</head>
<body>
<div class="container">
    <h1>Carrito de Compras</h1>

    <c:choose>
        <c:when test="${empty items}">
            <p>Tu carrito está vacío.</p>
        </c:when>
        <c:otherwise>
            <table>
                <thead>
                <tr>
                    <th>Producto</th>
                    <th>Cantidad</th>
                    <th>Precio</th>
                    <th>Subtotal</th>
                    <th>Acción</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${items}" var="item">
                    <tr>
                        <td>${item.producto.nombre}</td>
                        <td>${item.cantidad}</td>
                        <td>$ ${item.producto.precio}</td>
                        <td>$ ${item.subtotal}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/carrito" method="post">
                                <input type="hidden" name="accion" value="eliminar" />
                                <input type="hidden" name="id" value="${item.producto.id}" />
                                <button type="submit" class="btn-danger">Quitar</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <p class="total">Total: $ ${total}</p>

            <div class="acciones">
                <form action="${pageContext.request.contextPath}/carrito" method="post">
                    <input type="hidden" name="accion" value="vaciar" />
                    <button type="submit" class="btn-danger">Vaciar carrito</button>
                </form>
                <form action="${pageContext.request.contextPath}/carrito" method="post">
                    <input type="hidden" name="accion" value="confirmar" />
                    <button type="submit">Confirmar pedido</button>
                </form>
            </div>
        </c:otherwise>
    </c:choose>

    <a href="${pageContext.request.contextPath}/catalogo" class="btn-sec">Volver al catálogo</a>
</div>
</body>
</html>