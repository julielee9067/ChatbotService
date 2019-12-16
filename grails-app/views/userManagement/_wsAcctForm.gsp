<%@ page import="ca.georgebrown.security.Appuser; ca.georgebrown.security.Approle" %>

<table class="table"  align="center">
    <tr>
        <th>User Name</th>
        <td>
                <input class="input input-lg" type="text" value="${user?.username}" name="username">
        </td>
    </tr>
        <tr>
            <th>First Name</th>
            <td>
                <input  class="input input-lg" type="text" value="${user?.firstName}" name="firstName">
            </td>
        </tr>
        <tr>
            <th>Last Name</th>
            <td>
            <input  class="input input-lg" type="text" value="${user?.lastName}" name="lastName">
        </tr>
    <tr>
        <th>Email</th>
    <td><input  class="input input-lg" type="email" value="${user?.email}" name="email"></td>
    </tr>
        <tr>
            <th>Reset Password</th>
            <td><input class="input input-lg" type="password" value="${Appuser.NO_CHG_PASSWORD}" name="resetPassword"></td>
        </tr>

</table>
