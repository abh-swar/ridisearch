<%--
  Created by IntelliJ IDEA.
  User: Abhinayak Swar
  Date: 5/25/13
  Time: 10:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
    This page contains header and footer for users that are logged in
--%>
<html>
<head>
    <title>
        Welcome member
    </title>
    <jsp:include page="resources.jsp" />
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <button data-target=".nav-collapse" data-toggle="collapse" class="btn btn-navbar" type="button">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="brand active" href="<c:url value="${homeAction}"/>">Ridisearch</a>
            <c:choose>
                <c:when test="${sessionScope.isAdmin==true}">
                    <c:set var="homeAction" value="/ridisearch/admin/index" />
                    <c:set var="searchAction" value="/ridisearch/admin/search" />
                    <c:set var="addAction" value="/ridisearch/admin/addItems" />
                    <c:set var="profileAction" value="/ridisearch/admin/profile" />
                </c:when>
                <c:otherwise>
                    <c:set var="homeAction" value="/ridisearch/user/index" />
                    <c:set var="searchAction" value="/ridisearch/user/search" />
                    <c:set var="addAction" value="/ridisearch/user/addItems" />
                    <c:set var="profileAction" value="/ridisearch/user/profile" />
                </c:otherwise>
            </c:choose>
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li class="active"><a href="<c:url value="${homeAction}"/>">Home</a></li>
                    <li><a href="<c:url value="${profileAction}"/>">Profile</a></li>
                    <li><a href="${addAction}">Add Items</a></li>
                    <li><a href="<c:url value="/ridisearch/user/about"/>">About</a></li>
                    <c:if test="${sessionScope.isAdmin==true}">
                        <li><a href="<c:url value="/ridisearch/admin/panel"/>">Admin Panel</a></li>
                    </c:if>
                    <li><a href="<c:url value="/ridisearch/logout/index"/>">Logout</a></li>
                </ul>

                <form class="navbar-form pull-right" name="generalSearch" action="${searchAction}" method="post">
                    <input type="text" class="span2" placeholder="Search" name="searchBar"  />
                    <button type="submit" class="btn">Submit</button>
                </form>
            </div><!--/.nav-collapse -->
        </div>
    </div>
</div>
<br />
<br />
<br />
<c:if test="${message!=null}">
    <div class="message-box">${message}</div>
</c:if>
<br />
<br />
<decorator:body />

<jsp:include page="footer.jsp" flush="true"/>

</body>
</html>