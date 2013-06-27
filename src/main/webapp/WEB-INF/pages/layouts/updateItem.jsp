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
        <c:set var="saveUpdatedItem" value="/ridisearch/admin/saveUpdatedItem" />
    </c:when>
    <c:otherwise>
        <c:set var="saveUpdatedItem" value="/ridisearch/user/saveUpdatedItem" />
    </c:otherwise>
</c:choose>
<script type="text/javascript">
    $(document).ready(function() {
        $("#itemType").attr("disabled","disabled");
        $("#itemName").attr("disabled","disabled");
        $("#accessPriv").attr("disabled","disabled");
        $("#accessPub").attr("disabled","disabled");
        $("#createdBy").attr("disabled","disabled");
        <%--if (${itemMap.get(0).isPrivate=="true"}) {--%>
            <%--$("input[type=radio][value=private]").attr("checked","checked");--%>
        <%--} else {--%>
            <%--$("input[type=radio][value=public]").attr("checked","checked");--%>
        <%--}--%>
    });

    function validateRegistrationForm() {
        if ($("#itemName").val() == "" || $("#itemType").val() == "") {
            alert("Item name and item type are mandatory fields!");
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
        <%--<form class="form-signin" name="editUser" id="editUser" method="post" action="${saveUpdatedItem}?id=${item.id}" onsubmit="return validateRegistrationForm();">--%>
            <form class="form-signin">
            <h2 class="form-signin-heading">Item Detail</h2>
                Item Name:<span class="info-required"> *</span> <input required type="text" class="input-block-level" name="itemName" id="itemName" value="${item.itemName}" placeholder="Item Name"/>
                Item Type:<span class="info-required"> *</span><input required type="text" class="input-block-level" name="itemType" id="itemType" value="${item.itemType}" placeholder="Item Type"/>
                Created By:<span class="info-required"> *</span><input required type="text" class="input-block-level" name="createdBy" id="createdBy" value="${item.user.name}" placeholder="Item Type"/>
                Access:
                <div style="margin-bottom:15px;">
                    <input required type="radio" <c:if test="${item.isPrivate==true}">checked="checked" </c:if>
                           id="accessPriv" name="access" value="private" />&nbsp;Private&nbsp;&nbsp;&nbsp;</label>
                    <input required type="radio" <c:if test="${item.isPrivate==false}">checked="checked" </c:if>
                           id="accessPub" name="access" value="public" />&nbsp;Public
                </div>
        <%--<input class="btn btn-large btn-primary" type="submit" value="Save"/>--%>
       </form>
</div>
</body>
</html>