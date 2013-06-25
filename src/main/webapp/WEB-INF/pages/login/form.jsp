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

    function validate() {
        if ($("#userName").val() == "" || $("#password").val() == "") {
            alert("Username and password cannot be empty!!");
            return false;
        }
        return true;
    }

</script>
<div class="container">
    <form class="form-signin" name="logInForm" id="logInForm" method="post" action="/ridisearch/login/index"
          onsubmit="return validate();">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input required type="text" class="input-block-level" name="userName" id="userName" placeholder="User Name"/>
        <input required type="password" class="input-block-level" name="password" id="password" placeholder="Password"/>
        <input class="btn btn-large btn-primary" type="submit" value="Log In"/>
    </form>
</div>
</body>
</html>