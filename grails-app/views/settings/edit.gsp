
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>GBC Banner Web Service - Setting Edit</title>
</head>
<body>
<g:render template='/layouts/navigation' model="[which:'config']"/>
<div id="edit-appSettings" class="container-fluid content scaffold-list" role="main">
        <div class="card border-warning">
            <div class=" text-center text-white bg-warning ">Edit Setting</div>
        <div class="card-header">
            <h1 class="card-title pageTitle">${settingsInstance.code}</h1>
        </div>
        <div class="card-body mx-auto mt-3 text-center">
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${settingsInstance}">
            <ul class="errors" role="alert">
                <g:eachError bean="${settingsInstance}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${settingsInstance?.id}" />
                <g:hiddenField name="version" value="${settingsInstance?.version}" />
                <fieldset class="form">
                    <g:render template="form"/>
                </fieldset>
                <fieldset class="buttons">
                    <g:actionSubmit class="save btn btn-primary" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
        </div>
    </div>
</div>
</body>
</html>
