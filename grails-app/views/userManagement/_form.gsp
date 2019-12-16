<%@ page import="ca.georgebrown.security.Appuser; ca.georgebrown.security.Approle" %>

<table class="table-bordered" align="center">
    <tr>
        <th>User Name</th>
        <td>
            <g:if test="${user.authenticationType == ca.georgebrown.security.Appuser.AUTHENTICATION_TYPE.DAO}">
                <input type="text" value="${user?.username}" name="username">
            </g:if>
            <g:else>
                ${user?.username}
            </g:else>
        </td>
    </tr>
    <g:if test="${user.authenticationType == ca.georgebrown.security.Appuser.AUTHENTICATION_TYPE.DAO}">
        <tr>
            <th>First Name</th>
            <td>
                <input type="text" value="${user?.firstName}" name="firstName">
            </td>
        </tr>
        <tr>
            <th>Last Name</th>
            <td>
            <input type="text" value="${user?.lastName}" name="lastName">
        </tr>
    </g:if>
    <g:else>
        <tr>
               <td>
               <th>Name</th>
        ${user?.displayName}
        </td>
     </tr>
    </g:else>
    <tr>
        <th>Email</th>
<g:if test="${user.authenticationType == ca.georgebrown.security.Appuser.AUTHENTICATION_TYPE.DAO}">
    <td><input type="email" value="${user?.email}" name="email"></td>
</g:if>
    <g:else>
        <td><a href="mailto:${user?.email}">${user?.email}</a></td>
    </g:else>
    </tr>
    <tr>
        <th>Enabled</th>
        <td><g:checkBox name="enabled" value="${user?.enabled}">Enabled</g:checkBox></td>
    </tr>
    <g:if test="${user.authenticationType == ca.georgebrown.security.Appuser.AUTHENTICATION_TYPE.DAO}">
        <tr>
            <th>Reset Password</th>
            <td><input type="password" value="${Appuser.NO_CHG_PASSWORD}" name="resetPassword"></td>
        </tr>
    </g:if>

    <tr>
        <th>Roles</th>
        <td>
            <g:each in="${Approle.list()}" var="role">
                <g:checkBox name="role.${role.id}" value="${role in user.authorities}"></g:checkBox> ${role.name}<br/>
            </g:each>
        </td>

    </tr>

</table>
