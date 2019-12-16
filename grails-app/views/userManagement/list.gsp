
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>GBC Web Site Service - Users</title>


    <style type="text/css" media="screen">
    .center {
        text-align: center;
    }

    th {
        background-color: #337ab7;
        color: white;
    }

    .table-striped>tbody>tr:nth-child(odd)>td{
        background-color: #f2f6fc;
    }

    </style>

</head>
<body>
<g:javascript>
    function setAuthType(selection) {
        if(selection.value == 'DOA') {
            $("#useridLabel").html("User Id :");
            $("#addUserButton").val("Add Local Account");
        } else {
            $("#useridLabel").html("GBC Id: ")
            $("#addUserButton").val("Add GBC User");
        }

    }
</g:javascript>
<g:render template='/layouts/navigation' model="[which:'userManagement']"/>

<div class="container-fluid">
    <div id="list-users" class="content scaffold-list" role="main">
        <h1 class="pageTitle center">List of Users</h1> <br>
        <g:if test="${flash.message}">
            <div class="warningMsg" role="status">${flash.message}</div>
        </g:if>
        <div class="center">
            <g:form method="post" action="list" id="form">

                <button type="submit" style="display: none" id="submitForm"></button>
            </g:form>
            <g:form method="post" action="newUser">
                <g:radioGroup labels="['GBC User','Local Account']"  values="['LDAP','DOA']"  value='LDAP'
                              onclick="setAuthType(this)"
                              id="authType" name="authType">
                    <p><g:message code="${it.label}" />: ${it.radio}</p>
                </g:radioGroup>
                <span id="useridLabel">GBC Id: </span><input type="text" value="" name="username">  <input type="submit" id="addUserButton" value="Add GBC User" name="addUser">
            </g:form>
            <hr/>
        </div>
        <table class="table-hover table-striped table-bordered" align="center">
            <thead>
            <tr>
                <th>Select</th>
                <th>Type</th>
                <th>User Id</th>
                <th>Full Name</th>
                <th>Email</th>
                <th>Enabled</th>
                <th>Roles</th>

            </tr>
            </thead>
            <tbody>
            <g:each in="${ca.georgebrown.security.Appuser.list(sort:'firstName')}" status="i" var="user">

                <tr class="table table-striped">
                    <td><g:link class="userManagement" action="edit" id="${user.id}">Edit</g:link></td>
                    <td>${user.authenticationType}</td>
                    <td>${user.username}</td>

                    <td>${user.displayName}</td>

                    <td><a href="mailto:${user.email}">${user.email}</a></td>
                    <td>${user.enabled?'YES':'NO'}</td>
                    <td>${user.authorities*.name.join(", ")}</td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
