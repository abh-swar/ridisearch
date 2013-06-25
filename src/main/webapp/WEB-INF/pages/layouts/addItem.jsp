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
        <c:set var="addItem" value="/ridisearch/admin/saveItem" />
    </c:when>
    <c:otherwise>
        <c:set var="addItem" value="/ridisearch/user/saveItem" />
    </c:otherwise>
</c:choose>
<script type="text/javascript">
    $(document).ready(function() {
        $("#file").focus();
    });

    function validateUploadFile() {
        if ($("#file").val() == "") {
            alert("File cannot be empty!!");
            return false;
        }
        if ($("input[name=access]").is(':checked')==false) {
            alert("The item must be either public or private!!");
            return false;
        }
        return true;
    }
</script>
<div class="container">
    <form class="form-signin" name="editUser" id="editUser" method="post" action="${addItem}" enctype="multipart/form-data" onsubmit="return validateUploadFile();">
        <h2 class="form-signin-heading">Please upload a file</h2>
        File:<span class="info-required"> *</span> <input required type="file" class="input-block-level" name="files[0]" />
        <div style="margin-bottom:15px;">
            <input required type="radio"  name="access" value="private" />&nbsp;Private&nbsp;&nbsp;&nbsp;</label>
            <input required type="radio"  name="access" value="public" />&nbsp;Public
        </div>
        <label><input class="btn btn-large btn-primary" type="submit" value="Upload"/></label>
    </form>
</div>
</body>
</html>