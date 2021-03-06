<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Abhinayak Swar
  Date: 6/4/13
  Time: 8:14 AM
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Search Result</title>
</head>
<body>
<div class="container-fluid">

    <%--<div class="jumbotron">--%>
        <%----%>
    <%--</div>--%>
    <%--<hr>--%>

    <div class="row-fluid marketing">
        <p class="lead offset1 text-info">Search Result &nbsp;&nbsp;&nbsp;Total hits: ${hitsCount}</p>
        <c:set var="i" value="0" />
        <c:forEach items="${listOfHits}" var="hit">
            <hr>
            <c:set var="i" value="${i+1}" />
            <div class="row-fluid">
                <div class="span8 offset1">
                    <h4 class="text-info">Title : ${hit.fileName}</h4>
                    <p>Uploaded By : ${hit.uploadedBy}</p>
                    <p>Status : Public   <a href="/ridisearch/download?id=${hit.itemId}">Download</a></p>
                </div>
            </div>

        </c:forEach>
    </div>

    <hr>

</div>

</body>
</html>