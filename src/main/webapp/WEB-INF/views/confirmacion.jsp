<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Confirmación</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css" />
</head>
<body>
<div class="container">
    <h1>Pedido Confirmado</h1>
    <p>Gracias por tu compra.</p>
    <p>Total registrado: $ ${sessionScope.ultimoTotal}</p>
    <a href="${pageContext.request.contextPath}/catalogo" class="btn-sec">Seguir comprando</a>
</div>
</body>
</html>