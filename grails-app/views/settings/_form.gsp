
<%@ page import="ca.georgebrown.chatbot.Setting" %>


<table class="table center table-bordered">
    <tr>
        <th>Code</th>
        <td>${settingsInstance?.code}</td>
    </tr>
    <tr>
        <th><label for="description">Description</label></th>
        <td><input class="settingsTextbox" type="text" id="description" name="description"  value="${settingsInstance?.description}"/></td>
    </tr>
    <tr>
        <th><label for="value">Value</label></th>
        <td>
<g:if test="${settingsInstance.type in [Setting.TYPE.INT,Setting.TYPE.LONG,Setting.TYPE.DOUBLE]}">
    <g:field id="value" type="number" name="value" value="${settingsInstance?.value}" />
</g:if>

<g:elseif test="${settingsInstance.type==Setting.TYPE.DATE}">
    <g:datePicker id="value"  name="value" relativeYears="[-2..2]" precision="day" value="${settingsInstance.value}" noSelection="['':'none']"/>
</g:elseif>
<g:elseif test="${settingsInstance.type==Setting.TYPE.DATETIME}">
    <g:datePicker id="value"  name="value" relativeYears="[-2..2]" precision="minute" value="${settingsInstance.value}" noSelection="['':'none']"/>
</g:elseif>
<g:elseif test="${settingsInstance.type==Setting.TYPE.BOOLEAN}">
    <g:select id="value"  name="value" from="${["true","false"]}" value="${settingsInstance.value}"/>
</g:elseif>
<g:elseif test="${settingsInstance.type in [Setting.TYPE.STRING,Setting.TYPE.LIST]}">
    <g:textArea cols="80" id="value"  name="value" value="${settingsInstance?.value}" />
</g:elseif>
        </td>
    </tr>
</table>
