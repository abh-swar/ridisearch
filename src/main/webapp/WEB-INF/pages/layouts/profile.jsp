<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
<script type="text/javascript">
    function printItems() {
        $("#printThis").printElement({printMode: 'popup'});
    }
</script>
<c:choose>
    <c:when test="${sessionScope.isAdmin==true}">
        <c:set var="editAction" value="/ridisearch/admin/edit" />
    </c:when>
    <c:otherwise>
        <c:set var="editAction" value="/ridisearch/user/edit" />
    </c:otherwise>
</c:choose>

<div class="span9" id="printThis">
    <div class="hero-unit">
        <h4 class="pull-left">Profile</h4>
        <div class="row">
            <span class="pull-right">
                <a href="javascript:void(0)" class="icon-print" onclick="printItems()"></a>&nbsp;&nbsp;
                <a href="${editAction}?id=${user.id}" role="button" data-toggle="modal" class="icon-edit" title="Edit Your Profile"></a>
            </span>
        </div>
        <table class="table table-striped">
            <tr><td>Name</td><td>:</td><td><c:if test="${user!=null}">${user.name}</c:if></td></tr>
            <tr><td>Username</td><td>:</td><c:if test="${user!=null}"><td>${user.userName}</c:if></td></tr>
            <tr><td>Address</td><td>:</td><c:if test="${user.address!=null}"><td>${user.address}</c:if></td></tr>
            <tr><td>Phone Number</td><td>:</td><c:if test="${user.phoneNumber!=null}"><td>${user.phoneNumber}</c:if></td></tr>
        </table>
    </div>
</div>

</body>