<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<title>Welcome</title>

<link rel="stylesheet"
              href="<c:url value='/resources/estilo/estilo.css' /> ">
</head>

<body>
	<c:if test="${coin!=null}">
    	<div class="notification">Coin ${coin} was added to your watchlist</div>
    </c:if>
    <p style="text-align: right;"><a href="${pageContext.request.contextPath}/watchlist">My watchlist</a></p>
	<!-- <hr>  -->
	<table border="1" width="100%">
		<tr>
			<th width="15%">Coin</th>
			<th width="15%">Low</th>
			<th width="15%">High</th>
			<th width="15%">Current</th>
			<th width="15%">% Min</th>
			<th width="15%">% Max</th>
			<th width="7%"></th>
		</tr>
		<c:forEach var="coin" items="${data.getCoinData()}">
			<tr>
				<td width="15%">${coin.getName()}</td>
				<td width="15%">${coin.getP24lS()}</td>
				<td width="15%">${coin.getP24hS()}</td>
				<td width="15%">${coin.getCurrentS()}</td>
				<td width="15%">${coin.getPeMinS()}</td>
				<td width="15%">${coin.getPeMaxS()}</td>
				<td width="7%"><a
					href="${pageContext.request.contextPath}/savecoin?coinname=${coin.getName()}">Save
						coin</a></td>
			</tr>
		</c:forEach>
	</table>

</body>

</html>