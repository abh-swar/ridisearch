<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${sessionScope.isAdmin==true}">
        <c:set var="editAction" value="/ridisearch/admin/edit" />
    </c:when>
    <c:otherwise>
        <c:set var="editAction" value="/ridisearch/user/edit" />
    </c:otherwise>
</c:choose>

<div class="span9">
    <div class="hero-unit">
        <h4 class="pull-left">Profile</h4>
        <a href="${editAction}?id=${user.id}" role="button" data-toggle="modal" class="pull-right icon-edit" title="Edit Your Profile"></a>
        <table class="table table-striped">
            <tr><td>Name</td><td>:</td><td><c:if test="${user!=null}">${user.name}</c:if></td></tr>
            <tr><td>Username</td><td>:</td><c:if test="${user!=null}"><td>${user.userName}</c:if></td></tr>
            <tr><td>Address</td><td>:</td><c:if test="${user.address!=null}"><td>${user.address}</c:if></td></tr>
            <tr><td>Phone Number</td><td>:</td><c:if test="${user.phoneNumber!=null}"><td>${user.phoneNumber}</c:if></td></tr>
        </table>
    </div>
</div>