<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Abhinayak Swar
  Date: 6/4/13
  Time: 8:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<c:choose>
    <c:when test="${sessionScope.isAdmin==true}">
        <c:set var="download" value="/ridisearch/admin/download" />
        <c:set var="updateItem" value="/ridisearch/admin/updateItem" />
        <c:set var="deleteItem" value="/ridisearch/admin/deleteItem" />
    </c:when>
    <c:otherwise>
        <c:set var="download" value="/ridisearch/user/download" />
        <c:set var="updateItem" value="/ridisearch/user/updateItem" />
        <c:set var="deleteItem" value="/ridisearch/user/deleteItem" />
    </c:otherwise>
</c:choose>
<div class="container-fluid">


    <div class="row-fluid marketing">
        <p class="lead offset1 text-info">Uploaded items</p>
        <c:set var="i" value="0" />
        <c:forEach items="${itemList}" var="item">
            <hr>
            <c:set var="i" value="${i+1}" />
            <div class="row-fluid">
                <div class="span8 offset1">
                    <a href="<c:url value="${download}?id=${item.id}"/>" title="Click to download"><h4>Title : ${item.itemName}</h4></a>
                    <p>Status :
                        <c:choose>
                            <c:when test="${item.isPrivate}">Private</c:when>
                            <c:otherwise>Public</c:otherwise>
                    </c:choose></p>
                    <p><a href="<c:url value="${updateItem}?id=${item.id}"/>" title="Click to View Detail"> View Detail</a> &nbsp;&nbsp;
                    <a href="<c:url value="${deleteItem}?id=${item.id}&private=${item.isPrivate}"/>" title="Click to Delete" onclick="return confirm('Are you sure you want to delete this item.');"> Delete</a></p>
                </div>

            </div>

        </c:forEach>
    </div>

    <hr>


</div>
</body>
</html>