<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta name="decorator" content="master"/>
<div class="span13">
    <div class="hero-unit">
        <h4 class="pull-left">List</h4>
        <a href="/ridisearch/admin/addUser" role="button" data-toggle="modal" class="pull-right" title="Add New User">Add New User</a>
        <table class="table table-striped">
            <tr>
                <th>Name</th>
                <th>User Name</th>
                <th>Address</th>
                <th>Phone Number</th>
                <th>Action</th>
            </tr>
            <c:forEach items="${userList}" var="user">
                <tr>
                    <td>${user.name}</td>
                    <td>${user.userName}</td>
                    <td>${user.address}</td>
                    <td>${user.phoneNumber}</td>
                    <td><a href="/ridisearch/admin/edit?id=${user.id}" role="button" data-toggle="modal" class="icon-edit" title="Edit"></a>
                    </a>&nbsp; <a href="/ridisearch/admin/deleteUser?id=${user.id}" onclick="return confirm('Are you sure to delete?')" class="icon-remove" ></a></td>


                </tr>
            </c:forEach>
        </table>
    </div>
</div>