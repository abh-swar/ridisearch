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

    function validateAddUserForm() {
        if ($("#name").val() == "" || $("#userName").val() == "") {
            alert("Full name and User name are mandatory fields and cannot be empty!!");
            return false;
        }
        if ($("input[name=roles]").is(':checked')==false) {
            alert("One of the roles must be chosen!");
            return false;
        }
        return true;
    }
</script>
<div class="container">
    <form class="form-signin" name="editUser" id="editUser" method="post" action="/ridisearch/admin/saveNewUser" onsubmit="return validateAddUserForm();">
        <h2 class="form-signin-heading">Please fill the form to add new user</h2>
        Full Name:<span class="info-required"> *</span> <input type="text" class="input-block-level" name="name" id="name" placeholder="Full Name"/>
        User Name:<span class="info-required"> *</span><input type="text" class="input-block-level" name="userName" id="userName" placeholder="User Name"/>
        Address:<input type="text" class="input-block-level" name="address" id="address" placeholder="Address"/>
        Phone Number:<input type="text" class="input-block-level" name="phoneNumber" id="phoneNumber" placeholder="Phone Number"/>
        <div style="margin-bottom:15px;">
            <input type="checkbox"  name="roles" value="ROLE_ADMIN" />&nbsp;Admin&nbsp;&nbsp;&nbsp;</label>
            <input type="checkbox"  name="roles" value="ROLE_USER" />&nbsp;User
        </div>
        <label><input class="btn btn-large btn-primary" type="submit" value="Add User"/></label>
    </form>
</div>
</body>
</html>