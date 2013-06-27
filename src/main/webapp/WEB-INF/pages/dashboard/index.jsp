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
<div class="container-narrow">

    <hr>

    <div class="jumbotron">
        <h1>Search, upload, download photos, pdf, text files, MS-Word file and more!</h1>
        <p class="lead">Ridisearch is a online storage application that allows users to upload and download files, and do a quick search on the existing archives.</p>
        <a href="<c:url value="/ridisearch/register/index"/>" class="btn btn-large btn-success">Register Now!</a>
    </div>

    <hr>

    <div class="row-fluid marketing">
        <c:set var="i" value="0" />
        <c:forEach items="${itemList}" var="item">
            <c:set var="i" value="${i+1}" />
                <div class="row-fluid">
                    <div class="span8">
                        <h4>Title : ${item.itemName}</h4>
                        <p>Uploaded By : ${item.user.name}</p>
                        <p><a href="<c:url value="/ridisearch/download?id=${item.id}"/>"> Download</a></p>
                    </div>
                </div>
        </c:forEach>
    </div>

    <hr>


</div>
</body>
</html>