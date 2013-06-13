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
<%--
    This page contains header and footer for users that are logged in
--%>
<html>
<head>
    <title>
        Home Page
    </title>
</head>
<body>

<div class="header">
    <ul>
        <li>Home logged in</li>
        <li>
            <form name="generalSearch" action="/dasboard/" method="post">
                <input type="text" name="searchBar" id="searchBar" placeholder="Search"/>
                <input type="submit" value="Search">
            </form>
        </li>
        <li>Home</li>
        <li>About us</li>
        <li>Sign out</li>
    </ul>
</div>

<div class="main">
    <decorator:body />
</div>

<div class="footer">Footer for logged in user</div>

</body>
</html>