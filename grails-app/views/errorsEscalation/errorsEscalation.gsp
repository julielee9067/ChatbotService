<%@ page import="ca.georgebrown.chatbot.ChatbotTranscript; ca.georgebrown.chatbot.Setting" contentType="text/html;charset=UTF-8" %>
<g:set var="settingService" bean="settingService"/>

<html>
<head>
    <meta name="layout" content="main">


    <title>Gbc Banner Web Service - Chatbot Errors and Escalation</title>
    <style>
    .escalation {
        background-color: #f2f4f4;
        border-style: groove;
        border-width: medium;
        height: auto;
        width: 37%;
        float: right;
        padding: 2%;
        overflow:auto;
    }

    .escalationPercentage {
        background-color: #f2f4f4;
        border-style: groove;
        border-width: medium;
        height: auto;
        width: 37%;
        float: right;
        padding: 2%;
        overflow:auto;
        margin-top: 10px;
    }

    .errors {
        background-color: #f2f4f4;
        border-style: groove;
        border-width: medium;
        width: 60%;
        height: 900px;
        float: left;
        overflow: auto;
        padding: 2%;
    }

    .processingErrors {
        background-color: #f2f4f4;
        border-style: groove;
        border-width: medium;
        width: 100%;
        height: 28%;
        margin-bottom:6%;
        float: left;
        overflow: auto;
        padding: 2%;
    }

    .classificationErrors {
        background-color: #f2f4f4;
        border-style: groove;
        border-width: medium;
        width: 100%;
        height: 28%;
        float: left;
        overflow: auto;
        padding: 2%;
        margin-bottom:6%;
    }

    .mismatchErrors {
        background-color: #f2f4f4;
        border-style: groove;
        border-width: medium;
        width: 100%;
        height: 28%;
        float: left;
        overflow: auto;
        padding: 2%;
    }

    .reason{
        width: 40%;
        padding: 1% 5%
    }

    .count{
        width:30%;
        float:right;
        text-align:right;
        padding:2% 10%;
    }

    .escalationTable {
        width:100%;
    }

    .header {
        border-bottom: solid;
        border-width:thick;
        width: 630px;
    }

    .rowTable{
        border-bottom-style: dotted;
        border-bottom-width:thin;
    }

    .rTableRow {
        display: table-row;
    }

    .rTableCellCause {
        display:table-cell;
        padding: 3px 10px;
        width: 200px;
        color: black;
    }

    .rTableCellResolve {
        display:table-cell;
        padding: 3px 10px;
        width: 10px;
        color: black
    }

    .rTableCellTimeSent {
        display:table-cell;
        padding: 3px 10px;
        min-width: 250px;
        width: auto;
        color: black;
    }
    .rTableCellUtteranceMismatch {
        display:table-cell;
        padding: 3px 10px;
        width: 300px;
        color: black;

    }
    .rTableCellUtterance {
        display:table-cell;
        padding: 3px 10px;
        width: 425px;
        color:black;
    }

    .rTableCellIntent {
        display:table-cell;
        padding: 3px 10px;
        width: 200px;
        color:black;
    }

    .resolve {
        display:table-cell;
        padding: 3px 10px;
        width: 100px;
        color:black;
    }

    .resolve button {
        margin-left:30px;
    }

    .errorsContent {
        overflow:auto;
    }
    </style>
</head>

<body class="container-fluid">
<g:render template="/layouts/navigation" model="[which:'errorsEscalation']" />
<div class="container-fluid mx-5 py-3 flex-f">
    <div class = "errors">
        <h2> Errors </h2>
        <div class="processingErrors">
            <h4> Processing Errors </h4>
            <tr class="rTableRow"> <td>
                <div class = "header">
                    <div class = "cell rTableCellCause">Cause Utterance</div>
                    <div class = "cell rTableCellTimeSent">Last Occurrence</div>
                </div>
            </td>
            </tr>
            <div class = "errorsContent">
                <g:each in="${errorsList}" var="error">
                    <tr class="rTableRow"> <td>
                        <span>
                            <div class = "cell rTableCellCause">${ChatbotTranscript.findByChatTokenAndOrderBubble(error.chatToken, error.orderBubble-1).chatMessage}</div>
                            <g:link action="transcript" controller ="transcript" params="[token: error.chatToken]">
                                <div class = "cell rTableCellTimeSent">${formatDate(format: 'MMM dd, yyyy (HH:mm:ss)', date: error?.timeSentMessage)}</div>
                            </g:link>
                            <div class = "cell rTableCellResolve"> <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#confirmResolveProcessing" data-id ="${ChatbotTranscript.findByChatTokenAndOrderBubble(error.chatToken, error.orderBubble).id}">Resolve</button></div>
                        </span>
                        <br>
                    </td>
                    </tr>
                </g:each>

                <div id="confirmResolveProcessing" class="modal fade">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <!-- dialog body -->
                            <div class="modal-body">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <br>
                                <g:form name="Resolve" action ="resolve">
                                    <input type="hidden" name="transcriptId" id="utterance_processing" value="" />
                                    <p> Are you sure that you want to record the following cause of a processing error (<span id = "utterance_span_processing"></span>) as resolved? It cannot be reversed. </p>
                                    <!-- dialog buttons -->
                                    <g:submitButton name="Resolve" action ="resolve" class="btn btn-sm btn-primary"></g:submitButton>
                                    <button type="button" data-dismiss="modal" class="btn btn-sm btn-danger">Cancel</button>
                                </g:form>
                            </div>
                        </div>
                    </div>
                </div>
                <script>
                    $('#confirmResolveProcessing').on('show.bs.modal', function (e) {
                        var cause = $(e.relatedTarget).attr('data-id');
                        $(".modal-body #utterance_span_processing").text(cause);
                        $(".modal-body #utterance_processing").val(cause);
                    })
                </script>
            </div>
        </div>
        <div class="classificationErrors">
            <h4> Unrecognized errors </h4>
            <tr class="rTableRow"> <td>
                <div class = "header">
                    <div class = "cell rTableCellUtterance">Utterance</div>
                </div>
            </td>
            </tr>
            <div class = "errorsContent">
                <g:each in="${unrecognized}" var="error">
                    <tr class="rTableRow"> <td>
                        <span>
                        <g:link action="transcript" controller ="transcript" params="[token: error.chatToken]">
                            <div class = "cell rTableCellUtterance">${error.utterance}</div>
                        </g:link>
                        <div class = "resolve"> <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#confirmResolveUnrecognized" data-id ="${error.utterance}">Resolve</button></div>
                        </span>
                        <br>
                    </td>
                    </tr>
                </g:each>
                <div id="confirmResolveUnrecognized" class="modal fade">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <!-- dialog body -->
                            <div class="modal-body">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <br/>
                                <g:form name="Resolve" action ="resolveUnrecognized" params="[]">
                                    <input type="hidden" name="utterance" id="utterance_unrecognized" value="" />
                                    <p> Are you sure that you want to record the unrecognized utterance (<span id = "utterance_span_unrecognized"></span>) as resolved? It cannot be reversed. </p>

                                <!-- dialog buttons -->
                                    <g:submitButton name="Resolve" action ="resolveMismatch" class="btn btn-sm btn-primary"></g:submitButton>
                                    <button type="button" data-dismiss="modal" class="btn btn-sm btn-danger">Cancel</button>
                                </g:form>
                            </div>
                        </div>
                    </div>
                </div>
                <script>
                    $('#confirmResolveUnrecognized').on('show.bs.modal', function (e) {
                        var unrecognized = $(e.relatedTarget).attr('data-id');
                        $(".modal-body #utterance_span_unrecognized").text(unrecognized);
                        $(".modal-body #utterance_unrecognized").val(unrecognized);
                    })
                </script>
            </div>
        </div>
        <div class="mismatchErrors">
            <h4> Mismatch errors </h4>
            <div class="rTableRow"> <td>
                <div class = "header">
                    <div class = "cell rTableCellUtteranceMismatch"> Utterance </div>
                    <div class = "cell rTableCellIntent"> Correct Intent </div>
                </div>
            </td>
            </div>
            <div class = "errorsContent">
                <g:each in="${mismatch}" var="mismatched">
                    <div class="rTableRow"> <td>
                        <span>
                            <div class = "cell rTableCellUtteranceMismatch">${mismatched.utterance}</div>
                            <div class = "cell rTableCellIntent"> ${mismatched.correctIntent}</div>
                            <div class = "cell rTableCellResolve"> <button type = "button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#confirmResolve" data-id ="${mismatched.utterance}~separate~${mismatched.correctIntent}">Resolve</button></div>
                        </span>
                    </td>
                    </div>
                </g:each>
                <div id="confirmResolve" class="modal fade">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <!-- dialog body -->
                            <div class="modal-body">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <br/>
                                <g:form name="Resolve" action ="resolveMismatch" params="[]">
                                    <input type="hidden" name="correct_intent" id="correct_intent" value="" />
                                    <input type="hidden" name="utterance" id="utterance" value="" />
                                    <p> Are you sure that you want to record the mismatched utterance (<span id = "utterance_span"></span>) and intent (<span id ="correct_intent_span"></span>) as resolved? This action cannot be reversed. </p>

                                    <!-- dialog buttons -->
                                    <g:submitButton name="Resolve" action ="resolveMismatch" class="btn btn-sm btn-primary"></g:submitButton>
                                    <button type="button" data-dismiss="modal" class="btn btn-sm btn-danger">Cancel</button>
                                </g:form>

                            </div>
                        </div>
                    </div>
                </div>
                <script>
                    $('#confirmResolve').on('show.bs.modal', function (e) {
                        var Mismatched = $(e.relatedTarget).attr('data-id');
                        var separate = Mismatched.split("~separate~");
                        $(".modal-body #utterance_span").text(separate[0]);
                        $(".modal-body #correct_intent_span").text(separate[1]);

                        $(".modal-body #utterance").val(separate[0]);
                        $(".modal-body #correct_intent").val(separate[1]);
                    })
                </script>
            </div>
        </div>
    </div>
    <div class="escalation">
        <h2> Escalation </h2>
        <table class ="escalationTable">
            <tr class = "header">
                <td class ="reason"> Type </td>
                <td class ="count"> Freq </td>
            </tr>
            <g:each in="${escalationStats}" var="escalation">
                <tr class ="rowTable">
                  <td class ="reason"> ${escalation.reason} </td>
                    <td class ="count"> ${escalation.freq} </td>
                </tr>
            </g:each>
        </table>
        <br>
    </div>
    <div class="escalationPercentage">
        <h4>PERCENTAGE OF ESCALATION</h4><br>
        <h1 style="text-align: center">${escalationStat.percentage} %</h1>
        <br>
        <h6 style="padding-left: 10px">Number of total intent invoked: ${escalationStat.numIntent.toInteger()}</h6>
        <h6 style="padding-left: 10px">Number of escalation invoked: ${escalationStat.numEscalation.toInteger()}</h6>
    </div>
</div>
</body>
</html>