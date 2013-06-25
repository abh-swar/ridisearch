<%--
  Created by IntelliJ IDEA.
  User: Abhinayak Swar
  Date: 5/25/13
  Time: 10:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
    This page contains header and footer for users that are not logged in
--%>
<html>
<head>
    <title>
        Home Page
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
            <a class="brand" href="<c:url value="/ridisearch/index"/>">Ridisearch</a>
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li class="active"><a href="<c:url value="/ridisearch/about"/>">About</a></li>
                    <li><a href="<c:url value="/ridisearch/contact"/>">Contact</a></li>
                    <li><a href="<c:url value="/ridisearch/login/form"/>">Login</a></li>
                    <%--<li class="dropdown">--%>
                        <%--<a data-toggle="dropdown" class="dropdown-toggle" href="#">Dropdown <b class="caret"></b></a>--%>
                        <%--<ul class="dropdown-menu">--%>
                            <%--<li><a href="#">Action</a></li>--%>
                            <%--<li><a href="#">Another action</a></li>--%>
                            <%--<li><a href="#">Something else here</a></li>--%>
                            <%--<li class="divider"></li>--%>
                            <%--<li class="nav-header">Nav header</li>--%>
                            <%--<li><a href="#">Separated link</a></li>--%>
                            <%--<li><a href="#">One more separated link</a></li>--%>
                        <%--</ul>--%>
                    <%--</li>--%>
                </ul>
                <form class="navbar-form pull-right" name="generalSearch" action="/ridisearch/search/index" method="post">
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