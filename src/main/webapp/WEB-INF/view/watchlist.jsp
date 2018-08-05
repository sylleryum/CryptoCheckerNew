<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Watchlist</title>
</head>
<body>
<h3>Watchlist - Click on any coin to check if the 5% were reached</h3>
<p style="text-align: right;"><a href="${pageContext.request.contextPath}/">Return</a></p>
<p style="text-align: right;"><a href="${pageContext.request.contextPath}/checkall">Check all</a></p>
    <table border="1" width="100%">
        <tr>
            <th width="15%">Coin</th>
            <th width="15%">Low</th>
            <th width="15%">High</th>
            <th width="15%">Current</th>
            <th width="15%">% Min</th>
            <th width="15%">% Max</th>
            <th width="15%">Last checked</th>
            <th width="15%">Reached?</th>
        </tr>
        <c:forEach var="coin" items="${coinList}">
        <tr <c:if test="${coin.isBateu()==true}">bgcolor="#7fff00" </c:if> >
            <td width="15%">
                <c:choose>
                    <c:when test="${coin.isBateu()!=true}">
                        <a href="${pageContext.request.contextPath}/checker?id=${coin.getId()}">${coin.getName()}</a>
                    </c:when>
                   <c:when test="${coin.isBateu()==true}">
                       ${coin.getName()}
                   </c:when>
                </c:choose>
            </td>
            <td width="15%">${coin.getP24lS()}</td>
            <td width="15%">${coin.getP24hS()}</td>
            <td width="15%">${coin.getCurrentS()}</td>
            <td width="15%">${coin.getPeMinS()}</td>
            <td width="15%">${coin.getPeMaxS()}</td>
            <td width="15%">${coin.getlCheckDate()}</td>
            <td width="15%">${coin.getQuando()}</td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>