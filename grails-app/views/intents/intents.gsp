<%@ page import="ca.georgebrown.chatbot.Setting" contentType="text/html;charset=UTF-8" %>
<g:set var="settingService" bean="settingService"/>

<html>
<head>
    <meta name="layout" content="main">
    <title>Gbc Banner Web Service - Chatbot Intent Monitoring</title>
    <style>
        .IntentWindow {
            background-color: #337ab7;
            width: 81%;
            height: 100%;
            min-height: 500px;
            float: left;
            padding: 20px;
            border-style: solid solid solid none;
        }

        .Search {
            background-color: transparent;
            width: 19%;
            min-height: 800px;
            float:left;
            border-style: solid none solid solid;
        }

        .intent {
            background-color: white;
            width: 100%;
            padding: 20px 10px;
            height: 100%;
            border-style: solid;
        }

        .browseTable {
            overflow: auto;
            height: 100%;
            display: inline-block;
            width: 100%;
        }

        .intentData{
            background-color: white;
            width: 100%;
            padding: 20px 10px;
            height: 100%;
            border-style: solid;
        }

        .intent-select {
            background-color: white;
            width: 64%;
            padding: 20px 10px;
            height: 85%;
            float: left;
            border-style: solid;
        }

        .convoTab {
            width: 290px;
        }

        .convoTablist {
            width:auto;
        }

        .conversations {
            background-color: white;
            width: 33%;
            float: right;
            padding: 20px 10px;
            height: 85%;
            border-style: solid;
            overflow-y: auto;
            overflow-x: hidden;
        }

        .button {
            background-color: white;
            color: black;
            font-size: 16px;
            border-radius: 10px;
            border-style: solid;
            border-color: black;
            height: 30px;
            float:right;
            margin-right: 0px;
        }

        .SubmitButton {
            background-color: white;
            color: black;
            font-size: 16px;
            border-radius: 10px;
            border-style: solid;
            border-color: black;
            height: 30px;
            margin-top: 10px;
            margin-left:43%;
        }

        input[type=checkbox], input[type=radio] {
            background-color: red;
            border: 0;
            padding: 0;
        }

        .BrowseButton {
            background-color: inherit;
            color: inherit;
            font-size: 1vw;
            border-style: none none solid none;
            border-color: black;
            height: 100px;
            width: 100%;
        }

        .BrowseButton:hover {
            background-color:#9fc4e4;
        }

        .rTable {
            width: 100%;
            overflow:auto;
        }

        .rTableRow {
            display: table-row;
            font-size:14px;
            border-style: solid;
            border-width: thin;
        }

        .rTableHeading {
            display: table-header-group;
            background-color: #ddd;
        }

        .rTableCellUtterance {
            display: table-cell;
            padding: 3px 10px;
            width: 166px;
            color:black;
            font-size: 14px;
        }

        .rTableCellUtteranceSelect {
            display: table-cell;
            padding: 3px 10px;
            width: 250px;
            color: black;
            font-size: 14px;
        }

        .rTableCellCount {
            display: table-cell;
            padding: 3px 10px;
            width: 80px;
            color: black;
            font-size: 14px;
        }

        .rTableCellIncorrect{
            width: 93px;
        }

        .rTableCellEdit{
            width: 70px;
        }

        .rTableCellIntent{
            width: 229px;
        }

        .cell {
            display: table-cell;
            padding: 3px 10px;
            color: black;
            font-size: 14px;
        }
        .rTableCellCorrectIntent {
            width: 196px;
            text-align: center;
            margin-left: 30%;
        }

        .rTableCellCategory {

            width: 120px;
            text-align: center;
            margin-left: 30%;
        }

        .rTableCellDescription {
            width: 430px;
            text-align: left;
            margin-left: 30%;
        }

        .rTableCellIncorrect input {
            margin-left: 40%
        }

        .rTableCellTimeSent{
            width: 200px;
        }

        .true-Select, .false-Select {
            width: 150px;
        }

        .true-Select {
            display: table-cell;
        }

        .true-default {
            display: none;
        }

        .false-default{
            display: table-cell;
        }

        .false-Select {
            display: none;
        }

        .header {
            border-bottom: solid;
            border-width: thick;
            padding: 0;
        }

        .editButton {
            font-size: 12px;
            display: inline-block;
            padding: 4%;
            width: 60px;
            border-width: thin;
            border-style: solid;
            border-radius: 10px!important;
            text-align: center;
            overflow: hidden;
        }

        #{params.selection} {
            background-color: yellow;
        }

        div.active{
            background-color: #337ab7;
            color: white;
        }

        div.notActive{
            background-color: #f8f9fa;
            color: black;
            border-right:solid;
        }

        div.notActive:hover{
            background-color: #9fc4e4;
        }

        div.active:hover {
            background-color: #9fc4e4;
        }

        .blank {
            height: 24.5%;
        }

        .SelectCategory {
            width: 100px;
        }
    </style>
</head>

<body class="container-fluid" onload="shade_active(${params.selection})">
<g:render template="/layouts/navigation" model="[which:'intents']" />
<script type="text/javascript">
    <!--
    function shade_active(selection) {
        console.log(selection);
        selection.style.backgroundColor = "yellow";
    }
    function toggle_visibility(id, id2) {
        var e = document.getElementById(id);
        var f = document.getElementById(id2);

        if(e.style.display == 'table-cell')
            e.style.display = 'none';
        else if (e.style.display == 'none')
            e.style.display = 'table-cell';
        else if(e.className == "true-Select") {
            e.style.display = 'none';
        } else {
            e.style.display = 'table-cell';
        }

        if(f.style.display == 'table-cell')
            f.style.display = 'none';
        else if (f.style.display == 'none')
            f.style.display = 'table-cell';
        else if(f.className == "false-default") {
            f.style.display = 'none';
        } else {
            f.style.paddingLeft = '80px';
            f.style.display = 'table-cell';
        }
    }
    //-->

    function get_form_information(utterances){
        var uttData = utterances.split("]")

        for(i = 0; i < uttData.length; i++) {
            var start = uttData[i].indexOf("utterance:");
            var end = uttData[i].indexOf("incorrect:");
        }
        //var e = document.getElementById("Get status-Select").value;
        console.log(utterances)
    }

</script>


<div class="container-fluid mx-5 py-3 flex-f" >
    <div class ="Search">
        <g:form name ="MainMenu" url = " intents?selection=main">
            <div class="<g:if test="${params.selection == 'main'}">active</g:if><g:else>notActive</g:else>">
                <g:submitButton class="BrowseButton" id = 'main' name="browse-date" action= "intents" value="Main Stats"/>
            </div>
        </g:form>

        <g:form name ="BrowseByDate" url = " intents?selection=date">
            <div class="<g:if test="${params.selection == 'date'}">active</g:if><g:else>notActive</g:else>">
                <g:submitButton  class="BrowseButton" id = 'date' name="browse-date" action= "intents" value="Browse By Date"/>
            </div>
        </g:form>

        <g:form name ="BrowseByPopular" url = " intents?selection=popular">
            <div class="<g:if test="${params.selection == 'popular'}">active</g:if><g:else>notActive</g:else>">
                <g:submitButton  class="BrowseButton" name="browse-popular" action= "intents" value="Browse By Popular"/>
            </div>
        </g:form>

        <g:form name ="SeeIntentData" url = " intents?selection=data">
            <div class="<g:if test="${params.selection == 'data'}">active</g:if><g:else>notActive</g:else>">
                <g:submitButton class="BrowseButton"  id = "data" name="intent-data" action= "intents" value="See Intent Data"/>
            </div>
        </g:form>

        <g:form name ="SeeEscalationData" url = " intents?selection=escalation">
            <div class="<g:if test="${params.selection == 'escalation'}">active</g:if><g:else>notActive</g:else>">
                <g:submitButton class="BrowseButton"  id="escalation" name="intent-data" action= "intents" value="See Escalation Data"/>
            </div>
        </g:form>

        <g:form name ="ManageIntentCategory" url = " intents?selection=manageCategory">
            <div class="<g:if test="${params.selection == 'manageCategory'}">active</g:if><g:else>notActive</g:else>">
                <g:submitButton class="BrowseButton"  id="manage-category" name="intents" action= "intents" value="Manage Intent Category"/>
            </div>
        </g:form>

        <g:form name ="SeeSearch" url = " intents?selection=search">
            <div class="<g:if test="${params.selection == 'search'}">active</g:if><g:else>notActive</g:else>">
                <g:submitButton class="BrowseButton"  id="search" name="intent-search" action= "intents" value="Search"/>
            </div>
        </g:form>
        <div class ="notActive blank">
        </div>
    </div>

    <div class="IntentWindow">
            <g:if test="${params.selection == 'main' }">
                <g:include controller="intents" action="viewMainStats" params="[Intent: params.Intent, selection: params.selection]"/>
            </g:if>
            <g:if test="${params.selection == 'search' }">
                <g:include controller="intents" action="viewIntentInformation" params="[Intent: params.Intent, selection: params.selection]"/>
                <g:include controller="intents" action="viewConversations" params="[Intent:params.Intent, selection: params.selection]"/>
            </g:if>
            <g:if test="${params.selection == 'date' || params.selection == 'popular'}">
                <g:include controller="intents" action="browse" params="[selection: params.selection]"/>
            </g:if>
            <g:if test="${params.selection == 'data'}">
                <g:include controller="intents" action="intentData" params="[selection: params.selection]"/>
            </g:if>
            <g:if test="${params.selection == 'escalation'}">
                <g:include controller="intents" action="escalationData"/>
            </g:if>
            <g:if test="${params.selection == 'manageCategory'}">
                <g:include controller="intents" action="manageCategory"/>
            </g:if>
    </div>
</div>
</body>
</html>