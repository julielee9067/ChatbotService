<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>GBC Banner Web Services - Settings</title>
</head>

<body>
<g:render template='/layouts/navigation' model="[which: 'config']"/>
<div id="list-appSettings" class="container-fluid content scaffold-list" role="main">
    <div class="card">
        <div class="card-header">
            <h1 class="card-title pageTitle">Settings</h1>
        </div>

        <div class="card-body mx-auto mt-3 text-center">
            <g:if test="${flash.message}">
                <div class="warningMsg" role="status">${flash.message}</div>
            </g:if>

            <div class="table-responsive">
                    <table class="table-hover table-striped table-bordered" align="center">
                    <thead>
                    <tr>
                        <th>Code</th>
                        <th>Description</th>
                        <th>Type</th>
                        <th>Value</th>

                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${settingsInstanceList.sort { [it.seq] }}" status="i" var="settingsInstance">
                        <tr class="settingRec">
                            <td><g:link class="edit" action="edit"
                                        id="${settingsInstance.id}">${fieldValue(bean: settingsInstance, field: "code")}</g:link></td>

                            <td>${fieldValue(bean: settingsInstance, field: "description")}</td>

                            <td>${fieldValue(bean: settingsInstance, field: "type")}</td>

                            <td>${fieldValue(bean: settingsInstance, field: "value")}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
