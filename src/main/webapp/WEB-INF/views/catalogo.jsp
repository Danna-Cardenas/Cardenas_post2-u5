<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Catálogo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css" />
</head>
<body>
<div class="container">
    <h1>Catálogo de Productos</h1>

    <form class="filtros" action="${pageContext.request.contextPath}/catalogo" method="get">
        <input type="text" name="buscar" placeholder="Buscar por nombre" value="${buscar}" />
        <select name="categoria">
            <option value="">Todas las categorías</option>
            <c:forEach items="${categorias}" var="cat">
                <option value="${cat}" ${categoria == cat ? 'selected' : ''}>${cat}</option>
            </c:forEach>
        </select>
        <button type="submit">Filtrar</button>
        <a href="${pageContext.request.contextPath}/carrito" class="btn-sec">Ver carrito</a>
    </form>

    <div class="grid">
        <c:forEach items="${productos}" var="p">
            <div class="card">
                <h3>${p.nombre}</h3>
                <p>Categoría: ${p.categoria}</p>
                <p class="precio">$ ${p.precio}</p>
                <form action="${pageContext.request.contextPath}/carrito" method="post">
                    <input type="hidden" name="accion" value="agregar" />
                    <input type="hidden" name="id" value="${p.id}" />
                    <button type="submit">Agregar al carrito</button>
                </form>
            </div>
        </c:forEach>
        <c:if test="${empty productos}">
            <p>No se encontraron productos con los filtros seleccionados.</p>
        </c:if>
    </div>
</div>
</body>
</html>
