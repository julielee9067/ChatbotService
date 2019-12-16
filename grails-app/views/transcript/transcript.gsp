<%@ page import="ca.georgebrown.chatbot.ChatbotTranscript; ca.georgebrown.chatbot.Setting" contentType="text/html;charset=UTF-8" %>
<g:set var="settingService" bean="settingService"/>

<html>
<head>
    <meta name="layout" content="main">
    <title>Gbc Banner Web Service - Chatbot Transcripts</title>
    <style>
        .information-boxes {
            background-color: #337ab7;
            height: 700px;
            width: 37%;
            float: right;
            padding: 2%;
            margin-top: -100px;
            color: white;
        }

        .transcript {
            background-color: #f2f4f4;
            border-style: groove;
            border-width: medium;
            width: 60%;
            height: 600px;
            float: left;
            overflow: auto;
        }

        .cell {
            display: table-cell;
            padding: 3px 10px; !important;
            color: black;
            font-size: 14px;
        }

        .chat-bubble {
            border-radius:24px;
            display:block;
            font-size:calc(1em + .25vmin);
            padding:12px;
            width:fit-content;
            align-self:center;
            max-width:400px;
        }

        .bot,.Error {
            margin-right:auto;
        }

        .bot {
            background-color: #ebf7ff;
        }

        .Error {
            background-color: #fcbaba;
        }

        .user {
            background-color: #96d5ff;
        }

        .user,.correction {
            margin-left:auto;
        }

        .user .timeDisplay {
            text-align: right;
            margin-left: auto;
            float: right;
        }

        .bot .timeDisplay {
            text-align: left;
            margin-right: auto;
            float: left;
        }

        input{
            color: black;
        }

        .correction {
            border-radius:24px;
            font-size:10px;
            width:-moz-fit-content;
            width:fit-content;
        }

        .timeDisplay {
            display: inline-block;
            alignment-baseline: bottom;
            border-radius:24px;
            font-size:10px;
            margin-top: 40px;
            margin-bottom: 0;
            width:-moz-fit-content;
            width:fit-content;
        }

        .transcript-toolbar {
            border-style: groove;
            border-width: medium;
            border-bottom: none;
            width: 60%;
            height: 15%;
            background-color: #00b6ff;
            color: white;
            padding: 10px;
        }

        .input {
            text-align: center;
        }

        .button {
            background-color:white;
            color: black;
            font-size: 16px;
            border-radius: 10px;
            border-style:solid;
            border-color:black;
            height: 30px;
            float:right;
            margin-right: 0px;
        }

        .input-transcript{
            display:block;
        }

        .toolbar-title {
            font-size: 3vw;
        }

        .token-input {
            display:inline;
        }

        .token-input.transcript-search{
            padding-left:0.5em;
        }

        .userId-input {
            display:inline;
        }

        .transcript-search {
            width:100%;
        }

        .user-id-transcript {
            float:left;
            padding-left: 1%;
            min-width:200px;
            width:30%;
        }

        .information-boxes input[type=text]{
            width:70%;
            height: 2vw;
            overflow: auto;
            font-size: 1vw;
        }

        .token-input input[type=text] {
            font-size: 1vw;
        }

        .userId-input input[type=text] {
        }

        p {
            padding-left: 10px;
        }

        /*big screen*/
        @media screen and (min-width: 1200px) {
            .toolbar-title {
                font-size: 36px;
            }

            .chat-bubble {
                max-width: 60%;
            }
        }

        @media screen and (max-width: 1101px) {
            .userId-input, .token-input{
                display: block;
            }

            .token-input.transcript-search {
                padding-left: 0px;
            }
        }

        /*little screen*/
        @media screen and (max-width: 1100px) {
            .toolbar-title {
                font-size: 28px;
            }

            .user-id-transcript, .date-transcript {
                font-size:14px;
            }

            .transcript-toolbar{
                height:100px;
            }

            .userId-input, .token-input {
                display: inline;
            }

            .token-input.transcript-search {
                padding-left: 0.5em;
            }

            .information-boxes {
                display: block;
                width:100%;
                margin-top:10px;
            }

            .transcript-toolbar, .transcript {
                width:100%;
                display:block;
            }

            .information-boxes input[type=text]{
                width:70%;
                height: 28px;
                overflow: auto;
                font-size: 14px;
            }
        }

        /*tiny screen*/
        @media screen and (max-width: 867px)  and (min-width: 668px){
            .user-id-transcript, .date-transcript {
                width:100%;
                padding-left:1%;
            }
        }
    </style>
</head>

<body class="container-fluid">
<g:render template="/layouts/navigation" model="[which:'transcripts']" />
<div class="container-fluid mx-5 py-3 flex-f">
        <div class="transcript-toolbar">
            <h2 class="toolbar-title">Chatbot Transcript: Ask George</h2>
            <g:if test="${!params.userId && !params.token}">
                Search for a valid transcript in the right window.
            </g:if>
            <g:elseif test="${checkUserId == false && params.userId}">
                The user ID ${params.userId} is not found search for another user ID.
            </g:elseif>
            <g:elseif test="${checkChatToken == false && params.token}">
                The token ${params.token} is not found search for another token.
            </g:elseif>
            <g:else>
                <g:form name ="transcriptForm" url = " transcript?userId=${params.userId}">
                    <div class = "input-transcript">
                        <div class="user-id-transcript">
                            <b> User ID: ${params.userId}</b>
                        </div>
                        <div>
                            <b> Date: </b>
                            <b style="color: white"> ${formatDate(format: 'MMM dd, yyyy (HH:mm:ss)', date: dateTranscript)}</b>
                        </div>

                    </div>
                </g:form>
            </g:else>
        </div>
        <div class="transcript">
            <g:if test="${params.tokens && params.userId && params.token && checkChatToken == true && checkUserId == true}" >
            </g:if>
            <g:else>
                <p>No transcript could be found. </p>
            </g:else>
            <g:each in="${transcriptList}" var="transcript" status="i">
                <g:if test="${(transcript.chatMessage in mismatch.collect{it.utterance}.unique())}">
                    <g:if test="${transcript.speakerBubble == 'user'}">
                        <div style="display: flex; flex-flow: row wrap; justify-content: flex-end; align-items: stretch; align-content: space-around;">
                            <span class="timeDisplay" style="width: fit-content; margin: 20px 0;"> ${formatDate(format: 'HH:mm:ss', date: transcript.timeSentMessage)}</span>
                            <div class="${transcript.speakerBubble} chat-bubble" style=" background-color: #fffd73; width: fit-content;margin: 10px 10px;">${transcript.chatMessage}</div>
                        </div>
                    </g:if>
                    <g:else>
                        <div style="display: flex; flex-flow: row wrap; justify-content: flex-start; align-items: stretch; align-content: space-around;">
                            <div class="${transcript.speakerBubble} chat-bubble" style="background-color: #fffd73; width: fit-content; margin: 10px 10px;">${transcript.chatMessage}</div>
                            <span class="timeDisplay" style="width: fit-content; margin-top: auto;"> ${formatDate(format: 'HH:mm:ss', date: transcript.timeSentMessage)}</span>
                        </div>
                    </g:else>
                    <p class="correction" > Correct intent: ${mismatch.find{it.utterance ==~ transcript.chatMessage}?.correctIntent} </p>
                </g:if>
                <g:elseif test="${unrecognized.contains(transcript.chatMessage)}">
                    <g:if test="${transcript.speakerBubble == 'user'}">
                        <div style="display: flex; flex-flow: row wrap; justify-content: flex-end; align-items: stretch; align-content: space-around;">
                            <span class="timeDisplay" style="width: fit-content; margin: 20px 0;"> ${formatDate(format: 'HH:mm:ss', date: transcript.timeSentMessage)}</span>
                            <div class="${transcript.speakerBubble} chat-bubble" style="background-color: #fffd73; width: fit-content; margin: 10px 10px;">${transcript.chatMessage}</div>
                        </div>
                    </g:if>
                    <p class="correction">
                        <g:link type="button" action="resolveEscalationFromTranscript" controller="transcript" name="resolved" id="resolveUnrecognized" params="[utterance: transcript.chatMessage, token: transcript.chatToken]"> Resolve </g:link>
                        Unrecognized intent
                    </p>
                </g:elseif>
                <g:elseif test="${insultList.contains(transcript.chatMessage)}">
                    <g:if test="${transcript.speakerBubble == 'user'}">
                        <div style="display: flex; flex-flow: row wrap; justify-content: flex-end; align-items: stretch; align-content: space-around;">
                            <span class="timeDisplay" style="width: fit-content; margin: 20px 0;"> ${formatDate(format: 'HH:mm:ss', date: transcript.timeSentMessage)}</span>
                            <div class="${transcript.speakerBubble} chat-bubble" style=" background-color: #ff8888; width: fit-content;margin: 10px 10px;">${transcript.chatMessage}</div>
                        </div>
                    </g:if>
                    <p class="correction">
                        <g:link type="button" class="correction" action="resolveEscalationFromTranscript" controller="transcript" name="resolved" id="resolveUnrecognized" params="[utterance: transcript.chatMessage, token: transcript.chatToken]"> Resolve </g:link>
                        Insult
                    </p>
                </g:elseif>
                <g:elseif test="${escalated.contains(transcript.chatMessage)}">
                    <g:if test="${transcript.speakerBubble == 'user'}">
                        <div style="display: flex; flex-flow: row wrap; justify-content: flex-end; align-items: stretch; align-content: space-around;">
                            <span class="timeDisplay" style="width: fit-content;margin: 20px 0;"> ${formatDate(format: 'HH:mm:ss', date: transcript.timeSentMessage)}</span>
                            <div class="${transcript.speakerBubble} chat-bubble" style=" background-color: lavender; width: fit-content;margin: 10px 10px;">${transcript.chatMessage}</div>
                        </div>
                    </g:if>
                    <p class="correction">
                        <g:link type="button" class="correction" action="resolveEscalationFromTranscript" controller="transcript" name="resolved" id="resolveUnrecognized" params="[utterance: transcript.chatMessage, token: transcript.chatToken]"> Resolve </g:link>
                        Escalation
                    </p>
                </g:elseif>
                <g:else>
                    <g:if test="${transcript.speakerBubble == 'user'}">
                        <div style="display: flex; flex-flow: row wrap; justify-content: flex-end; align-items: stretch; align-content: space-around;">
                            <div class="timeDisplay" style="width: fit-content; margin-top: 40px"> ${formatDate(format: 'HH:mm:ss', date: transcript.timeSentMessage)}</div>
                            <div class="${transcript.speakerBubble} chat-bubble" style="width: fit-content; margin: 10px 10px;">${transcript.chatMessage}</div>
                        </div>
                    </g:if>
                    <g:elseif test="${transcript.speakerBubble == 'Error' && !transcript.resolved}">
                        <div style="display: flex; flex-flow: row wrap; justify-content: flex-start; align-items: stretch; align-content: space-around;">
                            <div class="Error chat-bubble" style="width: fit-content;margin: 10px 0;">${transcript.chatMessage}</div>
                            <div class="timeDisplay" style="width: fit-content; margin-top: auto;"> ${formatDate(format: 'HH:mm:ss', date: transcript.timeSentMessage)}</div>
                            <g:link type="button" class="timeDisplay" style="margin-top: auto; margin-left: 10px" action="resolveEscalationFromTranscript" controller="transcript" name="resolved" id="resolveUnrecognized" params="[utterance: transcript.chatMessage, token: transcript.chatToken]"> Resolve </g:link>
                        </div>
                    </g:elseif>
                    <g:elseif test="${transcript.speakerBubble}">
                        <div style="display: flex;flex-flow: row wrap;justify-content: flex-start; align-items: stretch; align-content: space-around;">
                            <div class="bot chat-bubble" style="width: fit-content;margin: 10px 0;">${transcript.chatMessage}</div>
                            <div class="timeDisplay" style="width: fit-content; margin-top: auto;"> ${formatDate(format: 'HH:mm:ss', date: transcript.timeSentMessage)}</div>
                        </div>
                    </g:elseif>
                </g:else>
            </g:each>
        </div>

    <div class="information-boxes">
        <h4> Search</h4>
        <g:form name="SearchByUserId" url=" transcript">
            <div class = "transcript-search">
                <div class="userId-input"> User ID: </div>
                <g:textField id="userIdInput" name="userId" style="height: 30px" value="${params.userId}" action="transcript"/>
                <g:submitButton class="button" name="search-userId" action="transcript" value="Go"/>
            </div>
        </g:form>
        <br>
        <g:form name="SearchByToken" url=" transcript">
            <div class="transcript-search token-input">
                <div class="token-input" > Token: </div>
                <g:textField id="tokenInput" name="token" style="height: 30px" value="${params.token}" action="transcript"/>
                <g:submitButton class="button" name="search-token" action="transcript" value="Go"/>
            </div>
        </g:form>
        <div class="browse table-container" id="browse">
            <h4 class="mt-3"> Browse </h4>
            <h6> * : Escalation &nbsp ! : Error &nbsp X : Mismatch</h6>
            <g:include controller ="transcript" action = "browseTranscripts"/>
        </div>
    </div>
</div>
</body>
</html>