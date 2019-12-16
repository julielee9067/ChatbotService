<%@ page import="ca.georgebrown.chatbot.ChatbotIntentUtterance" %>
<style>
    div.search-bars{
        float:left;
        width:32.2%;
        margin-right: 1%;
    }

    .searchArea {
        margin: -20px -20px 3%;
        /*padding: 2% 1%;*/
        /*width: 104.3%;*/
        min-height: calc(100% - 750px);
        /*height: 10%;*/
        background-color: white;
        border-bottom-style: solid;
    }

    .saveButton {
        background-color: lightgreen;
        border-color: white;
        font-size: 12px;
        display: inline-block;
        padding: 4%;
        width: 60px;
        border-width: thin;
        border-style: solid;
        border-radius: 10px;
        text-align: center;
    }

    .descriptionIntent {
        text-align: left;
        width:100%;
        font-size:14px;
        margin-bottom: 3%;
    }

    .intentInput {
        padding-left:10px;
        width:150px;
        font-size:14px;
        margin:0px;
    }

    .search-utterance {
        padding-left:10px;
        width:150px;
        font-size:14px;
    }

    .search-intent {
        margin-right:35px;
    }

    .rTable {
        overflow:auto;
        height:400px;
        display:inline-block;
        width:100%;
    }

    .select-intent {
        margin-right: 5%;
    }

    .searchBoxContainer {
        display: grid;
        float:left;
        padding-left: 3%;
        /*max-height: 961px;*/
        /*height: 100%;*/
        /*width: calc(100% - 20px);*/
    }

</style>
<script type="text/javascript">
    <!--
    function edit_visibility(id, id2, id3, id4, id5) {

        var e = document.getElementById(id);
        var f = document.getElementById(id2);
        var g = document.getElementById(id3);
        var h = document.getElementById(id4);
        var i = document.getElementById(id5);

        e.style.display ='none'
        f.style.display ='none'
        g.style.display = 'block'
        h.style.display = 'block'
        i.style.display = 'block'
    }
    //-->

    function undo_visibility(id, id2, id3, id4, id5) {

        var e = document.getElementById(id);
        var f = document.getElementById(id2);
        var g = document.getElementById(id3);
        var h = document.getElementById(id4);
        var i = document.getElementById(id5);

        e.style.display ='block';

        f.style.display ='block';
        g.style.display = 'none';
        h.style.display = 'none';
        i.style.display = 'none';

    }

    $(document).ready(function() {
    $("#submitBtn").on("click", function () {

        console.log('hello');
        $("#myModal").on("show", function() {    // wire up the OK button to dismiss the modal when shown
            $("#myModal a.btn").on("click", function(e) {
                console.log("button pressed");   // just as an example...
                $("#myModal").modal('hide');     // dismiss the dialog
            });
        });

        $("#myModal").on("hide", function() {    // remove the event listeners when the dialog is dismissed
            $("#myModal a.btn").off("click");
        });

        $("#myModal").on("hidden", function() {  // remove the actual elements from the DOM when fully hidden
            $("#myModal").remove();
        });

        $("#myModal").modal({                    // wire up the actual modal functionality and show the dialog
            "backdrop"  : "static",
            "keyboard"  : true,
            "show"      : true                     // ensure the modal is shown immediately
        });

    })
});

</script>
<div class="searchArea py-3">
   <table class="w-100">
    <tr>
        <td>
            <g:form name ="SearchByIntent" url = " intents?selection=search">
                <table class="searchBoxContainer my-1">
                    <tr>
                        <td style="padding-right: 5px;">INTENT:</td>
                        <td><g:textField id = "intentInput" class="intentInput mr-1" name="Intent" action="intents"/></td>
                        <td><g:submitButton class="button search-intent"  name="search-intent" action= "intents" value="Go"/></td>
                    </tr>
                </table>
            </g:form>

            <g:form name ="SelectIntent" url = " intents?selection=search">
                <table class="searchBoxContainer my-1">
                    <tr>
                        <td><g:select id='IntentSelect' name="Intent" from="${intents}" noSelection="${['':'Select Intent']}" optionKey="intent" optionValue="intent" class="mr-1"/></td>
                        <td><g:submitButton  class="button select-intent mr-1"  name="select-intent" action= "intents" value="Go"/></td>
                    </tr>
                </table>
            </g:form>

            <g:form name ="SearchByUtterance" url = " intents?selection=search">
                <table class="searchBoxContainer my-1">
                    <tr>
                        <td style="padding-right: 5px;">UTTERANCE:</td>
                        <td><g:textField id = "intentInput" class="search-utterance mr-1"  name="Utterance" action="intents"/></td>
                        <td><g:submitButton class="button mr-1"  name="search-utterance" action= "intents" value="Go"/></td>
                    </tr>
                </table>
            </g:form>
        </td>
    </tr>
</table>
</div>

<div class="intent-select">
    <g:if test="${params.Intent}">
        <h3> ${params.Intent} </h3>
        <g:form name="updateDescription" action="updateIntentDescription" params = "[Intent: params.Intent, selection: params.selection]">
            <div class = "rTableCellDescription m-0"> <div class ="descriptionIntent" style="display:block;" id="${params.Intent}-Description">${description}</div>
                <g:textArea id = "${params?.Intent}-Input" class="descriptionIntent" style="display:none" value="${description}" name="${params.Intent}.description" action="updateIntentDescription"/>
            </div>
            <div class = "rTableCellEdit m-0 p-0">
                <div class="editButton m-0" onclick="edit_visibility('${params.Intent}-Description', '${params.Intent}-Edit', '${params.Intent}-Input', '${params.Intent}-Undo', '${params.Intent}-Save')" id="${params.Intent}-Edit"> EDIT </div>
                <span style="display:none; margin: 0" class="editButton" onclick="undo_visibility('${params.Intent}-Description', '${params.Intent}-Edit', '${params.Intent}-Input', '${params.Intent}-Undo', '${params.Intent}-Save' )" id="${params.Intent}-Undo"> UNDO </span>
                <span><g:submitButton id="${params.Intent}-Save" style ="display:none;" class="saveButton" name="submit" action= "updateIntentDescription" value="SAVE" params = "[Intent: params.Intent]"/></span>
            </div>
        </g:form>
        <g:form name="updateIntent" action="updateIntents" params = "[utteranceData: utterances, Intent: params.Intent]">
            <table class="rTable" >
                <tr class ="rTableRow">
                    <td class = "cell rTableCellCount">Count</td>
                    <td class = "cell rTableCellUtteranceSelect">Utterance</td>
                    <td class = "cell rTableCellIncorrect"> Incorrect </td>
                    <td class = "cell rTableCellCorrectIntent">Correct Intent</td>
                </tr>
                <g:each in="${utterances}" var="utterance" status ="i">
                    <tr class="rTableRow">
                        <td class = "cell rTableCellCount">${ChatbotIntentUtterance.findAllByUtterance(utterance.key as String).size() ?: 0}</td>
                        <td class = "cell rTableCellUtteranceSelect">${utterance.key}</td>
                        <td class = "cell rTableCellIncorrect"> <g:checkBox  onclick="toggle_visibility('${utterance.key}-Select', '${utterance.key}-Select-2')" name="${utterance.key}.incorrect" value="${utterance.value[0].incorrect}"/> </td>
                        <td class = "cell rTableCellCorrectIntent" id = '${utterance.key}-NewIntent'> <g:select id = '${utterance.key}-Select'  class = "${utterance.value[0].incorrect}-Select" name="${utterance.key}.correctIntent" from="${intents}" value="${utterance.value[0].correctIntent}" optionKey="intent" optionValue="intent"/><p id="${utterance.key}-Select-2" class ="${utterance.value[0].incorrect}-default">--</p></td>
                    </tr>
                </g:each>
            </table>
            <g:submitButton class="SubmitButton" name ="submit" id="submitBtn" action="updateIntents" params = "[utteranceData: utterances, Intent: params.Intent]">
                Update
            </g:submitButton>
        </g:form>
    </g:if>
    <g:else>
        <h4 style ="text-align: center; margin-top:40%;"> Search for an intent or utterance in the search bar above </h4>
    </g:else>

    <!-- Modal -->
</div>