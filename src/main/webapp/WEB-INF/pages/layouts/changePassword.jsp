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
        <c:set var="saveNewPassword" value="/ridisearch/admin/saveNewPassword" />
    </c:when>
    <c:otherwise>
        <c:set var="saveNewPassword" value="/ridisearch/user/saveNewPassword" />
    </c:otherwise>
</c:choose>
<html>
<body>
<script type="text/javascript">
    $(document).ready(function() {
        $("#oldPassword").focus();
    });

    function validateRegistrationForm() {
        if ($("#oldPassword").val() == "" || $("#password1").val() == "" || $("#password2").val()=="") {
            alert("All the fields are mandatory and cannot be empty!!");
            return false;
        }

        if ($("#password1").val() != $("#password2").val()) {
            alert("The two passwords do not match!!");
            return false;
        }
        return true;
    }
</script>
<div class="container">
    <form class="form-signin" name="changePassword" id="changePassword" method="post" action="${saveNewPassword}" onsubmit="return validateRegistrationForm();">
        <h2 class="form-signin-heading">Please enter the old and new passwords</h2>
        Old password:<span class="info-required"> *</span><input required type="password" class="input-block-level" name="oldPassword" id="oldPassword" placeholder="Old Password"/>
        Password:<span class="info-required"> *</span><input required type="password" class="input-block-level" name="password1" id="password1" placeholder="New Password"/>
        Re-enter Password:<span class="info-required"> *</span><input required type="password" class="input-block-level" name="password2" id="password2" placeholder="Re-enter New Password"/>
        <input class="btn btn-large btn-primary" type="submit" value="Change Password"/>
    </form>
</div>
</body>
</html>