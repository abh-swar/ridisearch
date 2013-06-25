<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Abhinayak Swar
  Date: 6/4/13
  Time: 8:14 AM
  To change this template use File | Settings | File Templates.
--%>
<html>
<body>
<c:choose>
    <c:when test="${sessionScope.isAdmin==true}">
        <c:set var="saveUser" value="/ridisearch/admin/save" />
    </c:when>
    <c:otherwise>
        <c:set var="saveUser" value="/ridisearch/user/save" />
    </c:otherwise>
</c:choose>
<script type="text/javascript">
    $(document).ready(function() {
        $("#userName").focus();
    });

    function validateRegistrationForm() {
        if ($("#name").val() == "" || $("#userName").val() == "") {
            alert("Full name and User name are mandatory fields and cannot be empty!!");
            return false;
        }
        return true;
    }
</script>
<div class="container">
    <form class="form-signin" name="editUser" id="editUser" method="post" action="${saveUser}?id=${user.id}" onsubmit="return validateRegistrationForm();">
        <h2 class="form-signin-heading">Please fill the form to register</h2>
        Full Name:<span class="info-required"> *</span> <input type="text" class="input-block-level" name="name" id="name" value="${user.name}" placeholder="Full Name"/>
        User Name:<span class="info-required"> *</span><input type="text" class="input-block-level" name="userName" id="userName" value="${user.userName}" placeholder="User Name"/>
        Address:<input type="text" class="input-block-level" name="address" id="address" value="${user.address}" placeholder="Address"/>
        Phone Number:<input type="text" class="input-block-level" name="phoneNumber" id="phoneNumber" value="${user.phoneNumber}" placeholder="Phone Number"/>
        <input class="btn btn-large btn-primary" type="submit" value="Save"/>
    </form>
</div>
</body>
</html>