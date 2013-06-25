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
<script type="text/javascript">
    $(document).ready(function() {
        $("#userName").focus();
    });

    function validateRegistrationForm() {
        if ($("#name").val() == "" || $("#userName").val() == "" || $("#password1").val() == "" || $("#password2").val()=="") {
            alert("All the fields are mandatory and cannot be empty!!");
            return false;
        }
        return true;
    }
</script>
<div class="container">
    <form class="form-signin" name="logInForm" id="logInForm" method="post" action="/ridisearch/register/saveUser" onsubmit="return validateRegistrationForm();">
        <h2 class="form-signin-heading">Please fill the form to register</h2>
        Full Name:<span class="info-required"> *</span> <input type="text" class="input-block-level" name="name" id="name" placeholder="Full Name"/>
        User Name:<span class="info-required"> *</span><input type="text" class="input-block-level" name="userName" id="userName" placeholder="Email"/>
        Password:<span class="info-required"> *</span><input type="password" class="input-block-level" name="password1" id="password1" placeholder="Password"/>
        Re-enter Password:<span class="info-required"> *</span><input type="password" class="input-block-level" name="password2" id="password2" placeholder="Re-enter Password"/>
        <input class="btn btn-large btn-primary" type="submit" value="Log In"/>
    </form>
</div>
</body>
</html>