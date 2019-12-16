<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>GBC Banner Web Service - User Edit</title>
</head>
<body>
<g:render template='/layouts/navigation' model="[which:'userManagement']"/>
<div id="edit-appSettings" class="content scaffold-edit center" role="main">
    <div class="container-fluid">
        <br>
    </div>
    <h1 class="pageTitle" style="text-align: center">Edit User - ${user.displayName}</h1>

    <g:if test="${flash.message}">
        <div class="message" role="status" style="text-align: center">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${user}">
        <ul class="errors" role="alert">
            <g:eachError bean="${user}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <div class="container-fluid">
    <g:form method="post" >
        <g:hiddenField name="id" value="${user?.id}" />
        <g:hiddenField name="version" value="${user?.version}" />
        <fieldset class="form">
            <g:render template="wsAcctForm"/>
        </fieldset>
        <br>
        <fieldset class="buttons" style="text-align: center">
            <g:actionSubmit class="save btn btn-primary" action="wsAcctUpdate" value="Update" />
        </fieldset>
    </g:form>
    </div>
</div>
</body>
</html>
